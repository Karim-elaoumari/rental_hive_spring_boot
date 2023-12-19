# BâtisPro Equipment Rental Management System

## Project Overview

Welcome to the BâtisPro Equipment Rental Management System repository! This project aims to modernize BâtisPro's equipment rental process by introducing a comprehensive web application. The current challenge involves managing inventory manually, leading to errors and delays in the rental process. The solution is an integrated system that streamlines inventory management, equipment availability tracking, rental operations, and detailed reporting.

## Project Structure

- **Problem:**
  - BâtisPro manages its inventory with traditional paper files and spreadsheets, resulting in errors and delays.

- **Solution:**
  - Develop a web application for efficient inventory management, equipment tracking, rental operations, and reporting.

## Technologies Used

### Backend

- **Framework:**
  - Spring Boot

- **Database Migration:**
  - Liquibase

- **Testing:**
  - JUnit

### Frontend

- **Framework:**
  - Angular

## Implemented Features

### Phase 1

- **Inventory Management:**
  - Add new equipment with details.
  - Update existing equipment information.
  - Search for specific equipment.

- **Rental Operations:**
  - Record equipment rentals.
  - View rental history for specific equipment.

- **Additional Requirements:**
  - Unit tests for core functionalities.
  - Database migration with Liquibase.

### Phase 2

- **Request Management:**
  - Record new equipment rental requests.
  - View pending requests and their statuses.

- **Quoting Management:**
  - Generate detailed quotes for approved requests.
  - View and approve quotes.

- **Contract Management:**
  - Generate detailed contracts for approved quotes.
  - View active contracts.
  - Archive completed contracts.

- **File Management:**
  - Attach and store relevant files for documentation.

## How to Run

### Backend

1. Clone the repository. `https://github.com/UnesseAh/RentalHive-Spring-Boot`
3. Run `./mvnw spring-boot:run` to start the Spring Boot application.

### Frontend

1. Navigate to the `https://github.com/Karim-elaoumari/rental_hive_angular` repo.
2. Run `npm install` to get dependancies
3. Run `ng serve` to start the Angular application.

### Access

- Open a web browser and go to `http://localhost:4200` to access the application.

## Testing

- Run unit tests for each functionality using `./mvnw test` for the backend.

## Database Migration

- Liquibase scripts are provided for versioned database schema changes.

## Contribution

Feel free to contribute by forking the repository and submitting pull requests.

Thank you for exploring the BâtisPro Equipment Rental Management System! If you have any questions or feedback, feel free to open an issue or contact me directly. Happy coding! 🚀
