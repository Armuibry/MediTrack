# Design Decisions - MediTrack

This document explains the architectural choices and design patterns used in the MediTrack application.

## Architecture Overview

MediTrack follows a **layered architecture** pattern with clear separation of concerns:

```
Controller Layer (Presentation)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
Database (SQLite)
```

---

## 1. Layered Architecture (Controller-Service-Repository)

### Decision: Use Layered Architecture

**Rationale:**

- **Separation of Concerns**: Each layer has a single responsibility
- **Maintainability**: Changes in one layer don't affect others
- **Testability**: Each layer can be tested independently
- **Scalability**: Easy to add new features or modify existing ones

### Controller Layer

**Purpose:** Handle user input/output and coordinate between UI and services.

**Responsibilities:**

- Receive user requests
- Validate input format
- Call appropriate service methods
- Format output for display
- Handle exceptions and error messages

**Example:**

```java
public class PatientController {
    private final PatientService patientService;

    public Patient createPatient(...) {
        return patientService.createPatient(...);
    }
}
```

### Service Layer

**Purpose:** Implement business logic and orchestrate operations.

**Responsibilities:**

- Business rule validation
- Business logic execution
- Coordinate between multiple repositories
- Transaction management (if needed)
- Handle business exceptions

**Example:**

```java
public class PatientService {
    private final PatientRepository repository;

    public Patient createPatient(...) {
        // Business logic: validation
        Validator.validateName(name);
        // Create patient
        return repository.create(patient);
    }
}
```

### Repository Layer

**Purpose:** Abstract data access logic.

**Responsibilities:**

- Database operations (CRUD)
- SQL query execution
- Result set mapping
- Connection management
- No business logic

**Example:**

```java
public class PatientRepository {
    public Patient create(Patient patient) {
        // Pure data access logic
        String sql = "INSERT INTO patients ...";
        // Execute and map results
    }
}
```

---

## 2. Entity Design

### Decision: Use Proper OOP Principles

#### Encapsulation

**All fields are private with getters/setters.**

**Rationale:**

- Control access to data
- Enable validation in setters (if needed)
- Maintain data integrity
- Follow JavaBean conventions

#### Inheritance Hierarchy

```
Person (Base Class)
├── Doctor
└── Patient
```

**Rationale:**

- Code reuse: Common fields (name, email, phone) in Person
- Polymorphism: Can treat Doctor and Patient as Person
- Extensibility: Easy to add new person types

#### Immutability

**BillSummary is immutable (final class, final fields, no setters).**

**Rationale:**

- Thread safety without synchronization
- Prevent accidental modifications
- Clear contract: Once created, cannot be changed
- Useful for value objects

---

## 3. Design Patterns

### 3.1 Singleton Pattern (IdGenerator)

**Decision:** Use singleton for ID generation.

**Implementation:** Thread-safe lazy initialization with double-checked locking.

**Rationale:**

- Single source of truth for ID generation
- Thread-safe ID generation across the application
- Prevent ID collisions
- Efficient memory usage

**Code:**

```java
public class IdGenerator {
    private static volatile IdGenerator instance;

    public static IdGenerator getInstance() {
        if (instance == null) {
            synchronized (IdGenerator.class) {
                if (instance == null) {
                    instance = new IdGenerator();
                }
            }
        }
        return instance;
    }
}
```

### 3.2 Factory Pattern (BillFactory)

**Decision:** Use factory pattern for bill creation.

**Rationale:**

- Centralize bill creation logic
- Support different bill types (Standard, Discounted, Premium)
- Hide complex creation logic from clients
- Easy to extend with new bill types

**Code:**

```java
public static Bill createBill(String type, int appointmentId,
                              double baseAmount, double additionalParam) {
    switch (type) {
        case "STANDARD": return createConsultationBill(...);
        case "DISCOUNTED": return createDiscountedBill(...);
        case "PREMIUM": return createPremiumBill(...);
    }
}
```

### 3.3 Observer Pattern (Appointment Notifications)

**Decision:** Use observer pattern for appointment notifications.

**Rationale:**

- Decouple notification logic from appointment management
- Support multiple notification types
- Easy to add new observers
- Follows Open/Closed Principle

**Code:**

```java
public interface AppointmentObserver {
    void update(Appointment appointment);
}
```

### 3.4 Repository Pattern

**Decision:** Use repository pattern for data access.

**Rationale:**

- Abstract database implementation details
- Easy to switch databases
- Testable: Can mock repositories
- Single Responsibility: Each repository handles one entity

---

## 4. Exception Handling Strategy

### Decision: Use Custom Exceptions

**Custom Exceptions:**

- `AppointmentNotFoundException`: Business exception for missing appointments
- `InvalidDataException`: Validation exceptions

**Rationale:**

- Better error messages
- Exception chaining for debugging
- Type-safe error handling
- Clear error semantics

**Exception Chaining:**

```java
throw new InvalidDataException("Invalid email", cause);
```

---

## 5. Database Design

### Decision: Use SQLite

**Rationale:**

- Lightweight and embedded
- No separate database server needed
- Good for small to medium applications
- Easy deployment
- ACID compliant

### Schema Design

**Tables:**

