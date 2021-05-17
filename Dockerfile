# Build Stage
FROM maven:3.6.3-openjdk-11-slim AS builder
COPY . /app
WORKDIR /app
RUN mvn clean package -Dmaven.test.skip=true

# Production Stage
FROM openjdk:11-jre-slim
COPY --from=builder /app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]