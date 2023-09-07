FROM ubuntu:latest
LABEL authors="vinicius"

FROM eclipse-temurin:17-alpine
EXPOSE 8080
ARG JAR_FILE=target/DesafioSimplifyTec-1.0.0-RELEASE.jar
ADD ${JAR_FILE} DesafioSimplifyTec-1.0.0-RELEASE.jar
ENTRYPOINT ["java", "-jar", "/DesafioSimplifyTec-1.0.0-RELEASE.jar", "&", "top", "-b"]