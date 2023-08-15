FROM openjdk:17-alpine
LABEL maintainer="kakao99"
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/backend.jar"]
