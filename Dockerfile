# Utiliser une image OpenJDK comme base
FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Définir les arguments pour Nexus
ARG NEXUS_URL=http://nexus:8081/repository/maven-releases/
ARG GROUP_ID=tn.esprit
ARG ARTIFACT_ID=tp-foyer
ARG VERSION=5.0.0
ARG JAR_NAME=${ARTIFACT_ID}-${VERSION}.jar

# Configuration des identifiants Nexus
ARG NEXUS_USERNAME=admin
ARG NEXUS_PASSWORD=Nexus/siwar1

# Installer curl et télécharger le fichier JAR depuis Nexus avec authentification
RUN apk add --no-cache curl && \
    echo "Téléchargement du JAR depuis Nexus..." && \
    curl -u ${NEXUS_USERNAME}:${NEXUS_PASSWORD} -o ${JAR_NAME} "${NEXUS_URL}$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/${JAR_NAME}" || { echo "Échec du téléchargement du JAR"; exit 1; } && \
    test -f "/app/${JAR_NAME}" || { echo "Le fichier JAR n'a pas été téléchargé avec succès"; exit 1; } && \
    apk del curl

# Exposer le port de l'application
EXPOSE 8089

# Lancer l'application
CMD ["java", "-jar", "/app/${JAR_NAME}"]
