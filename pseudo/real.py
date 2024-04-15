import tornado.ioloop
import tornado.web
import tornado.websocket
import json
import sqlite3

connections = []
DATABASE = "real.db"

class LoginHandler(tornado.web.RequestHandler):
    """
    POST /user/login
    
    Request:
    {
        "nameOrEmail": "username",
        "password": "password"
    }

    Response:
    If success:
        {
            "success": true,
            "token": "token"
            "username": "username",
            "userRole": 1,
            "message": "message"
        }
    If failed:
        {
            "success": false,
            "message": "message"
        }
    """
    def post(self):
        data = json.loads(self.request.body)
        nameOrEmail = data["nameOrEmail"]
        password = data["password"]
        conn = sqlite3.connect(DATABASE)
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM users WHERE (username=? OR email=?) AND password=?", (nameOrEmail, nameOrEmail, password))
        user = cursor.fetchone()
        if user:
            self.write({
                "success": True,
                "token": user[0],
                "username": user[1],
                "userRole": user[4],
                "message": "Login success"
            })
        else:
            self.write({
                "success": False,
                "message": "Login failed"
            })
        conn.close()

class ShowcaseHandler(tornado.web.RequestHandler):
    """
    GET /showcase

    Response:
    [
        {
            "title": "title",
            "description": "description",
            "date": "date",
            "link": "link"
        },
        ...
    ]
    """
    def get(self):
        conn = sqlite3.connect(DATABASE)
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM showcase")
        showcase = cursor.fetchall()
        self.write([{
            "title": item[1],
            "description": item[2],
            "date": item[3],
            "link": item[4]
        } for item in showcase])
        conn.close()

class InboxHandler(tornado.web.RequestHandler):
    """
    GET /user/inbox

    Response:
    [
        {
            "from": "from",
            "message": "message",
            "date": "date"
        },
        ...
    ]
    """
    def get(self):
        token = self.request.headers.get("Authorization")
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
        self.write([{
            "from": item[1],
            "message": item[2],
            "date": item[3]
        } for item in inbox])
        conn.close()

class GroupsHandler(tornado.web.RequestHandler):
    pass


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
