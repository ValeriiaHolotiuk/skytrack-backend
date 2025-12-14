FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw -DskipTests package

EXPOSE 8080

CMD ["java", "-jar", "target/skytrack-backend-0.0.1-SNAPSHOT.jar"]

    