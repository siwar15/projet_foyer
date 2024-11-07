pipeline {
    agent any
    tools {
        nodejs 'nodejs'  // This should match the name you gave the NodeJS installation in Jenkins
    }
    environment {
        NEXUS_CREDENTIALS = credentials('nexus-credentials')
        DOCKER_CREDENTIALS = credentials('dockerhub-credentials')
    }
    stages {
        stage('Clone Backend Code') {
            steps {
                dir('backend') {
                    git branch: 'reservation-management', url: 'https://github.com/siwar15/projet_foyer.git'
                }
            }
        }

        stage('Clean and Compile Backend with Maven') {
            steps {
                dir('backend') {
                    sh 'mvn clean compile'
                }
            }
        }
        
        stage('Run Unit Tests') {
            steps {
                dir('backend') {
                    sh 'mvn test'
                }
            }
        }
        
        stage('Generate JaCoCo Report') {
            steps {
                dir('backend') {
                    sh 'mvn jacoco:report'
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                dir('backend') {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
        
        stage('Package Backend Application') {
            steps {
                dir('backend') {
                    sh 'mvn clean install'
                }
            }
        }
        
        stage('Publish Coverage Report') {
            steps {
                dir('backend') {
                    archiveArtifacts artifacts: 'target/site/jacoco/**/*.html', fingerprint: true
                }
            }
        }
        
        stage('Nexus Deploy') {
            steps {
                dir('backend') {
                    echo "Using Nexus username: ${NEXUS_CREDENTIALS_USR}"
                    sh '''
                        mvn deploy \
                        -DskipTests \
                        -DaltDeploymentRepository=deploymentRepo::default::http://nexus:8081/repository/maven-releases/ \
                        -Dnexus.username=${NEXUS_CREDENTIALS_USR} \
                        -Dnexus.password=${NEXUS_CREDENTIALS_PSW}
                    '''
                }
            }
        }
        
        stage('Build Backend Docker Image') {
            steps {
                dir('backend') {
                    script {
                        sh '''
                            docker build \
                            --network jenkins-sonarqube-network \
                            --build-arg NEXUS_USERNAME=${NEXUS_CREDENTIALS_USR} \
                            --build-arg NEXUS_PASSWORD=${NEXUS_CREDENTIALS_PSW} \
                            -t mouadhs/reservation_management:vXX .
                        '''
                    }
                }
            }
        }
        
        stage('Push Backend Docker Image') {
            steps {
                script {
                    sh '''
                        echo "${DOCKER_CREDENTIALS_PSW}" | docker login -u "${DOCKER_CREDENTIALS_USR}" --password-stdin
                    '''
                    sh 'docker push mouadhs/reservation_management:vXX'
                }
            }
        }

        stage('Clone Frontend Code') {
            steps {
                dir('frontend') {
                    git branch: 'main', url: 'https://github.com/MouadhSaadaoui/tp-foyer-frontend.git', credentialsId: 'frontend-repo-credentials'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                script {
                    dir('frontend') {
                        sh '''
                            docker build -t mouadhs/frontend-app:v1.0 .
                        '''
                    }
                }
            }
        }

        stage('Push Frontend Docker Image') {
            steps {
                script {
                    sh '''
                        echo "${DOCKER_CREDENTIALS_PSW}" | docker login -u "${DOCKER_CREDENTIALS_USR}" --password-stdin
                    '''
                    sh 'docker push mouadhs/frontend-app:v1.0'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                dir('backend') {
                    
                    sh 'docker-compose up -d'
                }
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
