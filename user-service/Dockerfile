# FROM maven:latest AS build
# COPY src /usr/src/app/src
# COPY pom.xml /usr/src/app
# WORKDIR /usr/src/app
# RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
# COPY --from=build /usr/src/app/target/user-service-1.0.0.jar user-service-1.0.0.jar
ADD target/user-service-1.0.0.jar user-service-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "user-service-1.0.0.jar"]
