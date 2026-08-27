# NeonPulse - Ticket Sales Microservice (Milestone 4)

## 1. Microservice Description and Tech Stack

NeonPulse is a production-ready backend microservice designed strictly following the principles of **Hexagonal Architecture (Ports and Adapters)**. Its primary goal is to manage the business logic for purchasing and managing concert tickets, ensuring total isolation between pure domain rules and infrastructure details.

For this phase (Milestone 4), the project has evolved to include real database persistence, container orchestration, and secure API contracts.

**Tech Stack:**
* **Language:** Java 17
* **Core Framework:** Spring Boot 3.3.x
* **Persistence:** Spring Data JPA with Hibernate
* **Relational Database:** PostgreSQL 16 (Containerized)
* **Containers:** Docker & Docker Compose
* **Technical Documentation:** Springdoc OpenAPI (Swagger)
* **Other Libraries:** Lombok, Jakarta Validation

---

## 2. Database Initialization

The service depends on a PostgreSQL database. To spin it up with its respective persistent volume automatically, ensure you have Docker installed and running.

Open a terminal in the root of this project and execute:

```bash
docker compose up -d
```
> **Note:** This will spin up a container named `neonpulse-postgres-db` on port `5432`. Data is saved in the `postgres_data` volume, so it won't be lost when the container is shut down.

---

## 3. Running the Microservice

Once the Docker container is up and running, you can compile and start the Spring Boot microservice.

In the root of the project, execute the following command using the included Maven wrapper:

```bash
./mvnw spring-boot:run
```
*(If you are on Windows, you can use `mvnw.cmd spring-boot:run`)*

The application will start under the default profile (`dev`) and expose the REST services on port `8080`. The database schema will be automatically updated thanks to the Hibernate configuration (`ddl-auto: update`).

---

## 4. Documentation Routes and API Contracts (Swagger)

The REST API features semantic routes that have been documented and automatically secured using OpenAPI.

Under the development profile, you can access the interactive console where you can examine all DTOs and test the endpoints (**"Try it out"** button):

* **Swagger-UI Interactive Console:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* **JSON Technical Specification:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

> ⚠️ **Profile Security:** If the application is run with the production profile (`application-prod.yml`), these routes are completely disabled and sealed to avoid exposing the API's attack surface.

---

## 5. Postman Collection

To facilitate manual testing or integration by frontend/QA teams, a ready-to-import Postman collection is included.

In the root of the project you will find the file:
📄 `NeonPulse.postman_collection.json`

**Usage Instructions:**
1. Open Postman.
2. Click "Import" and drag the JSON file.
3. A collection will be created with all the documented use cases (Cities, Concerts, Purchases) pointing to the local environment ready to be fired.
