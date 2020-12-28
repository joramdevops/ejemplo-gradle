pipeline {
    agent any

    stages {
        stage('Pipeline') {
            steps {
                script {
                	stage('Build & Test'){
                		sh "./gradlew clean build"
                	}
                	stage('Sonar'){
                		//esto es lo que se configuro en global tool config de jenkins
                		def scannerHome = tool 'sonar-scanner';
                		//esto es lo que se configuro en config general de jenkins
    					withSonarQubeEnv('sonar-server') { 
    						//para mac
      						sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
    					}
                	}

                	stage('Run'){

                	}

                	stage('Rest'){

                	}

                	stage('Nexus'){

                	}
            }
        }
    }
}
