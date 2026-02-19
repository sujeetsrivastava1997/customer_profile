# Stage 1: Build JAR
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copy pom.xml and src relative to current folder
COPY pom.xml ./pom.xml
COPY src ./src

# Build JAR
RUN ./mvnw clean package || mvn clean package

# Stage 2: Run the app
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
