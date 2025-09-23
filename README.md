NutriTrack-AI 🥗💪

Your AI-powered fitness & nutrition tracker.

NutriTrack-AI is a Spring Boot based application that helps users track workouts, log diet entries, monitor calorie goals, and receive smart notifications about their daily health progress. Designed as a beginner-friendly yet resume-ready project, it demonstrates strong skills in Spring Boot, Hibernate/JPA, REST APIs, MySQL, and clean architecture.

🚀 Features

✅ User Management – Create and manage users with calorie goals.

✅ Diet Tracking – Log meals with calories & nutrients.

✅ Workout Tracking – Record workouts with duration & calories burned.

✅ Daily Calorie Goal – Monitor progress vs. target calories.

✅ Notifications – Get alerts on calorie surplus/deficit at day’s end.

✅ RESTful APIs – Built with Spring Boot & exposed as clean endpoints.

✅ Persistence Layer – Hibernate ORM with MySQL for data storage.

🛠️ Tech Stack

Backend: Spring Boot (MVC, Data JPA, AOP, Validation)

Database: MySQL (Hibernate ORM)

Build Tool: Maven

Java Version: Java 23

Testing: JUnit

📂 Project Structure
NutriTrack-AI/
├── entity/          # User, DietEntry, WorkoutEntry, Notification
├── repository/      # JPA repositories
├── service/         # Business logic
├── controller/      # REST APIs
├── dto/             # Data Transfer Objects
└── resources/       
    ├── application.properties
    └── schema.sql / data.sql

⚡ Getting Started
1️⃣ Clone the repository
git clone https://github.com/your-username/NutriTrack-AI.git
cd NutriTrack-AI

2️⃣ Configure MySQL

Create a database nutritrack_ai in MySQL.

Update application.properties with your DB credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/nutritrack_ai
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

3️⃣ Build & Run
mvn spring-boot:run

📌 Example API Endpoints
👤 User APIs

POST /api/users → Create a new user

GET /api/users/{id} → Get user by ID

🍽️ Diet APIs

POST /api/diets/{userId} → Add a diet entry for a user

GET /api/diets/{userId} → Get all diet entries

🏋️ Workout APIs

POST /api/workouts/{userId} → Add a workout entry

GET /api/workouts/{userId} → Get workouts for user

🔔 Notifications

POST /api/users/{id}/daily-summary/notify?date=YYYY-MM-DD → Send daily summary notification

🎯 Future Enhancements

🔹 Add JWT-based authentication & role management.

🔹 Frontend (React/Angular) for UI integration.

🔹 AI recommendation engine for personalized diet/workout plans.

🔹 Email/SMS notification support.

👨‍💻 Author

Shubham Rawat
📧 codersawan000@gmail.com

🔗 LinkedIn
 | GitHub

✨ NutriTrack-AI is built as a learning + portfolio project to demonstrate full-stack backend development using Spring Boot.
