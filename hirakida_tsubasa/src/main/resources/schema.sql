DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id serial NOT NULL,
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE posts
(
    id serial NOT NULL,
    body varchar(255) NOT NULL,
    place varchar(255) NOT NULL,
    image_url varchar(255) NOT NULL,
    user_id INT NOT NULL REFERENCES "users" (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE comments
(
    id serial NOT NULL,
    body varchar(255) NOT NULL,
    post_id INT NOT NULL REFERENCES "posts" (id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES "users" (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE likes
(
    id serial NOT NULL,
    user_id INT NOT NULL REFERENCES "users" (id) ON DELETE CASCADE,
    post_id INT NOT NULL REFERENCES "posts" (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);