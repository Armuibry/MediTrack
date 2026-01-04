# MediTrack - Clinic & Appointment Management System

## Project Description

MediTrack is a comprehensive Clinic & Appointment Management System built in Core Java. This project demonstrates proficiency in Object-Oriented Programming (OOP) principles, Java fundamentals, software design patterns, and modern Java features including streams, lambdas, and database integration.

## Features

### Core Features

1. **Patient Management**

   - Create, Read, Update, Delete (CRUD) operations for patients
   - Patient information including medical history, allergies, and insurance details
   - Search patients by ID, name, or age

2. **Doctor Management**

   - CRUD operations for doctors
   - Specialization-based doctor management
   - Consultation fee tracking
   - License number management

3. **Appointment Management**

   - Create and manage appointments
   - Appointment status tracking (CONFIRMED, CANCELLED, PENDING, COMPLETED)
   - View appointments by patient or doctor
   - Cancel and confirm appointments

4. **Billing System**

   - Automatic bill generation for appointments
   - Tax calculation (10% tax rate)
   - Bill summary generation (immutable)
   - Payment status tracking

5. **Search Functionality**
   - Dynamic search for patients (by ID, name, age)
   - Dynamic search for doctors (by ID, name, specialization)
   - Method overloading for polymorphic search

### Bonus Features

1. **AI Helper**

   - Rule-based doctor recommendation based on symptoms
   - Automatic appointment slot suggestions
   - Intelligent matching of patients to specialists

2. **Analytics (Java Streams & Lambdas)**

   - Average consultation fee calculation
   - Total revenue tracking
   - Appointments per doctor statistics
   - Most booked doctors analysis
   - Comprehensive analytics reports

3. **Design Patterns**

   - **Singleton Pattern**: ID Generator with thread-safe lazy initialization
   - **Factory Pattern**: Bill factory for different bill types
   - **Observer Pattern**: Appointment notification system

4. **File I/O & Persistence**
   - CSV utility for data export/import
   - SQLite database for persistent storage
   - Database schema management

## Technology Stack

- **Language**: Java 11
- **Database**: SQLite
- **Build Tool**: Maven
- **Architecture**: Controller-Service-Repository-Entity pattern

## Project Structure

```
src/main/java/com/airtribe/meditrack/
├── Main.java                          # Entry point with menu-driven UI
├── constants/
│   ├── Constants.java                 # Application constants
│   ├── Specialization.java            # Enum for medical specializations
│   └── AppointmentStatus.java         # Enum for appointment statuses
├── entity/
│   ├── Person.java                    # Base class for Person
│   ├── Doctor.java                    # Doctor entity (extends Person)
│   ├── Patient.java                   # Patient entity (extends Person)
│   ├── Appointment.java               # Appointment entity
│   ├── Bill.java                      # Bill entity (implements Payable)
│   ├── BillSummary.java               # Immutable bill summary
│   └── MedicalEntity.java             # Abstract medical entity class
├── repository/
│   ├── PatientRepository.java         # Data access layer for patients
│   ├── DoctorRepository.java          # Data access layer for doctors
│   ├── AppointmentRepository.java     # Data access layer for appointments
│   └── BillRepository.java            # Data access layer for bills
├── service/
│   ├── PatientService.java            # Business logic for patients
│   ├── DoctorService.java             # Business logic for doctors
│   └── AppointmentService.java        # Business logic for appointments
├── controller/
│   ├── PatientController.java         # Controller for patient operations
│   ├── DoctorController.java          # Controller for doctor operations
│   └── AppointmentController.java     # Controller for appointment operations
├── util/
│   ├── Validator.java                 # Centralized validation
│   ├── DateUtil.java                  # Date utility functions
│   ├── CSVUtil.java                   # CSV file operations
│   ├── IdGenerator.java               # Singleton ID generator
│   ├── DataStore.java                 # Generic data store
│   ├── DatabaseUtil.java              # Database connection and schema
│   ├── AIHelper.java                  # AI recommendation system
│   ├── AnalyticsUtil.java             # Analytics using streams/lambdas
│   ├── BillFactory.java               # Factory pattern for bills
│   └── AppointmentObserver.java       # Observer pattern for notifications
├── exception/
│   ├── AppointmentNotFoundException.java
│   └── InvalidDataException.java
├── interfaces/
│   ├── Searchable.java                # Interface for searchable entities
│   └── Payable.java                   # Interface for payable entities
└── test/
    └── TestRunner.java                # Manual test runner

docs/
├── Setup_Instructions.md              # Installation and setup guide
├── JVM_Report.md                      # JVM internals documentation
└── Design_Decisions.md                # Architectural decisions
```

