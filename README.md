📘 Bank Account Management API

This project is built to practice core Spring Boot skills including REST API design, DTO validation, service layering, database persistence, and mapping between DTOs and entities.
It simulates a simple banking system where users can create, update, delete, and manage bank accounts.

✨ Features

Create a new account (UUID auto-generated)

Get all accounts with pagination + sorting

Find account by account number (UUID)

Find accounts by customer ID

Update account information

Delete an account by account number

Soft delete (disable) an account using isDeleted = true

Validation using jakarta.validation

Standard API response wrapper (ApiResponse<T>)

🛠 Tech Stack

Java 17+

Spring Boot 3

Spring Web MVC

Spring Data JPA

PostgreSQL

Hibernate

MapStruct

Lombok

Maven/Gradle

Postman for testing

⚙️ Configuration
1. Database Setup

Create a PostgreSQL database:

CREATE DATABASE bankaccountdb;

2. Application Configuration

Add database credentials inside application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/bankaccountdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true


You may replace username/password with your own PostgreSQL credentials.

📡 API Endpoints
📍 Account Endpoints
➤ Create Account

POST /api/accounts

{
  "customerId": 1,
  "balance": 1500.00,
  "accountType": "SAVINGS"
}

➤ Get All Accounts (Paged + Sorted)

GET /api/accounts?page=0&size=10&sortBy=accountNo&direction=ASC

➤ Get Account by Account Number

GET /api/accounts/{accountNo}

➤ Get Accounts by Customer

GET /api/accounts/customer/{customerId}

➤ Update Account

PUT /api/accounts/{accountNo}

{
  "balance": 2000.00,
  "accountType": "CHECKING"
}

➤ Delete Account

DELETE /api/accounts/{accountNo}

➤ Disable Account (Soft Delete)

PATCH /api/accounts/{accountNo}/disable

▶️ How to Run the Project
1. Clone the Repository
git clone https://github.com/SakPhearoth/BankAccountManagement.git
cd BankAccountManagement

2. Configure PostgreSQL

Make sure PostgreSQL is running.
Create a database:

CREATE DATABASE bankaccountdb;


Update credentials inside application.properties.

3. Build & Run the Application
If using Maven:
./mvnw spring-boot:run

If using Gradle:
./gradlew bootRun

4. Test API

Use Postman or browser to access:

http://localhost:8080/api/accounts
