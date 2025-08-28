# 🎬 MovieList

A Spring Boot + HSQLDB application for managing movies and user watch states.  
Users can browse movies, add new ones, update details, delete them, and track their personal status (**WATCHED / TO_WATCH** with rating and comments).

This project was created as part of the **IT Crafters training program**.  
It demonstrates a clean **Controller → Service → Repository** layering, externalized SQL scripts (`schema.sql`, `data.sql`), and IntelliJ-friendly setup.  

👉 Repo: [https://github.com/raidolehtla/movielist](https://github.com/raidolehtla/movielist)

---

## 📑 Table of Contents

- [Description](#description)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
    - [Clone the repository](#clone-the-repository)
    - [Build & Run (IntelliJ IDEA)](#build--run-intellij-idea)
    - [Swagger UI](#swagger-ui)
- [Configuration](#configuration)
- [Database Initialization](#database-initialization)
- [Database Structure](#database-structure)
- [Available Endpoints](#available-endpoints)
- [Project Structure](#project-structure)

---

## 📝 Description

The **MovieList** application implements core CRUD operations for managing a movie list:

**MVP**

- Find a movie by ID
- List all movies
- Add a movie
- Update a movie
- Delete a movie
- Add/update user state (WATCHED / TO_WATCH + rating + comment)

Built on **Spring Boot** and **in-memory HSQLDB**, this project is reset on each startup using schema + seed data.

---

## ⚡ Prerequisites

- Java 21
- Gradle (or included `gradlew`)
- IntelliJ IDEA (recommended)

---

## 🚀 Getting Started

### Clone the repository

```bash
git clone https://github.com/raidolehtla/movielist.git
```

### Build & Run (IntelliJ IDEA)

1. **Open the project**  
   In IntelliJ, select **File ▸ Open…** and choose the root folder (`build.gradle` present).

2. **Run the app**  
   Navigate to `src/main/java/ee/raido/movielist/MovielistApplication.java`  
   Click the green ▶︎ icon or right-click → **Run 'MovielistApplication'**.

3. **Verify startup**  
   App runs on port **8080**.

4. **Swagger UI**  
   Open: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

5. **Stop the server**  
   Red ■ icon in IntelliJ Run window or `Ctrl + F2` (Win/Linux), `⌘ + F2` (macOS).

---

## ⚙️ Configuration

Runtime settings:

```
src/main/resources/application.properties
```

---

## 🗄️ Database Initialization

Spring Boot auto-runs:

- `schema.sql` → creates tables & constraints
- `data.sql` → inserts sample data (movies, users, user states)

---

## 🗃️ Database Structure

![ERD](docs/ERD.png)

- **movie** – Movies
- **user_account** – Application users
- **user_movie** – Relations: user ↔ movie with status, rating, comment

---

## 🌐 Available Endpoints

| Method | Path               | Description      |
|--------|--------------------|------------------|
| GET    | `/api/movies/{id}` | Get movie by ID  |
| GET    | `/api/movies`      | List all movies  |
| POST   | `/api/movies`      | Create new movie |
| PUT    | `/api/movies/{id}` | Update movie     |
| DELETE | `/api/movies/{id}` | Delete movie     |

---

## 📂 Project Structure

```text
movielist/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ee/raido/movielist/
│   │   │       ├── controller/               # REST controllers & DTOs
│   │   │       │   ├── movie/
│   │   │       │   │   ├── MovieController.java    # Movie endpoints
│   │   │       │   │   └── dto/
│   │   │       │   │       └── MovieDto.java       # Movie DTO
│   │   │       │   └── usermovie/
│   │   │       │       └── UserMovieDto.java       # User–Movie DTO
│   │   │       │
│   │   │       ├── infrastructure/          # Infra & config
│   │   │       │   ├── db/
│   │   │       │   │   └── HsqlServerConfig.java   # HSQLDB config
│   │   │       │   └── rest/
│   │   │       │       ├── error/
│   │   │       │       │   ├── ApiError.java       # Error payload
│   │   │       │       │   └── Error.java          # Error details
│   │   │       │       ├── exception/
│   │   │       │       │   ├── DataNotFoundException.java
│   │   │       │       │   └── ForbiddenException.java
│   │   │       │       └── RestExceptionHandler.java
│   │   │       │
│   │   │       ├── persistence/             # Entities & repos
│   │   │       │   ├── movie/
│   │   │       │   │   ├── Movie.java              # Movie entity
│   │   │       │   │   ├── MovieRepository.java    # Movie repo
│   │   │       │   │   └── MovieMapper.java        # Movie mapper
│   │   │       │   ├── useraccount/
│   │   │       │   │   ├── UserAccount.java        # User entity
│   │   │       │   │   └── UserAccountRepository.java
│   │   │       │   └── usermovie/
│   │   │       │       ├── UserMovie.java          # UserMovie entity
│   │   │       │       └── UserMovieRepository.java
│   │   │       │
│   │   │       ├── service/                # Services
│   │   │       │   └── movie/
│   │   │       │       └── MovieService.java       # Movie service
│   │   │       │
│   │   │       └── MovielistApplication.java       # Main app
│   │   │
│   │   └── resources/
│   │       ├── application.properties      # Config
│   │       ├── schema.sql                  # DB schema
│   │       ├── data.sql                    # Seed data
│   │       ├── static/                     # Static files
│   │       └── templates/                  # Templates
│   │
│   └── test/
│       └── java/
│           └── ee/raido/movielist/
│               └── MovielistApplicationTests.java
│
├── docs/
│   └── ERD.png                             # DB diagram
│
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── .gitignore
└── README.md

---

## ✅ Notes

- Database resets each restart (in-memory HSQLDB).
- Extendable: add new endpoints and entities by following **Controller → Service → Repository** layering.
- Check Swagger UI for live API docs.  
