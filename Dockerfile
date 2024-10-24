# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Define arguments for Nexus
ARG NEXUS_URL=http://172.20.0.2:8081/repository/maven-releases/
ARG GROUP_ID=tn.esprit
ARG ARTIFACT_ID=tp-foyer
ARG VERSION=release-03
ARG JAR_NAME=${ARTIFACT_ID}-${VERSION}.jar

# Set up Nexus credentials if needed
ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD

# Install curl and download the JAR file from Nexus
RUN apt-get update && apt-get install -y curl && \
    echo "Testing direct connection to Nexus..." && \
    curl -I http://admin:root@172.20.0.2:8081/repository/maven-releases/ || { echo "Unable to reach Nexus"; exit 1; } && \
    echo "Downloading JAR from Nexus..." && \
    curl -u nexus_username:nexus_password -o ${ARTIFACT_ID}-${VERSION}.jar "http://172.20.0.2:8081/repository/maven-releases/$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/${ARTIFACT_ID}-${VERSION}.jar" || { echo "Failed to download JAR"; exit 1; } && \
    apt-get clean && rm -rf /var/lib/apt/lists/*



# Expose the application's port
EXPOSE 8080

# Set the command to run the application (using shell form for variable expansion)
CMD java -jar "$JAR_NAME"
