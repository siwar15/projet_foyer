## Jenkins Pipeline

This repository includes a `Jenkinsfile` to automate the CI/CD pipeline for the backend application.

### Stage View of the Pipeline
Below is a visual representation of the Jenkins pipeline stages:

![Jenkins Pipeline Stage View](https://github.com/siwar15/projet_foyer/new/reservation-management/JenkinsStageView.png)

**Pipeline Features:**
- Clones the backend and frontend repositories.
- Builds the backend code and runs unit tests.
- Performs code quality analysis using SonarQube.
- Publishes artifacts to Nexus.
- Builds Docker images for backend and frontend services.
- Deploys the services using Docker Compose.

To run the pipeline, configure Jenkins with the following tools:
- Node.js
- Maven
- SonarQube
- Docker
