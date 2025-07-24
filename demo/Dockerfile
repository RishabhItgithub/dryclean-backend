# === Stage 1: Build the application using Maven ===
FROM maven:3.9.5-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# === Stage 2: Run the application ===
FROM openjdk:21-jdk-slim
COPY --from=build /demo/target/*.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/demo.jar"]
