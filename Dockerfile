# jdk17 Image Start
FROM openjdk:17-jdk-slim

WORKDIR /app

# 인자 정리 - Jar
ARG JAR_FILE=build/libs/MunchyMarket-0.0.1-SNAPSHOT.jar

# 시간대 설정
ENV TZ=Asia/Tokyo
RUN apt-get update && apt-get install -y tzdata && rm -rf /var/lib/apt/lists/*

# jar 파일 복사
COPY ${JAR_FILE} MunchyMarket.jar
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/munchymarket-bucket-credentials.json

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=Asia/Tokyo", "-Dspring.config.location=file:/app/application.yml", "-jar", "MunchyMarket.jar"]
