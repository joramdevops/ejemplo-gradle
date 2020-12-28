pipeline {
    agent any
    parameters { choice(name: 'herramienta', choices: ['gradle', 'maven'], description: 'compilacion')}
    stages {
        stage('Pipeline') {
            steps {
                script {
                
                params.herramienta                           
                if (params.herramienta == 'gradle'){
                    	def ejecucion = load 'gradle.groovy'
	                    ejecucion.call()
                } else {
                    	def ejecucion = load 'maven.groovy'
	                    ejecucion.call()                    
                }
		
            }
        }
    }
