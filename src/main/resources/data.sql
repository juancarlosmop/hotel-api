INSERT INTO users (email, password, full_name, role,status) VALUES
('user@example.com', '$2a$12$jZfbjErC2Y3fytwDTMp3RedphQ45rl5skejOvF8StePa0fsiYmytu', 'User', 'USER',true);
INSERT INTO users (email, password, full_name, role,status) VALUES
('admin@example.com', '$2a$12$jZfbjErC2Y3fytwDTMp3RedphQ45rl5skejOvF8StePa0fsiYmytu', 'User', 'ADMIN',true);
INSERT INTO rooms (number, type, description, price_per_night, available)
VALUES ('101', 'STANDARD', 'A comfortable standard room', 850.00, true);
