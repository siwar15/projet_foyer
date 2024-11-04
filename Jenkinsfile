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

tee -a /etc/prometheus/prometheus.yml <<EOF
  - job_name: backend
    metrics_path: prometheus
    static_configs:
      - targets: ['172.20.0.6:8080']
EOF