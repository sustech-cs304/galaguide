import sqlite3
import os

DATABASE = "real.db"

def create_db():
    if os.path.exists(DATABASE):
        os.remove(DATABASE)
    conn = sqlite3.connect(DATABASE)
    cursor = conn.cursor()
    cursor.execute("""
    CREATE TABLE users (
        token TEXT PRIMARY KEY,
        username TEXT NOT NULL,
        email TEXT NOT NULL,
        password TEXT NOT NULL,
        userRole INTEGER NOT NULL
    )
    """)
    cursor.execute("""
    INSERT INTO users (token, username, email, password, userRole)
    VALUES ("token", "test", "12211634@mail.sustech.edu.cn", "password", 0)
    """)