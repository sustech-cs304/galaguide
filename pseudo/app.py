import tornado.ioloop
import tornado.web
import json

class LoginHandler(tornado.web.RequestHandler):
    def post(self):
        data = json.loads(self.request.body)
        username = data["nameOrEmail"]
        password = data["password"]
        
        if username == "admin" and password == "password":
            response = {"success": True, "message": "Login successful", "user": "admin", "token": "123", "user_role": 1, "status": 200}
        else:
            response = {"success": False, "message": "Invalid username or password"}
        
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

class ShowcaseHandler(tornado.web.RequestHandler):
    def get(self):
        response = [
                { "title": 'Invitation to the English Corner on Feburary 24th (Saturday)', "date": '2024-02-21', "link": '#' },
                { "title": 'GRE General Test - Please tell us about your experience', "date": '2024-01-01', "link": '#' },
                { "title": 'Algorithm Design and Analysis SE2 Spring 2024: CS208 Course Info', "date": '2024-01-02', "link": '#' },
                { "title": '[Info Session] HEC Paris will give an info session on Thursday, Feb. 22, 2024', "date": '2024-02-03', "link": '#' },
                { "title": 'Upper Elementary French Spring 2024: What is the class like and what should I do?', "date": '2024-01-04', "link": '#' },
                { "title": 'Reminder for the Start of Classes in Spring 2024', "date": '2024-01-05', "link": '#' },
                { "title": 'Notice on Issuing and Implementing the Management of Study Abroad Scholarships for Undergraduates of SUSTech', "date": '2024-01-06', "link": '#' }
            ]
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

class InboxHandler(tornado.web.RequestHandler):
    def get(self):
        response = [
            {
                "title": 'Welcome to the new semester!',
                "sender": 'Admin',
                "time": 'Yesterday',
                "content": 'Welcome back to the new semester! We hope you have a great time learning and growing with us!<br>\
                        Btw, the new course schedule is out, don\'t forget to check it out!<br>\
                        Have a great day!'
            },
            {
                "title": 'Invitation to the English Corner on Feburary 24th (Saturday)',
                "sender": 'English Club',
                "time": '2024-02-21',
                "content": 'Dear students,<br>\
                        We are glad to invite you to the English Corner on Feburary 24th (Saturday).<br>\
                        The English Corner is a great place to practice your English and meet new friends.<br>\
                        We hope to see you there!'
            }
        ]
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

group_info = [
    {
        "name": "Group 1",
        "id": 0,
        "members": ["User 1", "User 2", "User 3"],
        "messages": [
            { "sender": "User 1", "content": "Hello" },
            { "sender": "User 2", "content": "Hi" },
            { "sender": "User 3", "content": "Hey" }
        ]
    },
    {
        "name": "Group 2",
        "id": 1,
        "members": ["User 1", "User 2", "User 3"],
        "messages": [
            { "sender": "User 1", "content": "Hello" },
            { "sender": "User 2", "content": "Hi" },
            { "sender": "User 3", "content": "Hey" }
        ]
    },
    {
        "name": "Group 3",
        "id": 2,
        "members": ["User 1", "User 2", "User 3"],
        "messages": [
            { "sender": "User 1", "content": "Hello" },
            { "sender": "User 2", "content": "Hi" },
            { "sender": "User 3", "content": "Hey" }
        ]
    }
]

class GroupsHandler(tornado.web.RequestHandler):
    def get(self):
        if self.request.path.endswith("/groups"):
            response = group_info
            self.set_header("Content-Type", "application/json")
            self.write(json.dumps(response))
        else:
            response = group_info[int(self.request.path.split("/")[-1])]
            self.set_header("Content-Type", "application/json")
            self.write(json.dumps(response))

def make_app():
    return tornado.web.Application([
        (r"/user/login", LoginHandler),
        (r"/showcase", ShowcaseHandler),
        (r"/user/inbox", InboxHandler),
        (r"/groups(?:\/\d+)?$", GroupsHandler)
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(8080)
    print("Server is running on http://localhost:8080")
    tornado.ioloop.IOLoop.current().start()
