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
  - Leak detection threshold: 60 seconds

### JPA/Hibernate Configuration
- **SQL Logging Disabled**: `spring.jpa.show-sql=false` eliminates the performance overhead of logging every SQL query
- **Open-in-View Disabled**: `spring.jpa.open-in-view=false` prevents lazy loading issues and ensures database connections are released promptly
- **Batch Operations**: Configured for efficient batch processing with:
  - Batch size: 20 operations
  - Order inserts and updates enabled for better batching
  - Batch versioned data enabled
- **Auto DDL**: Schema updates are managed with `spring.jpa.hibernate.ddl-auto=update`

### Server Optimizations
- **HTTP Compression**: Response compression enabled for text and JSON content (minimum 1KB)
- **Optimized Logging**: Configured at INFO level to reduce overhead

### Security & Resource Management
- **H2 Console Disabled**: The H2 console is disabled by default in production for security and performance

## Environment Profiles

The application supports multiple profiles for different environments:

### Development Mode

Activate the `dev` profile to enable debugging features:

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

### Production Mode

Activate the `prod` profile for production deployment:

```bash
# Using Maven
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# Using Java
java -jar target/springBootApp2-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

The production profile ensures:
- H2 console is disabled
- SQL logging is disabled
- Logging levels set to WARN/INFO
- DevTools are disabled

## Building and Running

```bash
# Build the project
./mvnw clean package

# Run tests
./mvnw test

# Run the application
./mvnw spring-boot:run
```
