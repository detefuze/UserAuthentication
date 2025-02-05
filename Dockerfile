# Базовый образ с Java
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/UserAuthentication-1.0-SNAPSHOT.jar /app/UserAuthentication-1.0-SNAPSHOT.jar
COPY .env /app/.env

ENTRYPOINT ["java", "-jar", "UserAuthentication-1.0-SNAPSHOT.jar"]