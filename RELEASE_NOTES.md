# Release Notes - v1.0.0

## Features
- **Event Ticket Sales System (Hito 1)**: Initial implementation of the core domain model for an event ticketing system.
- **Clean Architecture Principles**: Organized in Domain, Application, Infrastructure, and Exception layers.
- **Core Entities**: Implementation of `ShoppingCart`, `TicketItem`, `StockManager`, and `PurchaseValidator`.
- **Value Objects**: Domain-driven design concepts using `CardNumber`, `Email`, `EventId`, `Money`, `Quantity`, and `TicketId`.
- **Notification Adapters**: `MessageNotifier` port with concrete implementations (`SmsNotifier`, `DummyNotifier`).
- **Use Cases & Services**: Full logic for `CreatePurchaseUseCase`, `PaymentUseCase`, `PaymentService`, and `PurchaseService`.

## Technical Details
- **Java 25 (LTS)**
- **Maven**: Complete build lifecycle supported (`mvn compile`, `mvn test`, `mvn package`).
- **Testing**: 100% core test cases implemented utilizing JUnit 5 and Mockito, ensuring domain business rules isolation.

## Deployment
- Executable/packaged JAR available in the `target/` directory after running `mvn package`.
