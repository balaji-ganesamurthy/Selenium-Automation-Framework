pipeline {

    agent any

    tools {
        maven 'Maven-3.9.16'
    }

    stages {

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'reports',
                    reportFiles: 'AutomationReport.html',
                    reportName: 'Extent Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
                
                   archiveArtifacts artifacts: 'reports/**'
            }
        }

    }

    post {
        always {
            echo 'Pipeline execution completed'
        }
    }

}