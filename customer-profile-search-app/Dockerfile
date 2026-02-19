# Stage 1: Build JAR
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copy pom.xml and src folder relative to Docker context (customer-profile-search-app/)
COPY customer-profile-search-app/pom.xml ./pom.xml
COPY customer-profile-search-app/src ./src

# Build JAR (ensure Maven wrapper exists or use Maven installed in runner)
RUN ./mvnw clean package || mvn clean package

# Stage 2: Run app
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
