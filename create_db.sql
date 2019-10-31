CREATE TABLE users
(id INTEGER NOT NULL,
username VARCHAR(50) NOT NULL,
email VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
created_at INTEGER,
PRIMARY KEY (id));

CREATE TABLE auth
(id INTEGER NOT NULL,
user_id INTEGER NOT NULL,
expires_at INTEGER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY(user_id) REFERENCES users(id));

CREATE TABLE time_periods
(id INTEGER NOT NULL,
user_id INTEGER NOT NULL,
start_at INTEGER NOT NULL,
end_at INTEGER NOT NULL,
deleted_at INTEGER,
PRIMARY KEY (id),
FOREIGN KEY(user_id) REFERENCES users(id));


CREATE TABLE categories
(id INTEGER NOT NULL,
description VARCHAR(100),
user_id INTEGER NOT NULL,
deleted_at INTEGER,
PRIMARY KEY (id),
FOREIGN KEY(user_id) REFERENCES users(id));

CREATE TABLE events
(id INTEGER NOT NULL,
category_id INTEGER NOT NULL,
description VARCHAR(100),
start_at INTEGER NOT NULL,
end_at INTEGER NOT NULL,
user_id INTEGER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY(user_id) REFERENCES users(id),
FOREIGN KEY(category_id) REFERENCES categories(id));
