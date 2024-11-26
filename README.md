## Jenkins Pipeline

This repository includes a `Jenkinsfile` that automates the CI/CD pipeline for the backend application.

### Stage View of the Pipeline
Below is a visual representation of the Jenkins pipeline stages:

![Jenkins Pipeline Stage View](https://github.com/siwar15/projet_foyer/blob/reservation-management/JenkinsStageView.png)

### Pipeline Features:
- Clones the **backend** and **frontend** repositories.
- Builds the **backend** code and runs unit tests.
- Performs code quality analysis with **SonarQube**.
- Publishes artifacts to **Nexus**.
- Builds Docker images for both **backend** and **frontend** services.
- Deploys both services using **Docker Compose**.

### Required Jenkins Tools:
- **Node.js**
- **Maven**
- **SonarQube**
- **Docker**

---

### Integrating Frontend Repo:
- The **frontend repository** is cloned in the Jenkins pipeline during the build stage and integrated into the deployment process.
- You can find the frontend repository [here](https://github.com/MouadhSaadaoui/tp-foyer-frontend).

