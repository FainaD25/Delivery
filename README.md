# Delivery

## Overview

This is an application built with Java, Gradle and an in-memory H2 database. This README will guide you through the steps to build and run the application.

## Used technologies

* Spring Boot
* Gradle - build tool
* Lombok
* Liquibase
* H2 - database

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java JDK 21
- Gradle

## How to Build and Run the Application

1. Clone the repository

2. Run the application using Gradle:
```
./gradlew clean build
java -jar .\build\libs\Delivery-0.0.1-SNAPSHOT.jar
```
3. Access the backend application at http://localhost:8080

4. Access the database console at http://localhost:8080/h2-console/
JDBC URL: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
username: sa
leave the password field empty <br>

After running the application, the documentation can be seen at http://localhost:8080/swagger-ui.html
