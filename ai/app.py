import tornado.ioloop
import tornado.web
import tornado.websocket
import json
from openai import OpenAI
import os
import random
import toml

with open("ai.toml") as f:
    config = toml.load(f)

os.environ["http_proxy"] = config["proxy"]["http"]
os.environ["https_proxy"] = config["proxy"]["https"]
os.environ["OPENAI_API_KEY"] = config["openai"]["api_key"]
openai = OpenAI(config["openai"]["api_key"])
preprompt = """
You now need to answer the questions provided in the QUESTION section based on the information given in the CONTEXT section.

[CONTEXT]
The following is a list of user's events:
<USER_EVENTS>

The following is all events in the system:
<EVENTS>

Just now, you had a conversation with the user:
<HISTORY>

Please answer, <QUESTION>
"""

def make_rest_response(data, message="", code=0):
    return json.dumps({"code": code, "message": message, "data": data})

class AIHandler(tornado.web.RequestHandler):
    def get(self):
        """
        GET /ai
        
        Request:
            - userEvents: List of user's events
            - allEvents: List of all events in the system
            - history: Conversation history with the user
            - question: Question to ask the model

        Response:
            - answer: Answer to the question
        """
        userEvents = self.get_argument("userEvents")
        allEvents = self.get_argument("allEvents")
        history = self.get_argument("history")
        question = self.get_argument("question")

        prompt = preprompt.replace("<USER_EVENTS>", userEvents).replace("<EVENTS>", allEvents).replace("<HISTORY>", history).replace("<QUESTION>", question)
        try:
            answer = openai.complete(prompt)
        except Exception as e:
            answer = str(e)
        self.write(make_rest_response({"answer": answer}))

def make_app():
    return tornado.web.Application([
        (r"/ai", AIHandler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(9262)
    print("Server is running on http://localhost:9262")
    tornado.ioloop.IOLoop.current().start()
