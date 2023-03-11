# Account Service
Account service platform using REST API and Spring Security to store, manage payments for an accounting service.
Service uses HTTPS Protocol and self-signed cert, use HTTPS to access the endpoints of the project.

The service uses database to store user data, with secured hashed passwords, stroke each user roles and log all the events that is happening on the server on separate database.

# Roles 
- Admin role will be able to view, modify roles, modify access to other users.
- Accountant will be able to process payments to users
- User role will be able to log in, register and check payments received

All roles will be able to change passwords of their account. Upon creation, the first user registered will be granted the Admin role by default, and the Admin will grand newly registered accounts the Accountant role.

# Requirement
- Java version 17+ <a href="https://www.oracle.com/de/java/technologies/downloads/">Java download Link</a>

# Build - Run Project
- Clone repository and navigate into repo's directory
- Run project with `$ ./gradlew bootRun`
 
# Shutdown Server
Actuator is enabled by default, and you will be able to shut down by sending POST request to endpoint `localhost:8080/actuator/shutdown`
`localhost:8080` is the default port

