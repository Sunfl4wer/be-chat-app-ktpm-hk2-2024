# FROM maven:latest AS build
# COPY src /usr/src/app/src
# COPY pom.xml /usr/src/app
# WORKDIR /usr/src/app
# RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
# COPY --from=build /usr/src/app/target/service-discovery-1.0.0.jar service-discovery-1.0.0.jar
ADD target/service-discovery-1.0.0.jar service-discovery-1.0.0.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar", "service-discovery-1.0.0.jar"]
