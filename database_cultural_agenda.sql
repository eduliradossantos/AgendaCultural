CREATE DATABASE agenda_cultural;
USE agenda_cultural;

CREATE TABLE User (
    id_user BINARY(16) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Category (
    id_category BINARY(16) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE Event (
    id_event BINARY(16) PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    location VARCHAR(200),
    date_time DATETIME,
    category_id BINARY(16),
    created_by BINARY(16),
    FOREIGN KEY (category_id) REFERENCES Category(id_category) ON DELETE SET NULL,
    FOREIGN KEY (created_by) REFERENCES User(id_user) ON DELETE CASCADE
);

CREATE TABLE Comment (
    id_comment BINARY(16) PRIMARY KEY,
    text TEXT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_id BINARY(16),
    event_id BINARY(16),
    FOREIGN KEY (user_id) REFERENCES User(id_user) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Event(id_event) ON DELETE CASCADE
);

CREATE TABLE Favorite (
    id_favorite BINARY(16) PRIMARY KEY,
    user_id BINARY(16),
    event_id BINARY(16),
    favorited_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id_user) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Event(id_event) ON DELETE CASCADE
);
