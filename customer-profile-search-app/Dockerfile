FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy JAR from subfolder
COPY customer-profile-search-app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
