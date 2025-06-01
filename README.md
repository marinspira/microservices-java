# 🧩 Microservices Deployment – Java + Spring Boot + Docker + Azure Container Apps

This project demonstrates a basic microservices architecture using Java 21, Spring Boot, and Maven. The services are containerized with Docker and automatically deployed to Azure Container Apps using GitHub Actions.

## 🛠️ Microservices

- **user-service** – Manages user data
- **product-service** – Manages product catalog

---

## 🚀 Running Locally

### 🔧 Requirements

- Java 21
- Maven (`mvn -v`)
- Docker and Docker Compose
- Azure CLI (`az`)
- (Optional) kubectl CLI

### ▶️ Run with Maven

Open two terminals, run each service:

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
### 🐳 Run with Docker Compose

```bash
docker compose up --build
```

# 🧪 Manual Build and Push

## Generate JAR with Maven
```bash
mvn clean package -DskipTests
```

## Build Docker image
```bash
docker build -t marinspira/user-service:latest .
```

## Push image to Docker Hub
```bash
docker push marinspira/user-service:latest
```

# ☁️ Deploy to Azure
## 📦 Setup Azure CLI and Subscription

### Install Azure CLI (macOS)
```bash
brew update && brew install azure-cli
```

### Login to Azure
```bash
az login
```

### List subscriptions
```bash
az account list --output table
```

### Set subscription by name or ID
```bash
az account set --subscription "<subscription-id-or-name>"
```

### Verify active subscription
```bash
az account show --output table
```

### 📌 Create Resource Group and Environment
```bash
az provider register -n Microsoft.OperationalInsights --wait
```
```bash
az group create --name microservices-rg --location eastus
```
```bash
az containerapp env create \
  --name microservices-env \
  --resource-group microservices-rg \
  --location eastus
  ```

### 📦 Create Container App
```bash
az containerapp create \
  --name user-service \
  --resource-group microservices-rg \
  --environment microservices-env \
  --image marinspira/user-service:latest \
  --target-port 8080 \
  --ingress external \
  --cpu 0.25 \
  --memory 0.5Gi
  ```

### 🌐 Get Public URL
```bash
az containerapp show \
  --name user-service \
  --resource-group microservices-rg \
  --query properties.configuration.ingress.fqdn \
  -o tsv
  ```

## 🔁 Update Container App with New Image
### 1. Rebuild and Push Docker Image
```bash
docker build -t marinspira/user-service:latest .
docker push marinspira/user-service:latest
```
### 2. Update Azure Container App Image
```bash
az containerapp update \
  --name user-service \
  --resource-group microservices-rg \
  --image marinspira/user-service:latest
  ```
### 3. Update Ingress Port (if needed)
```bash
az containerapp ingress update \
  --name user-service \
  --resource-group microservices-rg \
  --target-port 8080
  ```

# 🤖 CI/CD with GitHub Actions
### 1. Generate Azure Credentials JSON for GitHub Secrets
```bash
az ad sp create-for-rbac \
  --name github-action-deploy \
  --role contributor \
  --scopes /subscriptions/<SUBSCRIPTION_ID> \
  --sdk-auth
  ```

### 2. Add the JSON output as a GitHub secret named AZURE_CREDENTIALS.

### 3. Set GitHub Workflow 
https://github.com/marinspira/microservices-java/blob/main/.github/workflows/ci.yml

This workflow builds the JAR, builds and pushes Docker images, then deploys to Azure Container Apps automatically.
# 🐞 Debug Tips
If your container crashes after deployment, check:

 - Wrong JAR version in the Dockerfile 
 - Inconsistent ports between EXPOSE, target-port, and app properties