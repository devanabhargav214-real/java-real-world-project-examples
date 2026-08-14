FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copies the packaged JAR from the user-service target folder
COPY target/*.jar app.jar

# Expose standard port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]