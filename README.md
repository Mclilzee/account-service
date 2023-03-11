# Account Service
Account service platform using REST API and Spring Security to store, manage payments for an accounting service.
Service uses HTTPS Protocol and self-signed cert, use HTTPS to access the endpoints of the project.

This server uses HTTPS Protocol and Self Signed Cert, Make sure to send all your requests via HTTPS for it to work. You will be able to provide your own properly signed Certificate if you wish, Self Signed Was used for testing purposes and to make it easier to pass around.
The `service.p12` Self Signed Cert is provided with the project, feel free to create your own and put them in `resources/application.properties`

The service uses database to store user data, with secured hashed passwords, stroke each user roles and log all the events that is happening on the server on separate database.

# Roles 
- Admin role will be able to view, modify roles, modify access to other users locking their accounts and unlocking them.
- Accountant will be able to process payments to users
- User role will be able to log in, register and check payments received

All roles will be able to change passwords of their account. Upon creation, the first user registered will be granted the Admin role by default, and the Admin will grand newly registered accounts the Accountant role.

# Requirement
- Java version 17+ <a href="https://www.oracle.com/de/java/technologies/downloads/">Java download Link</a>

# Build - Run Project
- Clone repository and navigate into repo's directory
- Run project with `$ ./gradlew bootRun`
 
# Shutdown Server
Actuator is enabled by default, and you will be able to shut down by sending POST request to endpoint `https://localhost:8080/actuator/shutdown`
`localhost:8080` is the default port

# Endpoints
All endpoints will start at port `https://localhost:8080` by default

## Registering
Registering obviously requires no previous role or to be logged in to create a new account
- POST Request to `api/auth/signup` will enable you to send JSON object body of user data with email and password as properties to register as a user.
Note: First created account will be granted the Admin role, others will be granted User role, the admin will have to give specific users the Accountant Role to be able to process payments, more details in the examples bellow.

## Admin
Admin will have access to the admin endpoint, where he will be able to grand, modify and adjust other people roles / access to the server.
 