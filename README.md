# Rewards Program API

A Spring Boot REST API application to calculate reward points earned by customers based on their transactions during a configurable time period.

The application provides customer reward calculation based on monthly transactions and total reward points.

---

# Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- Maven
- JUnit 5
- MockMvc
- Stream API

---

# Business Requirement

Given a record of every transaction during a configurable number of months, calculate the reward points earned for each customer per month and total.

---

# Reward Calculation Rules

- 2 points for every dollar spent over $100
- 1 point for every dollar spent between $50 and $100
- No reward points for amounts less than or equal to $50

---

# Features

- Calculate reward points per customer
- Monthly reward aggregation
- Total reward aggregation
- Configurable reward month filtering
- Fetch all customer rewards
- Fetch rewards by customer id
- Fetch all customers
- SQL-based transaction data loading
- Edge case handling
- Unit tests and controller tests

---

# API Endpoints

GET /api/rewards/allRewards

GET /api/rewards/{customerId}

GET /api/rewards/allCustomers

---

# Configuration

rewards.last.months=3

---

# Database Configuration

H2 Console URL:

http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:rewardsdb

Username: sa

---

# Run Application

mvn spring-boot:run

---

# Run Tests

mvn test

---

# Author

Seshadri Reddy
