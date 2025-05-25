# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy the pom and source code
COPY pom.xml .
COPY src ./src

# Build the project (without tests)
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8082
ENV PORT 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
