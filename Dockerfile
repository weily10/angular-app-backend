# Step 1: Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Dependency Caching: Copy pom.xml first and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build final artifact
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Configure JAVA_OPTS to allow environment variable overrides from Docker Compose
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]