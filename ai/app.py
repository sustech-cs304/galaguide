import tornado.ioloop
import tornado.web
import tornado.websocket
import json
from openai import OpenAI
import os
import random
import toml
import argparse

parser = argparse.ArgumentParser(description="AI Server")
parser.add_argument("--config", type=str, default="ai.toml", help="Path to the config file")
parser.add_argument("--port", type=int, default=9262, help="Port to run the server")
parser.add_argument("--model", type=str, default="gpt-3.5-turbo", help="Model to use")
args = parser.parse_args()

with open(args.config, "r") as f:
    config = toml.load(f)

try:
    os.environ["http_proxy"] = config["proxy"]["http"]
    os.environ["https_proxy"] = config["proxy"]["https"]
    os.environ["OPENAI_API_KEY"] = config["openai"]["api_key"]
    if args.model.lower().startswith("gpt"):
        openai = OpenAI(config["openai"]["api_key"])
    else:
        import torch
        from transformers import AutoModel, AutoTokenizer
        device = "cuda" if torch.cuda.is_available() else "cpu"
        model = AutoModel.from_pretrained(args.model).to(device)
        tokenizer = AutoTokenizer.from_pretrained(args.model)
except Exception as e:
    print(f"Error: {str(e)}")
    
preprompt = """
You now need to answer the questions provided in the QUESTION section based on the information given in the CONTEXT section.

[CONTEXT]
The following is a list of user's events:
<USER_EVENTS>

The following is all events in the system:
<EVENTS>

Just now, you had a conversation with the user:
<HISTORY>

[QUESTION]
<QUESTION>
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
        
        if args.model.lower().startswith("gpt"):
            try:
                answer = openai.chat.completions.create(
                    model=args.model,
                    messages=[
                        {"role": "system", "content": prompt.split("[QUESTION]")[0]},
                        {"role": "user", "content": prompt.split("[QUESTION]")[1]}
                    ]
                ).choices[0].message.content
            except Exception as e:
                answer = str(e)
        else:
            inputs = tokenizer(prompt, return_tensors="pt", max_length=1024, truncation=True).to(device)
            output = model.generate(**inputs, max_length=1024, num_return_sequences=1, temperature=0.7)
            answer = tokenizer.decode(output[0], skip_special_tokens=True)

        self.write(make_rest_response({"answer": answer}))

def make_app():
    return tornado.web.Application([
        (r"/ai", AIHandler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(args.port)
    print(f"Server is running on http://localhost:{args.port}")
    tornado.ioloop.IOLoop.current().start()
