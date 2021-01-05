def call()  {
  
        stage('Compile') {
          env.TAREA = 'Compile'
          sh './mvnw clean compile -e'
          }
  
        stage('Test') {
          env.TAREA = 'Test'
          sh './mvnw clean test -e'
          } 
      
        stage('Jar') {
          env.TAREA = 'Jar'
          sh './mvnw clean package -e'
          }
      
        stage('Sonar') {
          env.TAREA = 'Sonar'
          withSonarQubeEnv(installationName: 'sonar-server') {    
          sh './mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar' 
          }
          }  

        stage('UploadNexus') {
          env.TAREA = 'UploadNexus'
          nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.3']]]
          }   

}
return this;
