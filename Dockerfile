FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Install build dependencies
RUN apk add --no-cache openssl unzip

# Copy Gradle wrapper and project metadata first for better layer caching
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# Copy the application source
COPY src src

# Build the Spring Boot fat jar
RUN ./gradlew bootJar --no-daemon && \
    cp build/libs/*-SNAPSHOT.jar app.jar

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the executable jar from the builder stage
COPY --from=builder /app/app.jar app.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
