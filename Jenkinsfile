pipeline {
    agent any
    
    tools {
        // Specify Maven and JDK tools configured in Jenkins
        maven 'mvn399'
        //jdk 'jdk24'
    }
    stages {
        stage('java(/mvn)') {
           steps {
                sh 'java --version'
                sh 'mvn --version'     
           } 
        }

        stage('Checkout') {
            steps {
                // Get code from repository
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                // Run Maven build
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                // Run tests
                sh 'mvn test'
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
                sh 'mvn package -DskipTests'
                
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
