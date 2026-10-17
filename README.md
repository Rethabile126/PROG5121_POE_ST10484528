# PROG5121 POE - ST10484528

## Project Overview
This is a Java Maven project for PROG5121 Programming 1A POE. It demonstrates OOP principles, unit testing with JUnit, and CI/CD with GitHub Actions.

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
1. Clone repo:
   `git clone https://github.com/Rethabile126/PROG5121_POE_ST10484528.git`
2. Open in NetBeans / IntelliJ
3. Run: `mvn clean test`
4. Run Main class: `src/main/java/com/mycompany/.../Main.java`

## CI/CD Pipeline
- Workflow file: `.github/workflows/maven.yml`
- Trigger: Push to main branch
- Steps: Setup JDK 26 > Build with Maven > Run Tests
- Status: ✅ Passing (See Actions tab)

## Project Structure
