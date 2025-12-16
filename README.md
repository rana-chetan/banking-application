# Banking Application Backend

A clean and modular Java Spring Boot backend that provides essential banking features such as account creation, balance checks, fund transfers and transaction history.

---

## Features
- Create and manage user accounts
- Check account balance
- Transfer funds between accounts
- View transaction history
- Input validation for all requests
- Centralized exception handling
- Layered architecture for clean code
- Uses Spring Data JPA for database operations

---

## Tech Stack
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL or PostgreSQL
- Maven

---

## API Endpoints

### Account APIs
**Create Account**  
POST  
`/api/accounts`

**Get Account By Number**  
GET  
`/api/accounts/{accountNumber}`

**Check Balance**  
GET  
`/api/accounts/{accountNumber}/balance`

---

### Transaction APIs
**Transfer Funds**  
POST  
`/api/transactions/transfer`

**Get Transaction History**  
GET  
`/api/transactions/{accountNumber}`

---

## How to Run the Project

### 1. Clone the Repository
`git clone https://github.com/your-username/banking-application.git`

### 2. Go to the Project Directory
`cd banking-application`

### 3. Configure Your Database
Open `application.properties` and update the database settings:

### 4. Build and Run

---

## Folder Overview
- **controller**: REST API endpoints
- **service**: Business logic
- **repository**: Database access
- **entity**: Database tables as classes
- **dto**: Request and response objects
- **exception**: Custom errors and handlers

---

## Future Improvements
- Add unit tests and integration tests

---

## License
This project is open source and available under the MIT License.




