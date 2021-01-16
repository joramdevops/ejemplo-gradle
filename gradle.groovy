/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){

	stage('Build & Test'){
		sh "./gradlew clean build"
	}	

	stage('Sonar'){
		def scannerHome = tool 'sonar-scanner';
		//nombre del servidor de sonar en jenkins
		withSonarQubeEnv('sonar-server') {
			sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
		}
	}

	stage('Run'){
		sh "nohup gradlew bootRun &"
		sleep 20
	}

	stage('Rest'){
		sh "curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
	}
	
	stage('Nexus'){
		nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.12']]]
	}
	
	stage ('TagMaster') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: my_credentials, passwordVariable: 'qucse8vunQutfawjos', usernameVariable: 'joramdevops')]) {
                        sh "git tag V-{VERSION_NUMBER}-${BUILD_NUMBER}"
                        sh "git push https://${joramdevops}:${qucse8vunQutfawjos}@myurl V-{VERSION_NUMBER}-${BUILD_NUMBER}"
                    }
                }
            }
        }
	
	
}

return this;
