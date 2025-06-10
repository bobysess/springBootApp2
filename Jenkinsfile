pipeline {
    agent any 
    
    // tools {
    //     // Specify Maven and JDK tools configured in Jenkins
    //     //maven 'mvn399'
    //     //jdk 'jdk24'
    // }
    stages {
        stage('java(/mvn)') {
           steps {
                sh 'java --version'
                sh './mvnw --version'     
           } 
        }

        stage('Checkout') {
            steps {
                // Get code from repository
                checkout scm
            }
        }
        
        
        // stage('docker') {
        //     steps {
        //         withCredentials([usernamePassword(credentialsId: 'docker-repo-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        //             sh "docker login -u \$DOCKER_USER -p \$DOCKER_PASS"
        //             sh "docker build -t bobyess/spring-boot-app-2:1.0.1 ."
        //             sh "docker push bobyess/spring-boot-app-2:1.0.1"
        //         }
        //     }
        // }
    
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

        // stage ('jacoco') {
        //     steps {
        //         recordCoverage(tools: [[parser: 'JACOCO']],
        //             id: 'jacoco', name: 'JaCoCo Coverage',
        //             sourceCodeRetention: 'EVERY_BUILD',
        //             qualityGates: [
        //                 [threshold: 60.0, metric: 'LINE', baseline: 'PROJECT', unstable: true],
        //                 [threshold: 60.0, metric: 'BRANCH', baseline: 'PROJECT', unstable: true]])
        //     }
        // }
        
        // stage('Package') {
            // steps {
                // Package the application
                // sh './mvnw package -DskipTests'
                // 
                // Archive the artifacts
                // archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            // }
        // }
        
        // stage('Deploy') {
            // steps {
            //    Placeholder for deployment logic
                // echo 'Deploying the application...'
                // 
            //    Example deployment command (commented out)
            //    sh 'cp target/*.jar /deploy/directory/'
            // }
        // }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
            slackSend color: "good", message: 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
            slackSend color: "bad", message: 'Pipeline failed!'
        }
        always {
            // Clean workspace
            cleanWs()
        }
    }
}
