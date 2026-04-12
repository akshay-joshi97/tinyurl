FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests
RUN cp target/tinyurl-0.0.1-SNAPSHOT.jar app.jar

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/app.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]