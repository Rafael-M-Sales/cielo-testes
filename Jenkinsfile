pipeline {
    agent any

    environment {
        // Garante que o usuário do Jenkins possa falar com o socket do Docker se necessário
        // (Isso depende da configuração do agente, mas é uma boa prática manter env vars aqui)
        COMPOSE_PROJECT_NAME = "cielo-testes-${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Setup Docker') {
            steps {
                script {
                    // Limpa containers antigos se houver (falha segura)
                    sh 'docker-compose down -v || true'
                }
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    // Executa os testes usando docker-compose
                    // --build: garante que a imagem de teste seja recriada com código novo
                    // --exit-code-from tests: falha o pipeline se os testes falharem
                    // --abort-on-container-exit: para o browser quando os testes acabarem
                    sh 'docker-compose up --build --abort-on-container-exit --exit-code-from tests'
                }
            }
        }

        stage('Reports') {
            steps {
                // Copia os relatórios de dentro do volume/container se necessário
                // Mas como mapeamos ./target:/app/target no docker-compose, os arquivos já devem estar no workspace
                script {
                    echo 'Publishing reports...'
                }
            }
        }
    }

    post {
        always {
            // Garante limpeza do ambiente
            sh 'docker-compose down -v'
            
            // Publica relatórios JUnit (gerados pelo Maven dentro do container)
            junit 'target/surefire-reports/*.xml'
            
            // Publica relatórios do Cucumber (se o plugin estiver instalado no Jenkins)
            // cucumber 'target/cucumber-reports/*.json'
        }
    }
}
