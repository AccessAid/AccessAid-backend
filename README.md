# AccessAid
AccessAid is an information and accessibility guide platform for public and private spaces. It is built using Java 17 and the Spring Boot framework. This project uses Maven as the build automation tool.


## Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- PostgreSQL JDBC Driver
- Project Lombok
- SpringDoc OpenAPI UI
- Spring Boot Starter Security
- Spring Boot Starter Validation
- Google Maps Services
- SLF4J Simple
- Spring Boot Starter Mail
- JSON
- Mockito Core
- JJWT API
- JJWT Implementation
- JJWT Jackson

## Building the Project
To build the project, ensure that Maven is installed on your system. Then navigate to the project directory and run the following command:

```
mvn clean install
```
## Connecting to the Database and External Services
This project requires several environment variables to connect to the external API services, the database, and the email service. To set up these variables locally, create a file named `.env` in the root directory of the project. You can find an example `.env` file in the `.env.example` file provided.

#### Database:
  This project uses PostgreSQL as the database. Database name, username and password should be added in `.env` file.
#### External API Services: 
  This projects uses Google Maps API, and it requires an API Key from Google. To connect to the Google Maps API, obtain an API Key from the Google Cloud Console and add it to the `.env` file.
#### Email Service Configuration: 
Configure the necessary variables to connect to the email service in the `.env` file. These variables include the SMTP host, port, username, and password. Consult your email service provider's documentation for the required configuration variables and add them to the `.env` file.


## Running the Application
To run the application, navigate to the project directory and run the following command:

```
mvn spring-boot:run
```
The application should start and be available at http://localhost:8080.

