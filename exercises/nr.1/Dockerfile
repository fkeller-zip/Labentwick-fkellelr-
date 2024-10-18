# Verwende das offizielle OpenJDK Image
FROM openjdk:17-jdk-slim

# Setze das Arbeitsverzeichnis auf /app
WORKDIR /app

# Kopiere die pom.xml und die source files ins Image
COPY ./pom.xml ./src/ /app/

# Abhängigkeiten installieren
RUN ./mvnw clean install

# Anwendung starten
CMD ["java", "-jar", "target/cloud-usage-1.0-SNAPSHOT.jar"]
