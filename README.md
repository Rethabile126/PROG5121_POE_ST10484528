# PROG5121 POE - ST10484528

## Project Overview
This is a Java Maven project for PROG5121 Programming 1A POE. It demonstrates OOP principles, unit testing with JUnit, and CI/CD with GitHub Actions.

## POE Breakdown

### PART 1 - Login System (Login.java)
- User registration with username validation (must contain _ and <=5 chars)
- Password complexity validation
- Login authentication

### PART 2 - Chat System (Message.java + PROG5121_POE.java)
- Message creation with auto-generated MessageID
- Message validation (max 250 chars)
- Message hash creation
- Stores sent messages and saves to JSON
- Features: send messages, show reports, search, delete

### PART 3 - Unit Testing (MessageTest.java) - FINAL UPDATE 3 Nov 2025
- Location: src/test/java/za/ac/iie/prog5121_poe/MessageTest.java
- 9 JUnit tests covering:
    - Message length validation
    - MessageID generation
    - Message hash generation
    - Check message ID methods
- All tests passing

## Features
- User registration and login system
- Task management (Add tasks, reports, search)
- Input validation and error handling
- Automated unit testing

## Tech Stack
- Java 26
- Maven
- JUnit 5
- GitHub Actions (CI/CD)

## How to Run
1. Clone repo
2. Open in NetBeans
3. Run PROG5121_POE.java
4. To run tests: Right click project -> Test
