# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Define arguments for Nexus
ARG NEXUS_URL=http://nexus:8081/repository/maven-releases/
ARG GROUP_ID=tn.esprit
ARG ARTIFACT_ID=tp-foyer
ARG VERSION=release-X
ARG JAR_NAME=${ARTIFACT_ID}-${VERSION}.jar

# Set up Nexus credentials if needed
ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD

# Install curl and download the JAR file from Nexus
RUN apt-get update && apt-get install -y curl iputils-ping && \
    echo "Testing direct connection to Nexus..." && \
    ping -c 4 nexus || { echo "Unable to reach Nexus"; exit 1; } && \
    curl -I "${NEXUS_URL}" || { echo "Unable to reach Nexus"; exit 1; } && \
    echo "Checking repository path..." && \
    curl -I "${NEXUS_URL}$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/" || { echo "Repository path not found"; exit 1; } && \
    echo "Downloading JAR from Nexus..." && \
    curl -u ${NEXUS_USERNAME}:${NEXUS_PASSWORD} -o ${ARTIFACT_ID}-${VERSION}.jar "${NEXUS_URL}$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/${ARTIFACT_ID}-${VERSION}.jar" || { echo "Failed to download JAR"; exit 1; } && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

RUN ls -l | grep "${JAR_NAME}" || { echo "JAR file not found"; exit 1; }

# Expose the application's port
EXPOSE 8089

# Set the command to run the application (using shell form for variable expansion)
CMD ["java", "-jar", "/app/tp-foyer-release-X.jar"]
