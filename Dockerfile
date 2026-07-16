FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM gcr.io/distroless/java21-debian12

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]