# Simplified Banking System

Personal study project simulating a simplified payment/banking platform, built to practice backend development concepts such as REST APIs, transactional operations, layered architecture (Controller/Service/Repository), and integration with external services.

## Overview

The system allows users to hold a wallet balance and transfer money between accounts. There are two types of accounts:

- **Common users**: can send and receive money.
- **Merchants**: can only receive money (cannot send transfers).

## Business Rules

- Every account requires `Full Name`, `CPF/document`, `email`, and `password`. Both document and email must be unique in the system.
- Users can transfer money to merchants and to other users.
- Merchants can only receive transfers — they cannot send money to anyone.
- Before completing a transfer, the sender's balance is validated.
- Transfers are processed as a **transaction**: if anything fails, the operation is fully reverted and the sender's balance is restored.
- Before finalizing a transfer, an external authorization service is consulted to approve or deny the operation.
- After a successful transfer, a notification (email/SMS) is sent to the receiver through an external notification service. This service may be unstable or unavailable, and that shouldn't block the transfer itself.

## Transfer Endpoint

```
POST /transfer
Content-Type: application/json

{
  "value": 100.0,
  "payer": 4,
  "payee": 15
}
```

## Tech Stack

- Java
- Spring Boot
- Maven
- (add here: database, testing libraries, etc. as the project evolves)

## Goals of This Project

This project was built as a personal study exercise to practice:

- RESTful API design
- Clean and organized code structure
- SOLID principles and design patterns
- Transactional operations and data consistency
- Integration with external (mocked) services
- Unit and integration testing
- Relational database modeling

## Running the Project

```bash
./mvnw spring-boot:run
```

## Status

🚧 Work in progress — this is an ongoing study project.
