# 🧩 Microservices – Java + Spring Boot + Azure + Docker

This project demonstrates a basic microservices architecture using **Java 21**, **Spring Boot**, and **Maven**. It contains two independent services:

- `user-service`: Handles user data
- `product-service`: Manages product catalog

---
## 🚀 How to Run

### 🔧 Requirements

- Java 21
- Maven (`mvn -v`)
- Docker and Docker Compose (for local containerized run)
- Azure CLI (`az`)
- kubectl CLI (Kubernetes CLI)

### ▶️ Run Locally with Maven

In separate terminal windows, navigate into each service and run:

```bash
cd user-service
mvn clean install
java -jar target/user-service-0.0.1-SNAPSHOT.jar
```
```bash
cd product-service
mvn clean install
java -jar target/product-service-0.0.1-SNAPSHOT.jar
```

🐳 Run Locally with Docker Compose
```bash
docker compose up --build
```