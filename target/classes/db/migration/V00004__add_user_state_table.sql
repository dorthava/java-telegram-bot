CREATE TABLE user_state (
   id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   chat_id TEXT,
   note_id INT,
   state INT,
   FOREIGN KEY (chat_id) REFERENCES tg_user(chat_id)
);