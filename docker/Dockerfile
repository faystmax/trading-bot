FROM openjdk:11-jdk-slim
MAINTAINER Amosov Maxim - faystmax@gmail.com
VOLUME /tmp
COPY app/*.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]