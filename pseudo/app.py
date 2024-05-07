import tornado.ioloop
import tornado.web
import tornado.websocket
import json
from openai import OpenAI
import os
import random

os.environ["http_proxy"] = "http://127.0.0.1:7890"
os.environ["https_proxy"] = "http://127.0.0.1:7890"

# key = "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"
# client = OpenAI(api_key=key)
pseudo_responses = [
    "There are a total of <num> events that will take place at <time>. <br/>For example, <event1>, <event2>, and <event3>.<br/>\
    Which one would you like to know more about?",
]

connections = []

class LoginHandler(tornado.web.RequestHandler):
    def post(self):
        data = json.loads(self.request.body)
        username = data["nameOrEmail"]
        password = data["password"]
        
        if username == "admin" and password == "password":
            response = {"success": True, "message": "Login successful", "user": "admin", "token": "123", "user_role": 1, "status": 200, "username": "admin"}
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
        "name": "I love Genshin Impact!",
        "id": 0,
        "avatar": "https://fastcdn.hoyoverse.com/mi18n/hk4e_global/m20240416hy38hwmjuo/upload/3a44c8360866622e06d44738b7a643d0_1796956232662757728.jpg?x-oss-process=image/format,webp/quality,Q_90",
        "pinned": { 
            "content": "Welcome to the Genshin Impact fan club! We are a group of Genshin Impact fans who love to play the game together. Feel free to join us for some fun and exciting adventures in the world of Teyvat!",
            "time": "2024-02-21 18:30:00", 
            "sender": "olzzz07"
        },
        "members": [
            {
                "name": "olzzz07",
                "id": 123456,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456322.png"
            },
            {
                "name": "np-hard",
                "id": 123457,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456323.png"
            },
            {
                "name": "12211634",
                "id": 123458,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456324.png"
            },
            {
                "name": "LightShadow",
                "id": 123459,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456325.png"
            },
            {
                "name": "MoonBeam",
                "id": 123460,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456326.png"
            },
            {
                "name": "StarDust",
                "id": 123461,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456327.png"
            },
            {
                "name": "DreamCatcher",
                "id": 123462,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456328.png"
            },
            {
                "name": "OceanWhisper",
                "id": 123463,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456329.png"
            },
            {
                "name": "SkyWatcher",
                "id": 123464,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456330.png"
            },
            {
                "name": "FireFly",
                "id": 123465,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456331.png"
            },
            {
                "name": "ForestGlow",
                "id": 123466,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456332.png"
            },
            {
                "name": "MountainEcho",
                "id": 123467,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456333.png"
            },
            {
                "name": "RiverSong",
                "id": 123468,
                "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456334.png"
            }
        ],
        "messages": [
            { "sender": "olzzz07", "content": "Hello", "time": "2024-02-21 18:30:01", "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456322.png" },
            { "sender": "np-hard", "content": "How often do you play Genshin bro?", "time": "2024-02-21 18:30:02", "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456322.png"},
            { "sender": "12211634", "content": "I play it every day!", "time": "2024-02-21 18:31:00", "avatar": "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456322.png" },
            { "sender": "LightShadow", "content": "I love the game too!", "time": "2024-02-21 18:31:01"},
            { "sender": "MoonBeam", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:02"},
            { "sender": "StarDust", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:03"},
            { "sender": "DreamCatcher", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:04"},
            { "sender": "OceanWhisper", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:05"},
            { "sender": "SkyWatcher", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:06"},
            { "sender": "FireFly", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:07"},
            { "sender": "ForestGlow", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:08"},
            { "sender": "MountainEcho", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:09"},
            { "sender": "RiverSong", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:10"},
            { "sender": "olzzz07", "content": "Umm, I think we have enough fans here.", "time": "2024-02-21 18:31:11"},
            { "sender": "np-hard", "content": "Haha, I think so too.", "time": "2024-02-21 18:31:12"},
            { "sender": "12211634", "content": "Yeah, we should play together!", "time": "2024-02-21 18:31:13"},
            { "sender": "LightShadow", "content": "I'm in!", "time": "2024-02-21 18:31:14"},
            { "sender": "MoonBeam", "content": "Me too!", "time": "2024-02-21 18:31:15" },
            { "sender": "DreamCatcher", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:16" },
            { "sender": "OceanWhisper", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:17" },
            { "sender": "SkyWatcher", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:18" },
            { "sender": "FireFly", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:19" },
            { "sender": "ForestGlow", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:20" },
            { "sender": "MountainEcho", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:21" },
            { "sender": "RiverSong", "content": "I'm a big fan of Genshin Impact!", "time": "2024-02-21 18:31:22" },
            { "sender": "olzzz07", "content": "Umm, I think we have enough fans here.", "time": "2024-02-21 18:31:23" },
            { "sender": "np-hard", "content": "Haha, I think so too.", "time": "2024-02-21 18:31:24" },
            { "sender": "12211634", "content": "Yeah, we should play together!", "time": "2024-02-21 18:31:25" },
            { "sender": "LightShadow", "content": "I'm in!", "time": "2024-02-21 18:31:26" },
            { "sender": "MoonBeam", "content": "Me too!", "time": "2024-02-21 18:31:27" },
            { "sender": "StarDust", "content": "I'm in!", "time": "2024-02-21 18:31:28" },
            { "sender": "DreamCatcher", "content": "I'm in!", "time": "2024-02-21 18:31:29" },
            { "sender": "OceanWhisper", "content": "I'm in!", "time": "2024-02-21 18:31:30" },
            { "sender": "SkyWatcher", "content": "I'm in!", "time": "2024-02-21 18:31:31" },
            { "sender": "FireFly", "content": "I'm in!", "time": "2024-02-21 18:31:32" },
            { "sender": "np-hard", "content": "Wait, could *someone* help me with the `Abyss`?", "time": "2024-02-21 18:31:33" },
            { "sender": "olzzz07", "content": "Lol, np-hard, I guess you need to finish your coding homework first.", "time": "2024-02-21 18:31:34" },
            { "sender": "np-hard", "content": "Haha, I'm on it! If you need to see this: ```cpp\n#include <iostream>\nint main() {\n    std::cout << \"Hello, World!\" << std::endl;\n    return 0;\n}\n```", "time": "2024-02-21 18:31:35" },
            { "sender": "olzzz07", "content": "Nice code! Now try Segment Tree.", "time": "2024-02-21 18:31:36" },
            { "sender": "np-hard", "content": "That's **super** easy lmao. See: ```cpp\n#include <iostream>\n#include<vector>\nusing namespace std;\n\nclass SegmentTree {\npublic:\n    SegmentTree(vector<int>& nums) {\n        n = nums.size();\n        tree.resize(4 * n);\n        buildTree(nums, 0, 0, n - 1);\n    }\n\n    void buildTree(vector<int>& nums, int index, int left, int right) {\n        if (left == right) {\n            tree[index] = nums[left];\n            return;\n        }\n        int mid = left + (right - left) / 2;\n        buildTree(nums, 2 * index + 1, left, mid);\n        buildTree(nums, 2 * index + 2, mid + 1, right);\n        tree[index] = tree[2 * index + 1] + tree[2 * index + 2];\n    }\n\nprivate:\n    int n;\n    vector<int> tree;\n};\n```", "time": "2024-02-21 18:31:37" },
            { "sender": "np-hard", "content": "Now, who can tell me the time complexity of this code?", "time": "2024-02-21 18:31:38" },
            { "sender": "olzzz07", "content": "O(n) time complexity.", "time": "2024-02-21 18:31:39" },
            { "sender": "np-hard", "content": "Correct! You're a good student!", "time": "2024-02-21 18:31:40" },
            { "sender": "olzzz07", "content": "Thanks! I'm always here to help you! And for additional resources, check out this [link](https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/).", "time": "2024-02-21 18:31:41" },
            { "sender": "np-hard", "content": "Thanks! I'll check it out! ![a random image](https://cdn.iconscout.com/icon/free/png-256/avatar-370-456350.png)", "time": "2024-02-21 18:31:42" },
            { "sender": "olzzz07", "content": "You're welcome! Have a nice day!", "time": "2024-02-21 18:31:43" },
            { "sender": "np-hard", "content": "You too!", "time": "2024-02-21 18:31:44" }
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

class RegisterHandler(tornado.web.RequestHandler):
    def post(self):
        data = json.loads(self.request.body)
        
        response = {"success": True, "message": "Registration successful"}
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

class VerifyEmailHandler(tornado.web.RequestHandler):
    def post(self):
        response = {"success": True, "message": "Email verified", "role": 1}
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

class WebSocketHandler(tornado.websocket.WebSocketHandler):
    def check_origin(self, origin):
        return True

    def open(self):
        connections.append(self)
        print("WebSocket opened")

    def connect(self):
        print("WebSocket opened")

    def on_message(self, message):
        # self.write_message(u"You said: " + message)
        print("Received message: " + message)
        print(message)
        group_info[0]["messages"].append(json.loads(message))
        # add field `avatar` to the message
        group_info[0]["messages"][-1]["avatar"] = "https://cdn.iconscout.com/icon/free/png-256/avatar-370-456322.png"
        # Broadcast message
        for conn in connections:
            conn.write_message(message)

    def on_close(self):
        connections.remove(self)
        print("WebSocket closed")

class AIHandler(tornado.web.RequestHandler):
    def post(self):
        data = json.loads(self.request.body)
        message = data["message"]
        # response = client.chat.completions.create(
        # model="gpt-3.5-turbo-0125",
        # response_format={ "type": "json_object" },
        # messages=[
        #     {"role": "system", "content": "You are a helpful assistant designed to output JSON."},
        #     {"role": "user", "content": message}
        # ]
        # ).choices[0].message.content
        resp = random.choice(pseudo_responses)
        resp = resp.replace("<num>", str(random.randint(1, 10)))
        resp = resp.replace("<time>", "<strong>tomorrow</strong>")
        resp = resp.replace("<event1>", "<strong>English Corner</strong>")
        resp = resp.replace("<event2>", "<strong>GRE General Test</strong>")
        resp = resp.replace("<event3>", "<strong>the midterm for Algorithm Design and Analysis SE2 Spring 2024</strong>")
        response = f'{{"message": "{resp}"}}'
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))


class PostsHandler(tornado.web.RequestHandler):
    def get(self):
        response = [
            {
                "id": 1,
                "title": "Animal Science Major advice?",
                "content":
                "I'm a freshman and I'm thinking about majoring in Animal Science. I'm not sure if it's the right choice for me. Can anyone give me some advice?",
                "sender": "User 1",
                "time": "12:00",
                "likes": 1,
                "tags": ["Animal Science", "Major Advice"],
            },
            {
                "id": 2,
                "title": "How to get into the College of Engineering?",
                "content":
                "I'm a high school student and I'm interested in majoring in Computer Science. I'm not sure how to get into the College of Engineering. Can anyone give me some advice?",
                "sender": "User 2",
                "time": "12:00",
                "likes": 2,
                "tags": ["College of Engineering", "Computer Science"],
            },
            {
                "id": 3,
                "title": "SUSTech or PKU?",
                "content":
                "So SUSTech and PKU both accepted me. I'm not sure which one to choose. Can anyone give me some advice?",
                "sender": "User 3",
                "time": "12:10",
                "likes": 3,
                "tags": ["SUSTech", "PKU"],
            }
        ]
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

discuss_info = [
    { 
        "id": 0, 
        "title": "Crazy to spend 70,000 to go to Davis?",
        "content": "So I have been pretty dead set on attending UC Davis in the fall, until financial aid packages came out and I saw that I would be paying nearly 70k a year. Compare this to my best offer which is to attend LSU (Louisiana State University) for less than 17,000 a year. I am a pre vet major looking to attend veterinary school so there is a pathway towards me making more money, but is this amount of debt simply not worth it?", 
        "sender_name": "Ok Championship", 
        "sender_id": "114514", 
        "time": "11:00",
        "likes": 7,
        "tags": ["UC Davis", "LSU", "Pre Vet Major", "Veterinary School", "Debt"]
    },
    {
        "id": 1,
        "title": "Re: Crazy to spend 70,000 to go to Davis?",
        "content": "I think it's a good idea to go to LSU. You can save a lot of money and still get a good education. You can always transfer to Davis later if you want to.", 
        "sender_name": "Tiger Fan", 
        "sender_id": "1919810", 
        "time": "11:05",
        "likes": 5
    },
    {
        "id": 2,
        "title": "Re: Crazy to spend 70,000 to go to Davis?",
        "content": "Adding on to what everyone seems to have already said... don't spend this amount on your undergraduate degree. UC Davis has been a great school, but we're paying in-state tuition (and even that feels a bit steep for me). Honestly, for Californians, the two-year community college, then transfer to a UC seems the best option. I know someone who was devastated because they didn't get accepted into a UC, reluctantly went the community college route, and now it's time to transfer. Their choices: Berkeley or Davis. I think that worked out for them!\n\nBut back to your question. It's not worth the expense. Got to Louisiana State University.",
        "sender_name": "Aggie",
        "sender_id": "334455",
        "time": "11:15",
        "likes": 3
    },
    {
        "id": 3,
        "title": "Re: Crazy to spend 70,000 to go to Davis?",
        "content": "I agree with Tiger Fan. You can always transfer to Davis later if you want to.", 
        "sender_name": "LSU Fan", 
        "sender_id": "8101919", 
        "time": "11:10",
        "likes": 2
    }
]

class DiscussionsHandler(tornado.web.RequestHandler):
    def get(self):
        response = discuss_info
        print(response)
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

    def post(self):
        data = json.loads(self.request.body)
        print(data)
        response = {
            "id": 4,
            "title": data["title"],
            "content": data["content"],
            "sender_name": data["sender_name"],
            "sender_id": "123456",
            "time": data["time"],
            "likes": 0
        }
        discuss_info.append(response)
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

class UserAvatarHandler(tornado.web.RequestHandler):
    def get(self):
        # Return an image
        self.set_header("Content-Type", "image/png")
        with open("../galaguide/src/assets/logo.png", "rb") as f:
            self.write(f.read())

class DiscussionsLikeHandler(tornado.web.RequestHandler):
    def post(self):
        print(self.request)
        discuss_id = int(self.request.path.split("/")[-3])
        comment_id = int(self.request.path.split("/")[-1])
        discuss_info[comment_id]["likes"] += 1
        response = {"success": True, "message": "Liked", "likes": discuss_info[comment_id]["likes"]}
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

candidate_recommendations = [
    {
        "id": 1,
        "title": "Animal Science Major advice?",
        "content":
        "I'm a freshman and I'm thinking about majoring in Animal Science. I'm not sure if it's the right choice for me. Can anyone give me some advice?",
        "sender": "User 1",
        "time": "12:00",
        "likes": 1,
        "tags": ["Animal Science", "Major Advice"]
    },
    {
        "id": 2,
        "title": "Anyone ever take Bis 002a w/ Z. Jaramillo",
        "content": "I'm taking Bis 002a with Z. Jaramillo this quarter and I was wondering if anyone has taken it before. How is the class? How are the exams? Any tips?",
        "sender": "FrostyGG__3",
        "time": "12:10",
        "likes": 2,
        "tags": ["Bis 002a", "Z. Jaramillo"]
    },
    {
        "id": 3,
        "title": "Rejected from UC Davis MPH Program - Any Advice or Insights",
        "content": "Hey everyone, I'm reaching out to the community because I'm puzzled and a bit down after receiving some unexpected news. I'm a Global Disease Biology major and I recently applied to the Master of Public Health program at UC Davis. I thought my application essay was strong, and I put a lot of effort into making sure every part of my application was as good as it could be. I applied back in January, and just yesterday, I received my rejection letter. To add to the confusion, a friend of mine, who has a higher GPA than I do, also got rejected. We're both trying to wrap our heads around why we might have been turned down, especially considering UC Davis's program, while great, isn't typically viewed on the same tier as, say, Berkeley's. Does anyone have insights into the admissions process at UC Davis for their MPH program? Maybe there are factors I'm not considering, or perhaps the program is just far more competitive than I realized. Any advice or personal experiences with their public health grad school admissions would be greatly appreciated. How can I strengthen my application if I decide to reapply next year?",
        "sender": "CaliGirl",
        "time": "12:20",
        "likes": 3,
        "tags": ["UC Davis", "MPH Program", "Admissions"]
    }
]

class DiscussionsRecommendHandler(tornado.web.RequestHandler):
    def get(self):
        response = random.choices(candidate_recommendations, k=2)
        self.set_header("Content-Type", "application/json")
        self.write(json.dumps(response))

def make_app():
    return tornado.web.Application([
        (r"/user/login", LoginHandler),
        (r"/showcase", ShowcaseHandler),
        (r"/user/inbox", InboxHandler),
        (r"/groups(?:\/\d+)?$", GroupsHandler),
        (r"/user/register", RegisterHandler),
        (r"/user/verify-email", VerifyEmailHandler),
        (r".*/ws.*", WebSocketHandler),
        (r"/ai", AIHandler),
        (r"/posts", PostsHandler),
        (r"/discuss(?:\/\d+)/like(?:\/\d+)?$", DiscussionsLikeHandler),
        (r"/discuss(?:\/\d+)?$", DiscussionsHandler),
        (r"/user(?:\/\d+)/avatar", UserAvatarHandler),
        (r"/discuss(?:\/\d+)/related", DiscussionsRecommendHandler)
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(9260)
    print("Server is running on http://localhost:9260")
    tornado.ioloop.IOLoop.current().start()
