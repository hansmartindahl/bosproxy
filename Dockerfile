# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build

# Copy Maven wrapper and pom
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw

# Download dependencies (cached unless pom changes)
RUN ./mvnw dependency:go-offline -B

# Copy source and build
COPY src src
RUN ./mvnw package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Add a non-root user to run the app
RUN addgroup -g 1000 app && adduser -u 1000 -G app -D app
USER app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
