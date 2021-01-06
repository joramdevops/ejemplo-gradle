pipeline {
	agent any 

	parameters { choice(name: 'herramienta', choices: ['gradle','maven'], description: 'Seleccione la herramienta para la aplicación') }

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
			slackSend color: 'good', message: "[Joram Diaz][${env.JOB_NAME}][${params.herramienta}] Ejecución exitosa."
			}
			failure {
			slackSend color: 'danger', message: "[Joram Diaz][${env.JOB_NAME}][${params.herramienta}] Ejecución fallida en stage ${TAREA}."
			}
		}
}
