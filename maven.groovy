def call()  {
  
    stage('Compile') {
         
          sh 'mvn clean compile -e'
          
         }
        stage('Test') {
         
          sh './mvnw clean test -e'
        
        } 
      stage('Jar') {
       
         sh './mvnw clean package -e'
      }
      
      stage('sonar') {
        
          withSonarQubeEnv(installationName: 'Sonar') {    
           sh './mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar' 
        
       }
      }  

      stage('uploadNexus') {
         
          nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
         
       }   
}

return this;
