pipeline {
    agent { label 'master' }

    options {
        timestamps()
    }

    post {
        // success {
        //     updateGitlabCommitStatus name: 'Build-Finished', state: 'success'
        // }
        // failure {
        //     updateGitlabCommitStatus name: 'Build-Finished', state: 'failed'
        // }
        always {
            junit 'modules/**/target/surefire-reports/**.xml'
        }
    }

    stages {
        stage("Run with JDK 8 and maven") {
            agent {
                docker {
                    image 'maven:3.6.0-jdk-8'
                    args '-v maven-repo:/root/.m2 -v sonar-repo:/root/.sonar'
                    reuseNode true
                }
            }
            stages {
                stage("Build") {
                    steps {
                        sh "mvn clean package -Dmaven.test.skip=true"
                    }
                }
                stage("Test") {
                    steps {
                        sh "mvn test"
                    }
                }
                stage("Verify") {
                    steps {
                        withSonarQubeEnv("sonarcloud") {
                          sh "mvn sonar:sonar -Dsonar.projectKey=Samper1022_asv-swagger-codegen -Dsonar.organization=samper1022-github"
                        }
                        // timeout(time: 30, unit: "MINUTES") {
                        //     waitForQualityGate abortPipeline: true
                        // }
                    }
                }

            }
        }
        stage("Deploy") {
            steps{
                script {
                    def dockerImage = docker.build("asv-swagger-codegen:${env.BUILD_ID}")
                    dockerImage.push("${env.BUILD_NUMBER}")
                    dockerImage.push("latest")
                }
            }
        }
    }
}