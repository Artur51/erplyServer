This project is to manipulate random data on Erply products management platform and on the local Postgres database running vai docker-compose.

This project is required a docker-compose to be installed.

To run the project first you have to execute the docker-compose command "docker-compose up" from the project root location. This will make the Postgres database run.
Then you need to compile and run the project.
Java main class to run is located at com.middleware.erply.ErplyApplication.java

As soon as the project is running you are able to navigate to the swagger HTML documentation which also provides the ability to invoke code on the server endpoint.

Swagger documentation URL is:
http://localhost:8080/swagger-ui/index.html

This server has user authentication with the JWT token.
First, you need to register a new user, then log in with registered credentials to obtain a token.
Next, with the token, you are able to use data operations.
Data operations are simplified. With the data operations, this server provides, first you can generate random product-related records. Then you can update, read and delete these records.
Most of the data operations are designed to manipulate with a limited records count (1 - 10 items per operation). 