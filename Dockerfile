# Utiliser une image OpenJDK comme base
FROM openjdk:17-jdk-alpine

# Définir les arguments pour Nexus
ARG NEXUS_URL=http://localhost:8081/repository/maven-releases/
ARG GROUP_ID=tn.esprit
ARG ARTIFACT_ID=tp-foyer
ARG VERSION=5.0.0
ARG JAR_NAME=${ARTIFACT_ID}-${VERSION}.jar

# Configuration des identifiants Nexus
ARG NEXUS_USERNAME=admin
ARG NEXUS_PASSWORD=ryl123

# Installer curl et télécharger le fichier JAR depuis Nexus avec authentification
RUN apk add --no-cache curl iputils && \
    echo "Test de la connexion directe à Nexus..." && \
    ping -c 4 localhost || { echo "Impossible d'atteindre Nexus"; exit 1; } && \
    echo "Vérification du chemin du dépôt..." && \
    curl -I "${NEXUS_URL}$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/" || { echo "Chemin du dépôt non trouvé"; exit 1; } && \
    echo "Téléchargement du JAR depuis Nexus..." && \
    curl -u ${NEXUS_USERNAME}:${NEXUS_PASSWORD} -o ${JAR_NAME} "${NEXUS_URL}$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/${JAR_NAME}" || { echo "Échec du téléchargement du JAR"; exit 1; }

# Exposer le port de l'application
EXPOSE 8089

# Lancer l'application
CMD ["java", "-jar", "${JAR_NAME}"]
