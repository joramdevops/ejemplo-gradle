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
			slackSend message: "[Joram Diaz][Pipeline-maven-gradle][${params.herramienta}], color: 'good' Ejecución exitosa."
			}
			failure {
			slackSend message: "[Joram Diaz][Pipeline-maven-gradle][${params.herramienta}], color: 'danger' Ejecución fallida en stage ${TAREA}."
			}
		}
}