## Setup Instructions

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven 3.6 or higher
- SQLite JDBC Driver (automatically downloaded via Maven)

### Installation Steps

1. **Clone or download the project**

2. **Install JDK** (if not already installed)

   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Set JAVA_HOME environment variable
   - Verify installation: `java -version` and `javac -version`

3. **Install Maven** (if not already installed)

   - Download from [Apache Maven](https://maven.apache.org/download.cgi)
   - Set MAVEN_HOME and add to PATH
   - Verify installation: `mvn -version`

4. **Build the project**

   ```bash
   cd MediTrack
   mvn clean compile
   ```

5. **Run the application**

   ```bash
   mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
   ```

   Or compile and run manually:

   ```bash
   mvn clean package
   java -cp target/classes:target/dependency/* com.airtribe.meditrack.Main
   ```

6. **Run tests**
   ```bash
   mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"
   ```

## How to Run

1. Start the application using the commands above
2. The main menu will be displayed
3. Navigate through the menu options:
   - **1. Patient Management**: Add, view, update, delete patients
   - **2. Doctor Management**: Add, view, update, delete doctors
   - **3. Appointment Management**: Create, view, cancel appointments
   - **4. Billing**: Create bills and view bill summaries
   - **5. Search**: Search for patients or doctors
   - **6. Exit**: Exit the application

## Sample Run Output

```
Welcome to MediTrack - Clinic Management System
==================================================

=== MAIN MENU ===
1. Patient Management
2. Doctor Management
3. Appointment Management
4. Billing
5. Search
6. Exit
Enter your choice: 1

=== PATIENT MANAGEMENT ===
1. Add Patient
2. View All Patients
3. View Patient by ID
4. Update Patient
5. Delete Patient
6. Back to Main Menu
Enter your choice: 1

=== ADD PATIENT ===
Name: John Doe
Date of Birth (yyyy-MM-dd): 1990-05-15
Email: john.doe@email.com
Phone Number: 1234567890
Medical History (optional): None
Allergies (optional): Peanuts
Insurance Provider (optional): ABC Insurance
Insurance Policy Number (optional): POL123456
Patient created successfully!
Patient ID: 1001
```

## OOP Principles Demonstrated

1. **Encapsulation**: All entity fields are private with getters/setters
2. **Inheritance**: Person → Doctor, Person → Patient
3. **Polymorphism**: Method overloading in PatientService, method overriding
4. **Abstraction**: Abstract MedicalEntity class, interfaces (Searchable, Payable)
5. **Advanced OOP**:
   - Deep cloning in Patient and Appointment
   - Immutable BillSummary class
   - Enums for Specialization and AppointmentStatus
   - Static initialization blocks

## Design Patterns Implemented

- **Singleton**: IdGenerator with thread-safe lazy initialization
- **Factory**: BillFactory for creating different bill types
- **Observer**: Appointment notification system
- **Repository**: Data access layer pattern
- **Service**: Business logic layer pattern
- **Controller**: Presentation layer pattern

## Java 8+ Features

- **Streams**: Used extensively in AnalyticsUtil
- **Lambdas**: Functional programming in stream operations
- **Optional**: Null-safe operations
- **Time API**: LocalDate, LocalDateTime

## Database Schema

The application uses SQLite with the following tables:

- `patients`: Patient information
- `doctors`: Doctor information
- `appointments`: Appointment details
- `bills`: Billing information

## Testing

Run the TestRunner class to execute manual test cases covering:

- Patient operations
- Doctor operations
- Appointment operations
- Search functionality
- Billing operations
- AI Helper features
- Analytics features

## Command-Line Arguments

- `--loadData`: Load data from CSV files on startup (bonus feature)

## Author

MediTrack Project - Demonstrating Java OOP and Design Patterns

## License

This project is created for educational purposes.
