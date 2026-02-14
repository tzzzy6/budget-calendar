# Budget Calendar Backend

Spring Boot backend for the Budget Calendar application.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **JdbcTemplate** (no JPA/ORM)
- **H2 Database** (development) - configured in MySQL compatibility mode
- **Maven** - build tool

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Start the Server

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`

### Build JAR

```bash
mvn clean package
```

Then run:
```bash
java -jar target/budget-calendar-1.0.0.jar
```

## Configuration

Configuration is in `src/main/resources/application.properties`

### H2 Database

- **URL**: `jdbc:h2:file:./data/budget_calendar`
- **Mode**: MySQL compatibility
- **Console**: Available at http://localhost:8080/h2-console
- **Username**: `sa`
- **Password**: (empty)

Database files are stored in `./data/` directory.

## API Endpoints

### Health Check

```bash
GET /api/health
```

Returns server status and version.

## Project Structure

```
src/main/java/com/budgetcalendar/
├── BudgetCalendarApplication.java  # Main application class
├── controller/                     # REST controllers
├── dto/                           # Data transfer objects
├── model/                         # Domain models
├── repos/                         # JdbcTemplate repositories
├── rowmappers/                    # ResultSet row mappers
└── service/                       # Business logic services
```

## Database Schema

Tables:
- `users` - User accounts
- `paydays` - User paydays (income schedule)
- `bills` - Recurring bills
- `payment_cards` - Generated payment cards for calendar

Schema is auto-initialized from `src/main/resources/schema.sql`

## Future: MySQL/MariaDB Migration

When ready to switch to MySQL/MariaDB:

1. Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/budget_calendar
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```

2. Migrate raw SQL queries to stored procedures
3. Update repositories to use `SimpleJdbcCall` or `CallableStatement`

The H2 MySQL compatibility mode ensures SQL syntax is already MySQL-compatible.
