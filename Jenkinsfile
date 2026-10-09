
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

                    cp docker-compose.yml /workspace/employee-project/docker-compose.yml
                    cp -a backend frontend mysql /workspace/employee-project/

                    cd /workspace/employee-project
                    docker compose config --quiet
                    docker compose up -d --build
                '''
            }
        }

        stage('Verify') {
            steps {
                sh '''
                    set -eu
                    docker compose -f /workspace/employee-project/docker-compose.yml \
                        --project-directory /workspace/employee-project ps
                '''
            }
        }
    }
}
