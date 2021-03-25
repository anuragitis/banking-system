# banking-system
Use branch master

After server is up and running at 8080
insert these two rows in role table

INSERT INTO roles(name) VALUES('ROLE_USER');

INSERT INTO roles(name) VALUES('ROLE_ADMIN');

use /register api from postman collection to register user 

and /login to login user (this will return bearer token which needs to be used in every other api)

DatabaseUsed - postgresql
Authentication - JWT
