#FROM openjdk:21 as builder
# COPY files for build
# +Dependencies
#RUN /Contents/Home/bin/java -Dmaven.multiModuleProjectDirectory=/Users/mikhail/IdeaProjects/ekspring -Djansi.passthrough=true -Dmaven.home=/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3 -Dclassworlds.conf=/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/m2.conf -Dmaven.ext.class.path=/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven-event-listener.jar -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=58833:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/boot/plexus-classworlds.license:/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/boot/plexus-classworlds-2.7.0.jar org.codehaus.classworlds.Launcher -Didea.version=2023.2.5 clean install -Dmaven.plugin.validation=VERBOSE
#WORKDIR

#LABEL authors="mike&nikita"
FROM openjdk:21
#EXPOSE 8080
#COPY --FROM=builder ekspring-0.0.1-SNAPSHOT.jar app.jar
# path make from -context from docker-compose
ADD target/ekspring-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


#ARG jar-directory=ekspring_mac
#WORKDIR /app
#RUN mkdir ${jar-directory}
#
#COPY ./mvnw .
#COPY ./.mvn .mvn
#COPY ./pom.xml .
#
#COPY ./${jar-directory}/pom.xml ${jar-directory}
#COPY ./${jar-directory}/src ${jar-directory}/src
#
#RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests
#
#FROM openjdk:21
#VOLUME /tmp
#
#WORKDIR /build/${jar-directory}
#ARG JAR_FILE=/profile/${jar-directory}/target/${jar-directory}-0.0.1-SNAPSHOT.jar
#COPY --from=builder ${JAR_FILE} ./${jar-directory}.jar
#
#EXPOSE [PORT]


## Используем базовый образ с Java
#FROM maven:3.9.6 AS build
#
## Устанавливаем рабочую директорию
#WORKDIR /app
#
## Копируем файлы проекта в контейнер
#COPY pom.xml .
#COPY src ./src
#
## Собираем проект с помощью Maven
#RUN mvn clean install -DskipTests
#
## Второй этап сборки
#FROM openjdk:20
#
## Копируем собранный JAR файл из предыдущего этапа
#COPY --from=build /app/target/*.jar /app/app.jar
#
## Запускаем приложение
#CMD ["java", "-jar", "/app/app.jar"]