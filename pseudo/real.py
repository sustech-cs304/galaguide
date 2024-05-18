import tornado.ioloop
import tornado.web
import tornado.websocket
import json
import sqlite3
import hashlib
import random
import time
import smtplib
from email.mime.text import MIMEText
import base64

connections = []
DATABASE = "real.db"

def sha256_encode(s):
    return hashlib.sha256(s.encode()).hexdigest()

def make_rest_response(success, message, data={}):
    return {
        "code": 0 if success else 1,
        "message": message,
        "data": data
    }

class LoginHandler(tornado.web.RequestHandler):
    def post(self):
        data = json.loads(self.request.body)
        nameOrEmail = data["nameOrEmail"]
        password = data["password"]
        conn = sqlite3.connect(DATABASE)
        cursor = conn.cursor()
        password = sha256_encode(password)
        cursor.execute("SELECT * FROM users WHERE (username=? OR email=?) AND password=?", (nameOrEmail, nameOrEmail, password))
        user = cursor.fetchone()
        if user:
            self.write(make_rest_response(True, "Login success", {
                "userName": user[1],
                "email": user[2],
                "userRole": user[4]
            }))
        else:
            self.write(make_rest_response(False, "Login failed"))
        conn.close()

class ShowcaseHandler(tornado.web.RequestHandler):
    def get(self):
        conn = sqlite3.connect(DATABASE)
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM showcase")
        showcase = cursor.fetchall()
        self.write(make_rest_response(True, "Get showcase success", [{
            "title": item[1],
            "description": item[2],
            "date": item[3],
            "link": item[4]
        } for item in showcase]))
        conn.close()

class InboxHandler(tornado.web.RequestHandler):
    def get(self):
        token = self.request.headers.get("Bearer")
        if not token:
            raise tornado.web.HTTPError(401, "Unauthorized")
        conn = sqlite3.connect(DATABASE)
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM tokens WHERE token=?", (token,))
        user = cursor.fetchone()
        if not user:
            raise tornado.web.HTTPError(401, "Unauthorized")
        cursor.execute("SELECT * FROM inbox WHERE to_user=?", (user[1],))
        inbox = cursor.fetchall()
        self.write(make_rest_response(True, "Get inbox success", [{
            "from": item[1],
            "message": item[2],
            "date": item[3]
        } for item in inbox]))
        conn.close()

class GroupsHandler(tornado.web.RequestHandler):
    pass

class RegisterHandler(tornado.web.RequestHandler):
    def post(self):
        data = json.loads(self.request.body)
        name = data["name"]
        email = data["email"]
        password = data["password"]
        conn = sqlite3.connect(DATABASE)
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM users WHERE username=? OR email=?", (name, email))
        user = cursor.fetchone()
        if user:
            self.write(make_rest_response(False, "User already exists"))
        else:
            password = sha256_encode(password)
            ver_code = random.randint(100000, 999999)
            cursor.execute("INSERT INTO unverified_users (username, email, password, role, code) VALUES (?, ?, ?, 0)", (name, email, password, ver_code))
            conn.commit()
            self.write(make_rest_response(True, "Register success, please verify your email"))
        conn.close()

class VerifyEmailHandler(tornado.web.RequestHandler):
    def post(self):
        data = json.loads(self.request.body)
        email = data["email"]
        code = data["verificationCode"]
        conn = sqlite3.connect(DATABASE)
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM unverified_users WHERE email=? AND code=?", (email, code))
        user = cursor.fetchone()
        if user:
            cursor.execute("INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, 1)", (user[1], user[2], user[3]))
            cursor.execute("DELETE FROM unverified_users WHERE email=?", (email,))
            token = sha256_encode(user[1] + str(time.time()))
            cursor.execute("INSERT INTO tokens (token, username) VALUES (?, ?)", (token, user[1]))
            conn.commit()
            self.write(make_rest_response(True, "Email verification success",{
                "token": token
            }))
        else:
            self.write(make_rest_response(False, "Email verification failed"))
        conn.close()


def make_app():
    return tornado.web.Application([
        (r"/user/login", LoginHandler),
        (r"/showcase", ShowcaseHandler),
        (r"/user/inbox", InboxHandler),
        (r"/groups(?:\/\d+)?$", GroupsHandler),
        (r"/user/register", RegisterHandler),
        (r"/user/verify-email", VerifyEmailHandler),
        (r".*/ws.*", WebSocketHandler)
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(9260)
    print("Server is running on http://localhost:9260")
    tornado.ioloop.IOLoop.current().start()
