# Use an official OpenJDK image as the base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Download the JAR file from Nexus
# Replace the URL, repository, and credentials as needed
ARG NEXUS_URL=http://your-nexus-server:8081/repository/maven-releases
ARG GROUP_ID=tn.esprit
ARG ARTIFACT_ID=tp-foyer
ARG VERSION=latest
ARG JAR_NAME=${ARTIFACT_ID}-${VERSION}.jar

# Set up Nexus credentials if needed
ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD

RUN apt-get update && apt-get install -y curl && \
    curl -u $NEXUS_USERNAME:$NEXUS_PASSWORD -o $JAR_NAME $NEXUS_URL/$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/$JAR_NAME && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Expose the application's port
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "my-application-1.0.0.jar"]
