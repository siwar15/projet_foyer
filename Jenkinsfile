pipeline {
    agent any

    stages {
        stage('Clone Code') {
            steps {
                // Clone the repository and checkout the specific branch
                git branch: 'reservation-management', url: 'https://github.com/siwar15/projet_foyer.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // Clean and compile the project
                sh 'mvn clean install'
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
