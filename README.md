# initial-dropwizard


Curl Apis : 

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
    
    
  