- `patients`: Patient information
- `doctors`: Doctor information
- `appointments`: Appointment details
- `bills`: Billing information

**Foreign Keys:**

- Appointments reference patients and doctors
- Bills reference appointments

**Rationale:**

- Normalized design (3NF)
- Data integrity through foreign keys
- Easy to query and maintain

---

## 6. Validation Strategy

### Decision: Centralized Validation (Validator Class)

**Rationale:**

- DRY principle: Single place for validation logic
- Consistent validation across the application
- Easy to modify validation rules
- Reusable validation methods

**Example:**

```java
public static void validateEmail(String email) {
    if (!EMAIL_PATTERN.matcher(email).matches()) {
        throw new InvalidDataException("Invalid email format");
    }
}
```

---

## 7. Utility Classes

### Decision: Static Utility Classes

**Utilities:**

- `Validator`: Validation logic
- `DateUtil`: Date formatting and parsing
- `CSVUtil`: CSV file operations
- `DatabaseUtil`: Database connection management

**Rationale:**

- Stateless operations: No need for instances
- Easy to use: Just call static methods
- Performance: No object creation overhead
- Clear purpose: Each utility has a specific function

---

## 8. Enums for Constants

### Decision: Use Enums Instead of Constants

**Enums:**

- `Specialization`: Medical specializations
- `AppointmentStatus`: Appointment statuses

**Rationale:**

- Type safety: Compile-time checking
- Better IDE support: Autocomplete and refactoring
- Can have methods and fields
- Prevents invalid values

**Example:**

```java
public enum Specialization {
    CARDIOLOGY("Cardiology", "Heart and cardiovascular system"),
    DERMATOLOGY("Dermatology", "Skin, hair, and nails");

    private final String displayName;
    private final String description;
}
```

---

## 9. Java 8+ Features

### Decision: Use Streams and Lambdas

**Usage:**

- `AnalyticsUtil`: All analytics use streams
- `PatientService`: Stream filtering for age-based search

**Rationale:**

- Functional programming style
- Concise and readable code
- Parallel processing capability
- Modern Java best practices

**Example:**

```java
return allPatients.stream()
    .filter(p -> p.getAge() == age)
    .collect(Collectors.toList());
```

---

## 10. Method Overloading (Polymorphism)

### Decision: Overload searchPatient() Method

**Methods:**

```java
Patient searchPatient(int id)
List<Patient> searchPatient(String name)
List<Patient> searchPatient(int age)
```

**Rationale:**

- Intuitive API: Same method name for related operations
- Compile-time polymorphism
- Type safety: Compiler determines which method to call
- Clean interface

---

## 11. Cloning Strategy

### Decision: Implement Deep Cloning

**Implementation:**

- `Patient` implements `Cloneable`
- `Appointment` implements `Cloneable`

**Rationale:**

- Prevent accidental modifications
- Create independent copies
- Support prototype pattern usage
- Immutable-like behavior for mutable objects

---

## 12. AI Helper Design

### Decision: Rule-Based Recommendation System

**Rationale:**

- Simple and effective for demo purposes
- Easy to understand and modify
- Fast execution
- Can be extended with ML later

**Implementation:**

- Symptom-to-specialization mapping
- Slot suggestion based on availability
- Least-busy doctor selection

---

## 13. Thread Safety

### Decision: Use AtomicInteger and Volatile

**In IdGenerator:**

- `AtomicInteger` for thread-safe counters
- `volatile` for singleton instance

**Rationale:**

- Thread-safe without explicit synchronization
- Better performance than synchronized blocks
- Prevent race conditions
- Suitable for concurrent access

---

## 14. Package Structure

### Decision: Organize by Layer and Feature

**Structure:**

```
com.airtribe.meditrack/
├── controller/     # Presentation layer
├── service/        # Business logic layer
├── repository/     # Data access layer
├── entity/         # Domain models
├── util/           # Utilities
├── exception/      # Custom exceptions
├── interfaces/     # Interfaces
└── constants/      # Constants and enums
```

**Rationale:**

- Clear organization
- Easy to navigate
- Standard Java package conventions
- Scalable structure

---

## 15. Testing Strategy

### Decision: Manual Test Runner

**Implementation:**

- `TestRunner` class with comprehensive test cases
- Tests all major functionality
- Demonstrates application usage

**Rationale:**

- Educational purposes: Shows how to test
- Easy to understand for beginners
- Can be extended with JUnit later
- Provides usage examples

---

## Future Enhancements

1. **Dependency Injection**: Use Spring Framework or manual DI
2. **Transaction Management**: Add transaction boundaries
3. **Caching**: Implement caching for frequently accessed data
4. **Logging Framework**: Add proper logging (Log4j, SLF4J)
5. **Unit Testing**: Add JUnit test cases
6. **REST API**: Expose REST endpoints
7. **Security**: Add authentication and authorization
8. **Database Migration**: Use Flyway or Liquibase

---

## Conclusion

The design decisions in MediTrack prioritize:

- **Clarity**: Easy to understand and maintain
- **Separation of Concerns**: Each component has a clear purpose
- **Extensibility**: Easy to add new features
- **Best Practices**: Following Java and OOP best practices
- **Educational Value**: Demonstrating key concepts

These decisions make the codebase maintainable, testable, and scalable.
