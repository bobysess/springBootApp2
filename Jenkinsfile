pipeline {
    agent any
    
    tools {
        // Specify Maven tool configured in Jenkins
        jdk 'jdk24'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Get code from repository
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                // Run Maven build
                sh './mvnw clean compile'
            }
        }
        
        stage('Test') {
            steps {
                // Run tests
                sh './mvnw test'
            }
            post {
                always {
                    // Publish test results
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                // Package the application
                sh './mvnw package -DskipTests'
                
                // Archive the artifacts
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        
        stage('Deploy') {
            steps {
                // Placeholder for deployment logic
                echo 'Deploying the application...'
                
                // Example deployment command (commented out)
                // sh 'cp target/*.jar /deploy/directory/'
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
        always {
            // Clean workspace
            cleanWs()
        }
    }
}
