# 🏦 Banking Management System (Core Java)

A console-based Banking Management System developed using **Core Java** by following **Object-Oriented Programming (OOP)** principles and a layered architecture.

This project demonstrates how a real-world banking application can be designed using Java concepts such as encapsulation, inheritance, polymorphism, abstraction, custom exception handling, repository pattern, and constructor-based dependency injection.

---

# 📌 Features

- Create Savings Account
- Create Current Account
- Login Authentication
- Check Account Balance
- Deposit Money
- Withdraw Money
- View Transaction History
- Display All Accounts
- SMS & Email Notification (Simulation)
- Custom Exception Handling

---

# 🛠 Technologies Used

- Java
- IntelliJ IDEA
- Git
- GitHub

---

# 📂 Project Architecture

```
BankingManagementSystem
│
├── src
│   │
│   ├── app
│   │     └── Main.java
│   │
│   ├── model
│   │     ├── Account.java
│   │     ├── SavingsAccount.java
│   │     ├── CurrentAccount.java
│   │     └── Transaction.java
│   │
│   ├── service
│   │     ├── AccountService.java
│   │     ├── AuthenticationService.java
│   │     └── TransactionService.java
│   │
│   ├── repository
│   │     └── AccountRepository.java
│   │
│   ├── notification
│   │     ├── NotificationService.java
│   │     ├── SMSNotification.java
│   │     └── EmailNotification.java
│   │
│   └── exception
│         ├── BankException.java
│         ├── InvalidAmountException.java
│         ├── InvalidPinException.java
│         ├── AccountNotFoundException.java
│         ├── InsufficientBalanceException.java
│         └── AccountAlreadyExistsException.java
│
└── README.md
```

---

# 🏗 Architecture Overview

```
                 User
                   │
                   ▼
              Main.java
                   │
        ┌──────────┼──────────┐
        ▼          ▼          ▼
 AccountService Authentication TransactionService
        │          │          │
        └──────────┼──────────┘
                   ▼
          AccountRepository
                   │
                   ▼
           ArrayList<Account>
```

---

# 📦 Package Responsibilities

### app

Contains the application entry point.

- Main.java

---

### model

Contains all domain entities.

- Account
- SavingsAccount
- CurrentAccount
- Transaction

---

### service

Contains business logic.

- Account creation
- Authentication
- Transactions

---

### repository

Acts as the data access layer.

Responsible for

- storing accounts
- searching accounts
- retrieving account information

---

### notification

Simulates notification services.

- SMS Notification
- Email Notification

---

### exception

Contains all custom exceptions used throughout the application.

---

# 🚀 Object-Oriented Programming Concepts Used

✅ Classes & Objects

✅ Encapsulation

✅ Inheritance

✅ Polymorphism

✅ Abstraction

✅ Interfaces

---

# ⚠ Exception Handling

Implemented custom exception hierarchy.

- BankException
- InvalidAmountException
- InvalidPinException
- AccountNotFoundException
- AccountAlreadyExistsException
- InsufficientBalanceException

---

# 📚 Java Concepts Covered

- Packages
- Collections (ArrayList)
- Constructor Injection
- Repository Pattern
- Interfaces
- Method Overriding
- Method Overloading
- Exception Propagation
- Custom Exceptions

---

# 🔮 Future Enhancements

- File Handling
- Serialization
- JDBC
- MySQL Database
- Spring Boot REST API
- Spring Security
- JUnit Testing

---

# 👨‍💻 Developed By

**Adamu Alamuri**