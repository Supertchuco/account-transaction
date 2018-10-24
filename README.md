This is an API to perform money transaction management, I developed this API using Intellij IDE and these technologies:

Springboot;
Gradle;
Oracle Database;
H2 (memory database);
Swagger;
Lombok;
Actuator;
Spring rest.

This API allow these operations:

Create new client: http://localhost:8090/account-transaction/client/createClient
Create new Account: http://localhost:8090/account-transaction/account/createAccount
Create new Money Transaction Between two Accounts: http://localhost:8090/account-transaction/transaction/doTransferMoney


You can check the all rest operations and the payloads through Swagger, to do it you need to running the application and access this url:http://localhost:8090/account-transaction/swagger-ui.html

This project have these kind of tests:

Integration Tests (8 tests): ParkingManagementIntegrationTest class;
Unity Tests (23 tests) for all services classes.

The integrantion tests are using memory database (H2) and create the data, you can verify it on seed.sql and purge.sql files.

Note: You can run the tests through Intellij IDE or call direct in Gradle (execute the command "gradlew clean test" in cmd prompt)

Step to running the project:

Intellij IDE:
    Import the project like Gradlew Project;
    Mark the check box Enable Annotation Processing in Settings->Build->Execution->Deployment->Compiler->Annotation Processor
    Run the major class ParkingManagementApplication.
	
	*Detail: if you want to use Oracle database connection you need to configure the connection on application-prod.properties and use this profile or if you want just use memory database (H2) you just need to use the default profile.

Without an IDE:
    Execute the command "gradlew clean build" inside the project in a cmd prompt;
    Execute command "java -jar account-transaction.jar" in "parking-management\build\libs" directory.

In both of cases you can check if the application is running accessing this url http://localhost:8090/account-transaction/actuator/health (actuator feature);

Important Note: This project is configured to create the database tables structure every time that the application is started, if you want to disable it, you need to comment (#) or delete this line "spring.jpa.hibernate.ddl-auto=update" on application.properties.

If you have questions, please feel free to contact me.
