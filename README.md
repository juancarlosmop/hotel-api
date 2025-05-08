# Hotel API

This project is a full REST API built with Spring Boot, featuring authentication and authorization.

The application defines two roles: **USER** and **ADMIN**. Each role has access only to specific URLs according to its permissions.

The project follows best practices and includes unit tests to ensure code quality.

## 🚀 TECHNOLOGIES

- **Spring Boot** – RESTful microservice framework.  
- **Spring Security** – Role-based authorization.  
- **JPA** – Data persistence.  
- **JWT** – JSON Web Token for stateless authentication.  
- **Swagger** – API documentation.  
- **JUnit** – Unit testing framework.  
- **Mockito** – Mocking framework for tests.  
- **Docker** – Service containerization. 

## 🚀 Build and Run the Application with Docker

### 1. Compile And install the Java application
mvn clean package

docker build -t hotel .

docker run -p 8080:8080 hotel


## ✅ Evidence
ACCESSS TO FOLLOW URL:http://localhost:8080/swagger-ui/index.html
![image](https://github.com/user-attachments/assets/b54ab5dc-1b15-4320-afb2-0266b4b5d23a)

### 1. Login as USER or ADMIN
![image](https://github.com/user-attachments/assets/8f08b5da-94a8-45a2-aba7-9053bab421b5)

### 2. COPY AND PASTE THE TOKEN 
![image](https://github.com/user-attachments/assets/6cf46244-a3bc-4f44-b3b6-18af02202629)
![image](https://github.com/user-attachments/assets/03ab4a5f-8ed1-484b-aa7b-5b6b770c663f)

### 3. TEST THE ENPOINTS
![image](https://github.com/user-attachments/assets/2f131ede-b32f-4cc5-b14e-78787f68e8a8)

IF you don't have the correct Authorization
![image](https://github.com/user-attachments/assets/ce91913e-afae-4679-8792-8328aa1ee54d)

## ✅ TESTS
![image](https://github.com/user-attachments/assets/574e9a37-1bdf-4c53-b2cc-eaf79ca190c9)
![image](https://github.com/user-attachments/assets/b09ed90a-ddc2-46f5-9627-005e9fc0801d)







