# Use an official OpenJDK image as the base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Define arguments for Nexus
ARG NEXUS_URL=http://172.20.0.2:8081/repository/maven-releases/
ARG GROUP_ID=tn.esprit
ARG ARTIFACT_ID=tp-foyer
ARG VERSION=1.0
ARG JAR_NAME=${ARTIFACT_ID}-${VERSION}.jar

# Set up Nexus credentials if needed
ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD

# Install curl and download the JAR file from Nexus
RUN apt-get update && apt-get install -y curl && \
    echo "Nexus URL: $NEXUS_URL" && \
    echo "Nexus Username: $NEXUS_USERNAME" && \
    echo "Downloading JAR from Nexus..." && \
    curl -u $NEXUS_USERNAME:$NEXUS_PASSWORD -o $JAR_NAME "$NEXUS_URL/$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/$JAR_NAME" || { echo "Failed to download JAR"; exit 1; } && \
    apt-get clean && rm -rf /var/lib/apt/lists/*


# Expose the application's port
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "$JAR_NAME"]
