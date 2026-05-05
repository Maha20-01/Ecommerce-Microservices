# 📚 Bookstore E-Commerce Microservices

**Spring Boot 3.x + Spring Cloud | Java 17+**

A production-ready backend for a bookstore application built using **microservices architecture**. This system replaces a monolithic backend with scalable, independently deployable services using Spring Cloud and modern DevOps practices.

---

## 🏗️ System Architecture

The application is decomposed into multiple independent microservices. Each service:

* Owns its own database (**Database-per-Service pattern**)
* Exposes RESTful APIs
* Communicates via:

    * HTTP (Feign / REST)
    * Kafka (Event-driven async communication)
* Routes all external traffic through a **single API Gateway**

---

## 🔧 Core Infrastructure Services

| Component     | Port | Technology                 | Responsibility                               |
| ------------- | ---- | -------------------------- | -------------------------------------------- |
| API Gateway   | 8080 | Spring Cloud Gateway       | Routing, JWT validation, rate limiting, CORS |
| Eureka Server | 8761 | Netflix Eureka             | Service discovery & registration             |
| Config Server | 8888 | Spring Cloud Config Server | Centralized configuration                    |

---

## 📦 Microservices Overview

| # | Microservice         | Port | Database   | Responsibility                    |
| - | -------------------- | ---- | ---------- | --------------------------------- |
| 1 | User Service         | 8081 | PostgreSQL | Registration, login, JWT, profile |
| 2 | Admin Service        | 8082 | PostgreSQL | Admin roles, dashboard            |
| 3 | Product Service      | 8083 | PostgreSQL | Books, categories, inventory      |
| 4 | Cart Service         | 8084 | Redis      | Cart sessions & calculations      |
| 5 | WishList Service     | 8085 | PostgreSQL | User wishlist                     |
| 6 | Customer Service     | 8086 | PostgreSQL | Address & preferences             |
| 7 | Order Service        | 8087 | PostgreSQL | Order lifecycle, Kafka events     |
| 8 | Feedback Service     | 8088 | PostgreSQL | Reviews & ratings                 |
| 9 | Notification Service | 8089 | Kafka      | Email/SMS notifications           |

---