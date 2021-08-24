#Tic tac toe game:

### To run this application you need to do the following steps:
## 1. Let's run a postgreSQL database
### Here is the magic command, (tested on windows): 
docker run --name local-postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_HOST_AUTH_METHOD=trust -d postgres

This will start to run a postgreSQL database in docker user is postgres, but password is not needed.

###Run the backend app:
mvnw spring-boot:run

This will start to listen on port 8080.
The app drop and recreate the database if it exists.
If you don't want to do this, just overwrite the

spring.jpa.hibernate.ddl-auto=create-drop property to

spring.jpa.hibernate.ddl-auto=validate

##Enjoy. :)
