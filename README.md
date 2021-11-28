# Read Me First
User authentication api signup,login, delete user ,change password with java and spring boot application

# Getting Started
Run docker-compose up to start mysql docker container.

### Api Documentation

* Register user call :
 ``` 
   curl -H "Content-Type: application/json" -X POST -d '{
       "name":"mesut",
      "email":"mesut@gmail.com",
      "password":"123",
      "confirmPassword":"123"
   }'  http://localhost:8080/user/register
  ```

* Login call :
 ``` 
  curl -H "Content-Type: application/json" -X POST -d '{
     "email":"mesut@gmail.com",
     "password":"123"
  }'  http://localhost:8080/user/login
  ```

* Change Password call :
 ``` 
 curl -H "Content-Type: application/json" -X POST -d '{
     "email":"mesut@gmail.com",
     "oldPassword":"123",
     "newPassword":"Start"
 }'  http://localhost:8080/user/changepassword
  ```
  
  * Delete user call :
   ``` 
  curl -H "Content-Type: application/json" -X DELETE -d '{
      "email":"mesut@gmail.com",
      "password":"Start"
  }'  http://localhost:8080/user
   
