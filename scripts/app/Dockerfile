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
