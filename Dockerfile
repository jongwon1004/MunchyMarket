# jdk17 Image Start
FROM openjdk:17-jdk-slim

WORKDIR /app

# 인자 정리 - Jar
ARG JAR_FILE=build/libs/MunchyMarket-0.0.1-SNAPSHOT.jar

# jar 파일 복사
COPY ${JAR_FILE} MunchyMarket.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.config.location=file:/app/application.yml", "-jar", "MunchyMarket.jar"]