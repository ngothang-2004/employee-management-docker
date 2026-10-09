pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ngothang-2004/employee-management-docker.git'
            }
        }

        stage('Build and Deploy') {
            steps {
                sh '''
                    set -eu
                    cd /workspace/employee-project
                    docker compose config --quiet
                    docker compose up -d --build
                '''
            }
        }

        stage('Verify') {
            steps {
                sh '''
                    docker ps --filter name=employee-backend
                    docker ps --filter name=employee-frontend
                    docker ps --filter name=employee-mysql
                '''
            }
        }
    }
}
