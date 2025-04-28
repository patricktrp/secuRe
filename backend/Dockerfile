# Stage 1: Build
FROM maven:3.9.9-amazoncorretto-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy only the pom.xml first to leverage Docker caching for dependencies
COPY pom.xml .

# Download dependencies (caching layer optimization)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Deploy
FROM amazoncorretto:21-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]