DROP DATABASE IF EXISTS bbooked;
CREATE DATABASE bbooked;
USE bbooked;
--DROP USER 'bbooked'@'localhost';
--CREATE USER 'bbooked'@'localhost' IDENTIFIED BY 'bbooked';
GRANT ALL on bbooked.* TO 'bbooked'@'localhost';

CREATE TABLE Role (
id          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
role        varchar(255)
);

CREATE TABLE User (
id          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
username    varchar(255),
password    varchar(255),
email       varchar(255),
role        INTEGER NOT NULL,
blocked     BOOLEAN,
session     VARCHAR(255),
FOREIGN KEY (role) REFERENCES Role(id)
);

CREATE TABLE Tag (
id          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
tag        VARCHAR(255)
);

CREATE TABLE Status (
id          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
status      VARCHAR(255)
);

CREATE TABLE Tour (
id          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
name        varchar(255),
description varchar(255),
user        INTEGER NOT NULL,
status      INTEGER NOT NULL,
hot         BOOLEAN,
hotel       INTEGER,
person      INTEGER NOT NULL,
price       DOUBLE,
percent     DOUBLE,
FOREIGN KEY (status) REFERENCES Status(id),
FOREIGN KEY (user) REFERENCES User(id)
);

CREATE TABLE TourTag (
tour        INTEGER NOT NULL,
tag        INTEGER NOT NULL,
FOREIGN KEY (tour) REFERENCES Tour(id),
FOREIGN KEY (tag) REFERENCES Tag(id)
);

INSERT INTO Role (id, role) values (1, 'root');
INSERT INTO Role (id, role) values (2, 'user');
INSERT INTO Role (id, role) values (3, 'manager');

INSERT INTO User (username, password, role) values ('root', 'admin', 1);
INSERT INTO User (username, password, role) values ('user', 'user', 2);
INSERT INTO User (username, password, role) values ('manager', 'manager', 3);

INSERT INTO Tag(id, tag) values (1, "rest");
INSERT INTO Tag(id, tag) values (2, "excursion");
INSERT INTO Tag(id, tag) values (3, "shopping");

INSERT INTO Status(id, status) values (1, "new");
INSERT INTO Status(id, status) values (2, "registered");
INSERT INTO Status(id, status) values (3, "paid");
INSERT INTO Status(id, status) values (4, "cancelled");

INSERT INTO Tour(id, name, description, user, status, hot, hotel, person, price) values
(1, "Monday tour", "A good shopping walk with a celebrity",
1, 1, false, 5, 1, 2147483647);
INSERT INTO TourTag (tour, tag) values (1, 2);
INSERT INTO TourTag (tour, tag) values (1, 3);

INSERT INTO Tour(id, name, description, user, status, hot, hotel, person, price) values
(2, "Tuesday tour", "Travel to Kyshyniv, massage at SPA saloon included",
1, 1, false, 4, 2, 10000);
INSERT INTO TourTag (tour, tag) values (2, 2);
INSERT INTO TourTag (tour, tag) values (2, 1);

INSERT INTO Tour(id, name, description, user, status, hot, hotel, person, price) values
(3, "Wednesday tour", "Kyiv sea, sailing & fishing",
1, 1, false, 3, 4, 7000);
INSERT INTO TourTag (tour, tag) values (3, 1);

INSERT INTO Tour(id, name, description, user, status, hot, hotel, person, price) values
(4, "Thursday tour", "Zakopane, excursion & shopping",
1, 1, false, 4, 4, 14000);
INSERT INTO TourTag (tour, tag) values (4, 2);
INSERT INTO TourTag (tour, tag) values (4, 3);

INSERT INTO Tour(id, name, description, user, status, hot, hotel, person, price) values
(5, "Friday tour", "Clubs, excursion & dancing",
1, 1, false, 1, 10, 5000);
INSERT INTO TourTag (tour, tag) values (5, 1);
INSERT INTO TourTag (tour, tag) values (5, 2);

INSERT INTO Tour(id, name, description, user, status, hot, hotel, person, price) values
(6, "Saturday tour", "Something really hot!",
1, 1, true, 5, 5, 50000);
INSERT INTO TourTag (tour, tag) values (6, 1);
INSERT INTO TourTag (tour, tag) values (6, 2);

INSERT INTO Tour(id, name, description, user, status, hot, hotel, person, price) values
(7, "Sunday tour", "Something really hot! Part 2!!!",
1, 1, true, 5, 5, 60000);
INSERT INTO TourTag (tour, tag) values (7, 1);

INSERT INTO Tour(id, name, description, user, status, hot, hotel, person, price) values
(8, "Test tour", "something to be tested",
1, 1, true, 5, 5, 60000);
INSERT INTO TourTag (tour, tag) values (7, 1);
