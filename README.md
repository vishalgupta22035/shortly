# Shortly – URL Shortener REST API

Shortly is a backend REST API built using Java and Spring Boot that converts long URLs into short, shareable links. It provides URL redirection, click tracking, custom aliases, expiration management, and paginated URL retrieval.

## Features

- **URL Shortening:** Convert long URLs into unique 7-character short codes.
- **Custom Aliases:** Create personalized short URLs.
- **URL Redirection:** Automatically redirect short URLs to their original destinations.
- **Click Tracking:** Track the number of times a shortened URL is accessed.
- **Expiration Management:** Set optional expiration dates for shortened URLs.
- **URL Management:** Retrieve URL details and delete shortened URLs.
- **Pagination:** Retrieve stored URLs using page number and page size.
- **Duplicate Alias Handling:** Prevent duplicate custom aliases.
- **Exception Handling:** Handle missing URLs, expired URLs, and duplicate aliases with appropriate HTTP status codes.
- **API Documentation:** Interactive API documentation using Swagger UI.

## Tech Stack

- **Language:** Java 25
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **Database Integration:** Spring Data JPA / Hibernate
- **API Documentation:** Swagger / OpenAPI
- **Build Tool:** Maven
- **Validation:** Jakarta Bean Validation

## Project Structure

```text
src/main/java/com/shortly/
│
├── config/
├── controller/
│   ├── UrlController.java
│   └── RedirectController.java
│
├── dto/
│   ├── ShortenUrlRequest.java
│   └── ShortenUrlResponse.java
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── UrlExpiredException.java
│   └── CustomAliasAlreadyExistsException.java
│
├── model/
│   └── Url.java
│
├── repository/
│   └── UrlRepository.java
│
└── service/
    └── UrlService.java
```

## Getting Started

### Prerequisites

Make sure you have installed:

- Java 25 or a compatible Java version
- Maven
- PostgreSQL
- IntelliJ IDEA (recommended)

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/shortly.git
```

### 2. Navigate to the Project

```bash
cd shortly
```

### 3. Configure PostgreSQL

Make sure PostgreSQL is installed and running.

Create a database named `shortly`:

```sql
CREATE DATABASE shortly;
```

Configure your `src/main/resources/application.properties` file:

```properties
spring.application.name=shortly
server.port=8080

# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/shortly
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD

# JPA / Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Shortly Configuration
shortly.base-url=http://localhost:8080
```

Replace `YOUR_POSTGRES_PASSWORD` with your PostgreSQL password.

Alternatively, configure database credentials using environment variables and reference them in the properties file.

### 4. Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or run `ShortlyApplication.java` directly from IntelliJ IDEA.

The application starts at:

```text
http://localhost:8080
```

## API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/urls/shorten` | Create a shortened URL |
| GET | `/api/urls/{shortCode}` | Retrieve URL details |
| GET | `/api/urls` | Retrieve all URLs with pagination |
| DELETE | `/api/urls/{shortCode}` | Delete a shortened URL |
| GET | `/{shortCode}` | Redirect to the original URL |

## API Usage

### 1. Shorten a URL

**POST** `/api/urls/shorten`

Request:

```json
{
  "originalUrl": "https://www.google.com"
}
```

Response:

```json
{
  "originalUrl": "https://www.google.com",
  "shortUrl": "http://localhost:8080/GCNezOB",
  "shortCode": "GCNezOB",
  "createdAt": "2026-10-31T13:36:11.892938500Z",
  "expiresAt": null
}
```

### 2. Create a Custom Alias

**POST** `/api/urls/shorten`

Request:

```json
{
  "originalUrl": "https://www.google.com",
  "customAlias": "google"
}
```

Generated short URL:

```text
http://localhost:8080/google
```

Custom aliases must be between 3 and 20 characters and can contain letters, numbers, hyphens, and underscores.

### 3. Set URL Expiration

**POST** `/api/urls/shorten`

Request:

```json
{
  "originalUrl": "https://www.google.com",
  "expiresAt": "2026-11-05T18:00:00Z"
}
```

The URL becomes unavailable for redirection after its expiration time.

### 4. Retrieve URL Details

**GET** `/api/urls/{shortCode}`

Example:

```text
GET /api/urls/GCNezOB
```

Returns the original URL, short code, creation date, expiration date, and click count.

### 5. Retrieve All URLs

**GET** `/api/urls?page=0&size=5&sort=createdAt,desc`

Returns paginated URL records along with pagination metadata.

### 6. Redirect to Original URL

Open the shortened URL directly in your browser:

```text
http://localhost:8080/GCNezOB
```

The browser redirects to the original destination and increments the click count.

### 7. Delete a Shortened URL

**DELETE** `/api/urls/{shortCode}`

Example:

```text
DELETE /api/urls/GCNezOB
```

Returns `204 No Content` when deletion succeeds.

## HTTP Status Codes

| Status Code | Description |
|---|---|
| 200 OK | URL details retrieved successfully |
| 201 Created | URL shortened successfully |
| 302 Found | Redirect to original URL |
| 204 No Content | URL deleted successfully |
| 400 Bad Request | Invalid request or validation failure |
| 404 Not Found | Short URL does not exist |
| 409 Conflict | Custom alias already exists |
| 410 Gone | Short URL has expired |

## API Documentation

Swagger UI is available after starting the application:

```text
http://localhost:8080/swagger-ui/index.html
```

Use it to explore and test the available REST API endpoints.

## Database

PostgreSQL stores shortened URL records in the `urls` table inside the `shortly` database.

Hibernate maps the `Url` entity to the database table using Spring Data JPA.

Each record contains:

- Unique ID (UUID)
- Original URL
- Short code
- Creation timestamp
- Optional expiration timestamp
- Click count

The short code has a unique database constraint to prevent duplicate entries.

## Future Improvements

- Redis caching for frequently accessed URLs
- Automated unit and integration testing
- Authentication and user-specific URL management
- Rate limiting
- Production deployment

## Author

**Vishal Gupta**