# Stage 1: Build with Maven + Java 21 (Alpine)
FROM maven:3.9.12-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy pom and source
COPY pom.xml .
COPY src ./src

# Build JAR
RUN mvn clean package -DskipTests

# Stage 2: Run with Java 21 (Alpine)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/target/customer-profile-search-app-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
