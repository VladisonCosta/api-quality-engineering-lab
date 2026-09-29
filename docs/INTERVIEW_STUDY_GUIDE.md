# Interview Study Guide

## Be ready to explain

### Why RestAssured?
It provides a fluent Java DSL for sending HTTP requests and asserting status codes, headers and JSON bodies. It integrates naturally with JUnit and Maven.

### What does this project test?
Authentication, CRUD behavior, payload validation, business rules, persistence, pagination, error contracts, headers/content type and JSON Schema.

### Why test negative scenarios?
A robust API must behave predictably not only for valid input, but also for unauthorized requests, invalid payloads, duplicates and missing resources.

### What is JSON Schema validation?
A contract check that validates response structure and data types independently from individual field-value assertions.

### Why H2?
It keeps the portfolio project self-contained and reproducible. Automated tests use a real relational persistence layer without requiring external infrastructure.

### What does GitHub Actions add?
It proves that the automated suite can execute from a clean environment after pushes and pull requests, reducing dependency on the developer's machine.

### How would you evolve it?
PostgreSQL + Testcontainers, contract testing, performance smoke tests with k6, security testing, service virtualization and parallel execution.
