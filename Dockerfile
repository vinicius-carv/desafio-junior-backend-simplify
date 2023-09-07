FROM ubuntu:latest
LABEL authors="vinicius"

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory to /app
WORKDIR /app

# Copy the packaged JAR file into the container at /app
COPY target/your-application.jar .

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define environment variable
ENV JAVA_OPTS=""

# Run your application with this command
CMD java $JAVA_OPTS -jar your-application.jar

ENTRYPOINT ["top", "-b"]