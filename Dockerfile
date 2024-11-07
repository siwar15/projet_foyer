FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Définir les arguments pour Nexus
ARG NEXUS_URL=http://172.17.0.4:8081/repository/maven-releases/
ARG GROUP_ID=tn.esprit
ARG ARTIFACT_ID=tp-foyer
ARG VERSION=5.0.0
ARG JAR_NAME=${ARTIFACT_ID}-${VERSION}.jar

# Installer curl et ping pour vérifier la connectivité
RUN apk add --no-cache curl iputils

# Vérifier la connectivité avec Nexus
RUN ping -c 4 172.17.0.4 || { echo "Impossible d'atteindre Nexus"; exit 1; } && \
    curl -I "${NEXUS_URL}" || { echo "Impossible d'atteindre Nexus"; exit 1; }

# Télécharger le JAR depuis Nexus
RUN curl -u ${NEXUS_USERNAME}:${NEXUS_PASSWORD} -o ${JAR_NAME} "${NEXUS_URL}$(echo ${GROUP_ID} | tr '.' '/')/${ARTIFACT_ID}/${VERSION}/${JAR_NAME}" || { echo "Échec du téléchargement du JAR"; exit 1; }

# Vérifier si le JAR a été téléchargé
RUN ls -l | grep "${JAR_NAME}" || { echo "Fichier JAR non trouvé"; exit 1; }

# Exposer le port de l'application
EXPOSE 8089

# Définir la commande d'entrée
CMD ["java", "-jar", "${JAR_NAME}"]
