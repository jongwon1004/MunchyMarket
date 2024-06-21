# jdk17 Image Start
FROM openjdk:17-jdk-slim

WORKDIR /app

# 인자 정리 - Jar
ARG JAR_FILE=build/libs/MunchyMarket-0.0.1-SNAPSHOT.jar
ARG SPRING_CONFIG_LOCATION
# jar 파일 복사
COPY ${JAR_FILE} MunchyMarket.jar

# application.yml 파일 복사
COPY resources/application.yml /app/resources/application.yml

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "MunchyMarket.jar"]