# 1. 사용할 베이스 이미지 지정 (OpenJDK 17)
FROM azul/zulu-openjdk:17

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 JAR 파일을 컨테이너로 복사
COPY target/user-service.jar app.jar

# 4. 컨테이너에서 실행할 명령어 지정
CMD ["java", "-jar", "app.jar"]