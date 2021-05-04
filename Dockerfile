# Build stage
FROM maven:3.6.3-openjdk-11-slim AS builder
COPY . /home/app
WORKDIR /home/app
RUN mvn clean package -Dmaven.test.skip=true

# Package stage
FROM openjdk:11-jre-slim
COPY --from=builder /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]