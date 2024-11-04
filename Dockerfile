# Use an official OpenJDK 21 image
FROM eclipse-temurin:21-jdk-alpine

# Install Maven
RUN apk add --no-cache maven

# Set the working directory
WORKDIR /home/app

# Copy the project files
COPY src /home/app/src
COPY pom.xml /home/app

# Build the application
RUN mvn clean package -DskipTests

# Expose the port the app will run on
EXPOSE 8070

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "/home/app/target/Coworker.ru-0.0.1-SNAPSHOT.jar"]
