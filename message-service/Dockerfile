# FROM maven:latest AS build
# COPY src /usr/src/app/src
# COPY pom.xml /usr/src/app
# WORKDIR /usr/src/app
# RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
# COPY --from=build /usr/src/app/target/messaging-service-1.0.0.jar messaging-service-1.0.0.jar
ADD target/messaging-service-1.0.0.jar messaging-service-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "messaging-service-1.0.0.jar"]
