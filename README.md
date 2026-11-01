# Coffee Washing Station Management System Backend

## Student
Kamali Elisee - Student ID: 23668

## Project
Coffee Washing Station Management System

## Tech stack
- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- PostgreSQL

## Run the API
1. Install PostgreSQL and start it.
2. Create a database (default: `coffee_washing_station`).
3. Update `src/main/resources/application.properties` with your PostgreSQL credentials.
4. Run the application (IntelliJ: run `CoffeeWashingStationApplication`, or `mvn spring-boot:run`).

## API
Base URL: `http://localhost:8080`

- Skills: `/api/skills/...`
- Drying tables: `/api/drying-tables/...`
- Supervisors: `/api/supervisors/...`
- Farmers: `/api/farmers/...`
- Cherry deliveries: `/api/cherry-deliveries/...`
- Parchment lots: `/api/parchment-lots/...`
- Dashboard: `/api/dashboard/summary`
- Cherry delivery page: `/api/cherry-deliveries/page`

## Postman
Collection: `coffee-washing-station-management-system-midterm.postman_collection.json`
