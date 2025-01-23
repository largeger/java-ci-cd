pipeline {
    agent any

    tools {
        jdk 'temurin-20'
        maven 'Maven 3.9.9'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Generate Documentation') {
            steps {
                parallel(
                    "Javadoc": {
                        sh 'mvn javadoc:javadoc'
                    },
                    "SpotBugs": {
                        sh 'mvn spotbugs:spotbugs'
                    }
                )
            }
        }

        stage('Prepare Documentation') {
            steps {
                sh '''
                    mkdir -p docs
                    mkdir -p docs/javadoc
                    mkdir -p docs/coverage
                    mkdir -p docs/spotbugs
                    cp -r target/site/apidocs docs/javadoc
                    cp -r target/site/jacoco docs/coverage
                    cp target/spotbugsXml.xml docs/spotbugs/report.xml
                '''

                writeFile file: 'docs/index.html', text: '''
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Java CI/CD Documentation</title>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                margin: 40px auto;
                                max-width: 800px;
                                padding: 0 20px;
                            }
                            .container {
                                background-color: #f5f5f5;
                                padding: 20px;
                                border-radius: 5px;
                            }
                            h1 { color: #333; }
                            a {
                                display: block;
                                margin: 10px 0;
                                color: #0366d6;
                                text-decoration: none;
                            }
                            a:hover { text-decoration: underline; }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <h1>Project Documentation</h1>
                            <a href="./javadoc/apidocs">📚 JavaDoc API Documentation</a>
                            <a href="./coverage/jacoco">📊 Test Coverage Report</a>
                            <a href="./spotbugs/report.xml">🐛 SpotBugs Analysis Report</a>
                        </div>
                    </body>
                    </html>
                '''
            }
        }

        stage('Publish Documentation') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'docs',
                    reportFiles: 'index.html',
                    reportName: 'Project Documentation'
                ])
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
