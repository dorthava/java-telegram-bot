CREATE TABLE note (
   id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   chat_id TEXT NOT NULL,
   content TEXT,
   active BOOLEAN NOT NULL,
   notification_time TIMESTAMP,
   FOREIGN KEY (chat_id) REFERENCES tg_user(chat_id)
);