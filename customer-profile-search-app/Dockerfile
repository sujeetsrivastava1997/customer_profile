# Use Temurin JDK
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy JAR (use wildcard for version flexibility)
COPY target/*.jar app.jar

# Run app
ENTRYPOINT ["java","-jar","app.jar"]
