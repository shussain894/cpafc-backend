# ⚽ CPAFC Team Admin – Backend API

This is the backend API for **CPAFC Team Admin App**, a role-based web application that allows coaches and admins to manage football teams, players (children), and training data.

Built with **Spring Boot** and **MongoDB**, the API enforces **role-based access control (RBAC)** for admin and coach roles.

## 📦 Tech Stack

- **Java 17**
- **Spring Boot 3.2.5**
- **MongoDB**
- **Spring Web + Spring Data MongoDB**
- **Bruno/Postman for API testing**

## 🚀 Features

### ✅ Role-Based Access Control (RBAC)

- Coaches can only view/edit children assigned to their team
- Admins can manage all data across all teams
- Role is determined by the `Role` enum in each `Coach` document (`ADMIN` or `COACH`)

### ✅ RESTful Endpoints

- CRUD operations for:
    - Children
    - Coaches
    - Teams
- Login route (`/api/login`) returns role info
- Uses custom Spring Interceptor to validate access per request

### ✅ Seed Data

- Prepopulated teams, coaches (with role/team), and children
- Located in `SeedData.java`

## 🔐 Authentication (Dev Mode)

Currently uses a placeholder `X-User-Email` header (in lieu of real authentication).  
Future enhancement: replace with Firebase Auth or Spring Security.

### ✅ Simulating Auth in Bruno

Add a custom header:
X-User-Email: admin@cpafc.org

Test different users:

- Admin: `shah@cpafc.org`
- Coach: `coach1@cpafc.org`
- Unauthorized: not present or invalid email

## 🔧 API Endpoints

### 🔑 `/api/login`
- **POST**
- Body: `{ "email": "shah@cpafc.org" }`
- Returns:
```json
{
  "email": "shah@cpafc.org",
  "isAdmin": true
}
```

---

### 7. Children Endpoints


### 👶 `/api/children`

| Method | Access      | Description                     |
|--------|-------------|---------------------------------|
| GET    | Admin/Coach | Get children (coach: own team)  |
| POST   | Admin/Coach | Add child to team               |
| PUT    | Admin/Coach | Update child (must own)         |
| DELETE | Admin/Coach | Delete child (must own)         |

### 🧑‍🏫 `/api/coaches`

| Method | Access  | Description             |
|--------|---------|-------------------------|
| GET    | All     | List all coaches        |
| POST   | All     | Register new coach      |
| PUT    | All     | Update coach            |
| DELETE | All     | Delete coach            |

### 🏆 `/api/teams`

| Method | Access      | Description              |
|--------|-------------|--------------------------|
| GET    | Admin/Coach | List all teams           |
| POST   | Admin only  | Create new team          |
| PUT    | Admin only  | Update team              |
| DELETE | Admin only  | Delete team              |

## 🛠 Local Setup

### 1. Clone the repo

```bash
git clone https://github.com/shussain894/cpafc-backend.git
cd cpafc-backend
```

### 2. Set up MongoDB
```bash
mongodb://localhost:27017
````
The app connects to a database called cpafc-test

### 3. Run the app

Using Maven wrapper:
```bash
./mvnw spring-boot:run
```

## 🔮 Next Steps

- 🌍 Build React or Next.js frontend
- 🔐 Add real authentication
- 🧪 Add integration & unit tests
- 🗃️ Add fixtures data and match results


