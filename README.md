# Payroll System
A payroll system for Wave software development challenge. The backend is coded with `Kotlin` in the `Spring Boot` framework, by use of the `MySQL` database. The front end is based on `React`.  

## Setting up the Database
To run the backend of the application, you'll need to configure a MySQL database. Here's a step-by-step guide:
1. If you haven't already, install MySQL on your system.
2. Open the MySQL shell by using the command: `mysql -u root -p.`
3. In the MySQL shell, create a database named _wave_payroll_ by executing the following command: `CREATE DATABASE wave_payroll;`.
4. Edit the _application.properties_ file to ensure that the database connection details match your MySQL setup.

When you run the backend for the first time, the application will automatically create the necessary tables in the _wave_payroll_ database.

## Running the backend
### Using IntelliJ
1. Clone/Open the project in IntelliJ.
2. In the backend folder, right-click on the _pom.xml_ file and select "Import as a Maven Project."
3. Open the _PayrollApplication.kt_ file and run it to start the server.
4. The server will be accessible at _http://localhost:8080_

### Using Terminal
1. Open the terminal and navigate to the _backend_ directory using `cd backend`.
2. Build the backend and create a jar file using `mvn package`.
3. Go to the _target_ directory using `cd target`
4. Run the jar file using `java -jar payroll-0.0.1.jar`.
5. The server will run on _http://localhost:8080_

## Running the front end from the terminal
1. Open your terminal and navigate to the _frontend_ directory using `cd frontend`.
2. Install the required packages by running `npm install`.
3. Start the React application using `npm run`.
4. The React application will be accessible at _http://localhost:3000_

![A screenshot from the UI](application.jpeg)

## APIs
### The API for uploading a new CSV file
```
POST http://localhost:8080/api/upload?file=<MultipartFile>
```

### The API for getting a report
```
GET http://localhost:8080/api/report
```

## Answers to Questions
### 1. How did you test that your implementation was correct? 
I tested the implementation using both positive and negative test cases, using the sample CSV file. These tests were designed to ensure that the system correctly parsed and processed the data, aggregated the records to payroll reports, and calculated the pay periods. I used some records of employees 1 and 4 to make sure the results were correct. I also tested exceptional states, such as handling invalid CSV files and duplicate CSV IDs.

### 2. If this application was destined for a production environment, what would you add or change?
For using the application for a production environment, the report API should support filters to allow users to retrieve specific subsets of data based on criteria like date ranges, employee IDs, or job groups. In that case, adding a cache server can improve performance by temporarily storing frequently accessed data. The current backend isn't horizontally scalable, so I would plan to break down the system into several modules, adopting a microservices architecture, consisting of a Gateway service, a Reporting Service, and a Core service responsible for managing data and interacting with the database. Depending on the system's load, I'm open to switching to another database system, such as a NoSQL database. The use of message queueing systems can also be considered at that point. We should Dockerise services and have CI/CD pipelines, and a container orchestration system like Kubernetes to easily update and manage the production, and also ensure high availability. Providing security measures, such as adding authentication and rate limiting for users, is essential for protecting the system and its data. Additionally, we would need monitoring and alerting systems to track the system's health.

### 3. What compromises did you have to make as a result of the time constraints of this challenge?
I had to make a few compromises due to time constraints. While I have some basic testing, I would have dedicated more time to implementing comprehensive boundary tests and maximizing the line and branch coverage. I didn't support the currency in my calculations, I only considered dollars. The user interface is minimal but functional. Given more time, I would have focused on improving its appearance and user experience. I also didn't Dockerize the system.
