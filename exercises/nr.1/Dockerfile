
FROM gradle:8.1.1-jdk17 AS builder

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN gradle build --no-daemon || return 0

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
