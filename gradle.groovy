/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){

	stage('Build & Test'){
		env.TAREA = 'Build & Test'
		sh "./gradlew clean build"
	}	

	stage('Sonar'){
		env.TAREA = 'Sonar'
		def scannerHome = tool 'sonar-scanner';
		//nombre del servidor de sonar en jenkins
		withSonarQubeEnv('sonar-server') {
			sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
		}
	}

	stage('Run'){
		env.TAREA = 'Run'
		sh "nohup gradlew bootRun &"
		sleep 20
	}

	stage('Rest'){
		env.TAREA = 'Rest'
		sh "curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
	}

	stage('Nexus'){
		env.TAREA = 'Nexus'
		nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.5']]]
	}
}

return this;
