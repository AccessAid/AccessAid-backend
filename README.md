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

## Building the Project
To build the project, ensure that Maven is installed on your system. Then navigate to the project directory and run the following command:

```
mvn clean install
```
## Connecting to the Database
This project uses PostgreSQL as the database. To connect to the database locally, create a file named ```application.properties``` in the *src/main/resources* directory and add the following configuration:

```
spring.jpa.database=POSTGRESQL
spring.sql.init.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/accessaiddb
spring.datasource.username=accessaid
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql = true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto = create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.sendgrid.api-key=[YOUR_API_KEY]
app.jwt.secret=[YOUR_SECRET_256-bit]
```
Make sure to replace the username and password with your local database credentials.

## Running the Application
To run the application, navigate to the project directory and run the following command:

```
mvn spring-boot:run
```
The application should start and be available at http://localhost:8080.

