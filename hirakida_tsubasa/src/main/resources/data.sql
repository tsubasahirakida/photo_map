INSERT INTO users
(name, email, password) VALUES 
-- password:test1
('test1', 'test1@sample.com', '$2a$10$sNw8FsZhFk3gcTusX7361.Y/98FMzmmtpKHw8zVctwvNbSwjrw2rK'),
-- password:test2
('test2', 'test2@sample.com', '$2a$10$c23BTh1etbJp1uhZrsuNxem3swNvm.GoXRRwWiFdVgsgFHp/hloF.');

INSERT INTO posts
(body, place, image_url, user_id) VALUES 
('テスト本文1', 'AMBL株式会社', 'src\main\resources\static\images\デフォルトユーザー画像.png', 1),
('テスト本文2', 'ハチ公前広場', 'src\main\resources\static\images\ハチ公前広場.jpg', 2);

INSERT INTO comments
(body, post_id, user_id) VALUES 
('いい写真だね！', 1, 2);

INSERT INTO likes
(user_id, post_id) VALUES 
(1, 1);