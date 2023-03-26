# initial-dropwizard
 Apache Maven path to download maven :

    https://maven.apache.org/download.cgi


  Youtube Video :

    https://www.youtube.com/watch?v=9S5lqB11OPI&ab_channel=CloudSecurityTraining%26Consulting


After setting up ... if you change the new terminal mvn -v is not working then follow below steps

     1. Go to /Users folder
     2. cat ~/.zshenv
     3. vi ~/.zshenv ( then edit the file )
     4. Below paths should be present in this file

            export JAVA_HOME=$(/usr/libexec/java_home)
            export M2_HOME=/opt/apache-maven-3.9.0/
            export PATH=/opt/apache-maven-3.9.0/bin:$PATH

now open any terminal after saving the file and run mvn -v , now it should work in all the terminals in mac

## Curl Apis : 

##### Health Check Api : 

    curl --location 'http://localhost:3002/healthcheck'

##### Create New User : 

      curl --location 'http://localhost:3001/users' \
      --header 'Content-Type: application/json' \
      --data-raw '{
          "name": "Harsh Chaurasiya",
          "email": "harsh@yopmail.com",
          "password" :"qwerty123"
      }'
      
      
##### Get All Users : 


     curl --location 'http://localhost:3001/users'
     
     
###### Get Single User By User Id : 


     curl --location 'http://localhost:3001/users/1'
    
###### Update User By UserId : 
    
      curl --location --request PUT 'http://localhost:3001/users/3' \
      --header 'Content-Type: application/json' \
      --data-raw '{
          "name": "Harsh Chaurasiya 1",
          "email": "harsh1@yopmail.com",
          "password": "qwerty123"
      }'

###### Delete User : 

    curl --location --request DELETE 'http://localhost:3001/users/1'
    
    
  
