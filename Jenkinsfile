
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

    post {
        success {
            withCredentials([string(
                credentialsId: 'telegram-bot-token',
                variable: 'TG_TOKEN'
            )]) {
                sh '''
                    curl -fsS --max-time 15 -X POST \
                        "https://api.telegram.org/bot${TG_TOKEN}/sendMessage" \
                        --data-urlencode "chat_id=-5505298580" \
                        --data-urlencode "text=✅ Jenkins SUCCESS: Employee Management đã build và triển khai thành công."
                '''
            }
        }

        failure {
            withCredentials([string(
                credentialsId: 'telegram-bot-token',
                variable: 'TG_TOKEN'
            )]) {
                sh '''
                    curl -fsS --max-time 15 -X POST \
                        "https://api.telegram.org/bot${TG_TOKEN}/sendMessage" \
                        --data-urlencode "chat_id=-5505298580" \
                        --data-urlencode "text=❌ Jenkins FAILURE: Pipeline Employee Management thất bại. Hãy kiểm tra Console Output trong Jenkins."
                '''
            }
        }
    }
}
