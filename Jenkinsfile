pipeline {
	agent any 

	parameters { choice(name: 'herramienta', choices: ['gradle','maven'], description: '') }

	stages {
		stage('Pipelines') {
			steps {
				script {

					params.herramienta // -> gradle o maven

					if(params.herramienta == 'gradle'){
                        def ejecucion = load 'gradle.groovy'
                        ejecucion.call()
                    }else{
                        def ejecucion = load 'maven.groovy'
                        ejecucion.call()
                    }
				}
			}
		}
	}
		post {

                	success {
			slackSend teamDomain: 'devops-usach-2020', tokenCredentialId: 'token-slack', color: 'good', message: "Joram Diaz][Pipeline-maven-gradle][${params.herramienta}] Ejecución exitosa."
			}
			failure {
			slackSend teamDomain: 'devops-usach-2020', tokenCredentialId: 'token-slack', color: 'danger', message: "[Joram Diaz][Pipeline-maven-gradle][${params.herramienta}] Ejecución fallida en stage ${TAREA}."
			}
		}
}
