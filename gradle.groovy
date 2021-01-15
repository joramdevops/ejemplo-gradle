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
		nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: 'release-0.0.11']]]
	}
	
	stage('gitTagMaster'){
    		echo "Tagging master with version: ${version}"
    		fetchMasterFromOrigin()
    		sshagent([sshKey]) {
        		int status = sh returnStatus: true, script: "git checkout master && git tag ${version} && git push --tag"
        		if (status != 0) {
            		echo "Failed to tag master branch with tag ${version}."
        		}
    		}
	}
	
	
}

return this;
