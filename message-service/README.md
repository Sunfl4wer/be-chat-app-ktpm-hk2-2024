# be-chat-app-ktpm-hk2-2024
Backend code for a simple chat app

# API Spec
`http://localhost:8080/chatapp/swagger-ui/index.html#/`

# UserManagement service (localhost:8099)
Backend code for UserManagement service.
![image](https://github.com/Sunfl4wer/be-chat-app-ktpm-hk2-2024/assets/129239083/4a5ad888-7ceb-4e51-891a-2b1cb57238d9)

Prerequisites: Setup system environment variables - - DEVDB-URL, DEVDB-USERNAME, DEVDB-PASSWORD [Notion Link](https://www.notion.so/Development-Environment-423deb424f8944ce820da5a931132039#001129668dd242b68977a64b5949731c)

API Documentation Swagger UI: Please use this endpoint - "http://localhost:8099/swagger-ui/index.html#/"

**Test User:{    "phoneNumber":"01234567895",    "password":"Test_regist#123"}**

API Endpoints:
- auth-controller (no authorization needed):
  + POST /auth/register: register a user account with role = USER. Requires unique phoneNumber and username.
    - phoneNumber: require number length between 10-11 digits. Can have '+' sign to indicate international format.
    - username: Username can only contain alphanumeric characters, dots, dashes, and underscores. 3-15 characters
    - password: Password must be at least 8 characters long, contain at least one number, one uppercase letter, one lowercase letter, and one special character. 8-63 characters
  + POST /auth/login: login user with phoneNumber & password. Response JWT tokens if successful.
  + POST/GET /logout: logout current user. Requires JWT tokens from /auth/login (Token Bearer authorization).

- chat-user-controller (USER role authorization needed, Token Bearer method):
  + GET /users/{phoneNumber}: get a single user info by phoneNumber.
  + PUT /users/{phoneNumber}: update a single user info (username, password, email_address, avatar_url, first_name, last_name, birth_date only).
  + GET /users: unused.
  + DELETE /users/{phoneNumber}: unused
 
- friendship-controller (USER role authorization needed, Token Bearer method):
  + PUT /friends/{senderPhoneNumber}/{receiverPhoneNumber}: update friend request status of sender & receiver. (PENDING, ACCEPTED, DECLINED, BLOCKED).
  + GET /friends/pending/{currentUserPhoneNumber}: get a phone number list of incoming pending request for current user phonenumber.
  + GET /friends/accepted/{currentUserPhoneNumber}: get a phone number list of accepted request (or actual friends) for current user phonenumber..
  + POST /friends: create friend request with PENDING status. Requires both sender and receiver phone number in request body. If both sender and receiver try to send friend request to each other, the 2nd request automatically update the 1st request to be accepted. (no possible multiple friend request exists for two users)
