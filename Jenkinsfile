pipeline {
    agent any
    parameters { choice(name: 'HERRAMIENTA', choices: ['gradle', 'maven'], description: 'opcion de compilacion')}
    stages {
        stage('Pipeline') {
            steps {
                script {
                env.TAREA = ''
                echo "HERRAMIENTA SELECCIONADA: ${params.HERRAMIENTA}"   
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"                             
                if (params.HERRAMIENTA == 'gradle'){
                    	def ejecucion = load 'gradle.groovy'
	                    ejecucion.call()
                } else {
                    	def ejecucion = load 'maven.groovy'
	                    ejecucion.call()                    
                }

                }
            }
        }
    }
