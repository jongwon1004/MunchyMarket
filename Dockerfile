# jdk17 Image Start
FROM openjdk:17-jdk-slim

WORKDIR /app

# 인자 정리 - Jar
ARG JAR_FILE=build/libs/MunchyMarket-0.0.1-SNAPSHOT.jar

# jar 파일 복사
COPY ${JAR_FILE} MunchyMarket.jar

# application.yml 파일을 Docker 이미지에 추가
COPY src/main/resources/application.yml application.yml


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "MunchyMarket.jar", "--spring.config.location=classpath:/application.yml"]

