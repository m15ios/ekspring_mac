LABEL authors="mike&nikita"
FROM openjdk:21-alpine
EXPOSE 8080
ARG JAR_FILE=target/ekspring-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
