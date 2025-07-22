# Use official OpenJDK 21 image
FROM eclipse-temurin:21-jre
COPY build/libs/weather_info_api-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]