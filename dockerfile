FROM openjdk:17-jdk-alpine
COPY target/AccessAid-0.0.1-SNAPSHOT.jar accessaid.jar

ENTRYPOINT [ "java", "-jar" , "accessaid.jar"]
