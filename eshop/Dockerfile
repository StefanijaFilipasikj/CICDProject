FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-ea-slim
WORKDIR /app
COPY --from=build /app/target/*.jar eshop.jar
COPY uploads ./uploads
EXPOSE 8080
CMD ["java", "-jar", "eshop.jar"]
