FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
COPY src src
RUN chmod +x mvnw && ./mvnw -DskipTests package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/pedido-service-0.0.1-SNAPSHOT.jar ./pedido-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/pedido-service.jar"]
