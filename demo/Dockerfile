# === Stage 1: Build the application using Maven ===
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /demo
COPY . .
RUN mvn clean package -DskipTests

# === Stage 2: Run the application ===
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /demo
COPY --from=build /demo/target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
