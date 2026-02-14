# Copilot Instructions for Budget Calendar

## Project Overview

A budgeting web app that visualizes bills and paydays on an interactive calendar. Users can drag and drop bill payment cards to plan when they'll pay each bill relative to their paydays.

**Core Concept**: Visual bill management through calendar-based drag-and-drop interface showing the relationship between income (paydays) and expenses (bills).

## Key Features

### User Flow
1. **Landing Page**: Hero section, screenshots, navigation with Sign Up/Login
2. **Onboarding**: 
   - Add paydays (amount, frequency: weekly/biweekly/etc.)
   - Add bills/recurring payments (amount, frequency, start date)
3. **Calendar View**: 
   - Paydays are highlighted/marked on calendar
   - Bill cards auto-generated for next 12 months based on start date
   - Drag-and-drop bill cards to plan payment dates
   - Add one-off payment cards

### Data Model Considerations
- **Paydays**: Multiple per user, with amount and frequency
- **Bills**: Recurring payments with start date to calculate approximate due dates
- **Payment Cards**: Generated tiles for each bill occurrence (12-month horizon)
- **One-time Payments**: Single cards that can be placed on calendar

## Tech Stack

### Frontend
- **Next.js** - React framework with SSR/SSG capabilities
- Drag-and-drop library TBD (consider react-dnd, dnd-kit, or react-beautiful-dnd)
- Calendar component TBD

### Backend
- **Spring Boot** - Java backend framework
- **JdbcTemplate** for database interactions (NOT JPA/Hibernate)
- REST API for frontend communication

### Database
- **H2** (development) - Lightweight SQL database, configured in MySQL mode
- **MySQL/MariaDB** (production) - Production-ready relational database

### Deployment
- TBD

## Database Strategy

### Using H2 in Development
Configure H2 to run in MySQL compatibility mode to minimize migration issues:
```properties
spring.datasource.url=jdbc:h2:file:./data/budget;MODE=MySQL;DATABASE_TO_LOWER=TRUE
```

### JdbcTemplate Approach
Since this project uses **JdbcTemplate instead of JPA/ORM**, follow these practices:

1. **Write MySQL-compatible SQL from the start** - Don't use H2-specific functions or syntax
2. **Test critical queries in both databases** during development if possible
3. **Use parameterized queries** via `?` placeholders for security and portability
4. **Standard SQL only** - Avoid vendor-specific extensions
5. **Mind the differences**:
   - Auto-increment: Use `AUTO_INCREMENT` in DDL (MySQL syntax)
   - String comparison: Be aware MySQL can be case-insensitive by default
   - Date/time functions: Use standard SQL date functions or Java-side processing

### Migration Path: Raw SQL → Stored Procedures
**Development (H2)**: Use raw SQL queries in JdbcTemplate for rapid iteration

**Production (MySQL/MariaDB)**: Migrate to stored procedures for better separation between data layer and backend

When making the switch:
1. Convert raw SQL queries to stored procedures in MySQL
2. Update JdbcTemplate calls to use `SimpleJdbcCall` or `CallableStatement`
3. Keep stored procedure definitions in version-controlled `.sql` files
4. Benefits: Granular control, better data/backend separation, potential performance improvements

### Schema Management
- Use Spring Boot's `schema.sql` and `data.sql` for initial setup
- Keep separate SQL files if H2 and MySQL require different syntax
- Version control all schema changes and stored procedure definitions

## Development Conventions

When implementing features, follow this order:
1. Set up authentication and user profiles first
2. Implement payday management before bills (bills reference paydays visually)
3. Build bill management with date calculation logic
4. Create calendar view with read-only display before adding drag-and-drop
5. Add one-time payments last (simplest feature)

### Frequency Handling
Bills and paydays use frequency values: "weekly", "biweekly", "monthly", "quarterly", "yearly". The start date combined with frequency determines all future occurrences.

### Calendar Date Calculations
- Generate 12 months of bill cards from current date
- Use start date + frequency to calculate approximate due dates
- Store user's actual planned payment date separately from calculated due date
