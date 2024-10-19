FROM openjdk:21-jdk

EXPOSE 8070

WORKDIR /app

COPY target/Coworker.ru-0.0.1-SNAPSHOT.jar coworker.jar

ENTRYPOINT ["java", "-jar", "coworker.jar"]