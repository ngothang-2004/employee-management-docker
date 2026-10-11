
pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    environment {
        KUBECONFIG = '/workspace/employee-project/jenkins-k8s/deployer-config'
        K3D_CLUSTER = 'employee-cluster'
        BACKEND_IMAGE = 'employee-backend'
        FRONTEND_IMAGE = 'employee-frontend'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ngothang-2004/employee-management-docker.git'
            }
        }

        stage('Prepare Tools') {
            steps {
                sh '''
                    set -eu

                    command -v docker
                    command -v kubectl

                    if ! command -v k3d >/dev/null 2>&1; then
                        mkdir -p "$WORKSPACE/.tools"
                        curl -fsSL \
                          https://github.com/k3d-io/k3d/releases/download/v5.9.0/k3d-linux-amd64 \
                          -o "$WORKSPACE/.tools/k3d"
                        chmod +x "$WORKSPACE/.tools/k3d"
                        export PATH="$WORKSPACE/.tools:$PATH"
                    fi

                    k3d version
                    kubectl get deployments -n employee
                '''
            }
        }

        stage('Build Images') {
            steps {
                sh '''
                    set -eu

                    docker build \
                      -t "${BACKEND_IMAGE}:${BUILD_NUMBER}" \
                      ./backend

                    docker build \
                      -t "${FRONTEND_IMAGE}:${BUILD_NUMBER}" \
                      ./frontend
                '''
            }
        }

        stage('Import Images to K3d') {
            steps {
                sh '''
                    set -eu

                    if ! command -v k3d >/dev/null 2>&1; then
                        export PATH="$WORKSPACE/.tools:$PATH"
                    fi

                    k3d image import \
                      --cluster "$K3D_CLUSTER" \
                      "${BACKEND_IMAGE}:${BUILD_NUMBER}" \
                      "${FRONTEND_IMAGE}:${BUILD_NUMBER}"
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    set -eu

                    kubectl set image deployment/backend \
                      backend="${BACKEND_IMAGE}:${BUILD_NUMBER}" \
                      -n employee

                    kubectl set image deployment/frontend \
                      frontend="${FRONTEND_IMAGE}:${BUILD_NUMBER}" \
                      -n employee

                    kubectl rollout status deployment/backend \
                      -n employee --timeout=300s

                    kubectl rollout status deployment/frontend \
                      -n employee --timeout=300s
                '''
            }
        }

        stage('Verify') {
            steps {
                sh '''
                    set -eu

                    kubectl get deployments,pods,services -n employee
                    kubectl get ingress -n employee
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
                      --data-urlencode "text=SUCCESS: Employee Management đã triển khai lên Kubernetes. Build ${BUILD_NUMBER}."
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
                      --data-urlencode "text=FAILURE: Triển khai Kubernetes thất bại ở build ${BUILD_NUMBER}. Hãy kiểm tra Console Output."
                '''
            }
        }
    }
}
