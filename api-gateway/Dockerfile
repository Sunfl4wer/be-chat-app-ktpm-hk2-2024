# FROM maven:latest AS build
# COPY src /usr/src/app/src
# COPY pom.xml /usr/src/app
# WORKDIR /usr/src/app
# RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
#COPY --from=build /usr/src/app/target/api-gateway-1.0.0.jar api-gateway-1.0.0.jar
ADD target/api-gateway-1.0.0.jar api-gateway-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "api-gateway-1.0.0.jar"]
