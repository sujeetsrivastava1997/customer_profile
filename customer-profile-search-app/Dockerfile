# Stage 1: Build JAR
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY customer-profile-search-app/pom.xml .
COPY customer-profile-search-app/src ./src
RUN ./mvnw clean package

# Stage 2: Run App
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
