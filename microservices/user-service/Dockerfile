FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

# Matches server.port: 8081 in application.yml
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]