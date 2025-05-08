# Hotel Api
This proyect is full api Rest created in spring boot whith Authorization and Authentification

This app have two roles the role of USER and ADMIN, then each role can acces a only the speficic urls

Also the app have the best practice and have unit test that garntize the quality of the code

## 🚀 THECHNOLOGIES 
- **Spring Boot** – RESTful microservice.
- **Spring Security** – Autherizaction of roles.
- **JPA** – Percistence of data.
- **JWT** – RESTful microservice.
- **Swagger** – Api Documentation.
- **Junit** – Unit test.
- **Mockito** – Mock test of the app.
- **Docker** – Service containerization.

## 🚀 Build and Run the Application with Docker

### 1. Compile the Java application
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







