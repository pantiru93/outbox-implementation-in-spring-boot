# Order Service with Outbox Pattern

This project demonstrates the implementation of the Outbox Pattern using Spring Boot, SQL Server, and Kafka.

## Overview

The Outbox Pattern is used to reliably publish events to Kafka while maintaining data consistency. When an order is created:

1. The order is saved to the database
2. An outbox event is created in the same transaction
3. A background process periodically checks for unpublished events and sends them to Kafka
4. Events are marked as processed after successful publication

## Prerequisites

- Docker and Docker Compose
- Java 17 or later
- Maven

## Setup

1. Start the infrastructure:
```bash
bash
docker-compose up -d
```

2. Run the schema creation script from `src/main/resources/schema.sql`

3. Run the application:

```bash
mvn spring-boot:run
```

## Testing

Create a new order:

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"customerName":"John Doe","totalAmount":100.00}'
```

## Project Structure

- `OrderController`: REST endpoint for creating orders
- `OrderService`: Business logic and transaction management
- `OutboxProcessor`: Background process that publishes events to Kafka
- `OutboxEvent`: Entity representing events in the outbox table
- `ShedLockConfig`: Distributed lock configuration for the outbox processor

## Tables

### Orders Table
- id
- customerName
- totalAmount
- createdAt
- status

### Outbox Table
- id
- eventType
- payload
- partitionKey
- status
- createdAt
- processedAt

For more information, please refer to the [Outbox Pattern](https://medium.com/@pantiru.cosmin4rolletechnology/a-practical-guide-to-the-outbox-pattern-ensuring-consistency-in-distributed-systems-8228d2a1dc62) article.