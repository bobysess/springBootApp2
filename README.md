# springBootApp2

A Spring Boot application optimized for performance and efficiency.

## Performance Optimizations

This application has been configured with several performance optimizations:

### Database Connection Pooling
- **HikariCP** is optimally configured with:
  - Maximum pool size: 10 connections
  - Minimum idle: 5 connections
  - Connection timeout: 20 seconds
  - Idle timeout: 5 minutes
  - Max lifetime: 20 minutes

### JPA/Hibernate Configuration
- **SQL Logging Disabled**: `spring.jpa.show-sql=false` eliminates the performance overhead of logging every SQL query
- **Open-in-View Disabled**: `spring.jpa.open-in-view=false` prevents lazy loading issues and ensures database connections are released promptly
- **Auto DDL**: Schema updates are managed with `spring.jpa.hibernate.ddl-auto=update`

### Security & Resource Management
- **H2 Console Disabled**: The H2 console is disabled by default in production for security and performance

## Development Mode

For development, activate the `dev` profile to enable debugging features:

```bash
# Using Maven
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Using Java
java -jar target/springBootApp2-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

The development profile enables:
- H2 console at `/h2-console`
- SQL query logging with formatting
- Verbose logging for Spring and Hibernate

## Building and Running

```bash
# Build the project
./mvnw clean package

# Run tests
./mvnw test

# Run the application
./mvnw spring-boot:run
```
