# Build Stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run Stage
FROM openjdk:17-jdk-slim AS run
WORKDIR /app
COPY .env .
COPY --from=build /app/target/futbol.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
