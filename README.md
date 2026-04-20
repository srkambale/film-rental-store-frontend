# 🎬 Film Rental Store — 3-Laptop Deployment Guide

## Architecture Overview
```
[Laptop A: MySQL DB :3306] ← [Laptop B: Backend :8081] ← [Laptop C: Frontend :8080] ← Browser
```

---

## Laptop A — Database Setup
> **Who**: The database team member (Saniya)

1. Make sure **MySQL** is running on port `3306`.
2. Ensure the `sakila` database is loaded.
3. Note your laptop's **IP address** (run `ipconfig` in CMD → look for IPv4).
4. No Spring Boot app needed on this laptop.

---

## Laptop B — Backend Setup
> **Who**: The backend team member

### Step 1: Configure the database connection
Open: `film-rental-store-main/src/main/resources/application.properties`

Change this line:
```properties
spring.datasource.url=jdbc:mysql://10.30.74.55:3306/sakila
```
Replace `10.30.74.55` with **Laptop A's IP address**.

### Step 2: Run the backend
```powershell
cd e:\film-rental-store-main
mvn spring-boot:run
```

The backend starts at: **http://[LaptopB-IP]:8081**

---

## Laptop C — Frontend Setup
> **Who**: The frontend team member (Roshni)

### Step 1: Configure the backend URL
Open: `film-rental-store-frontend/src/main/resources/application.properties`

Change this line:
```properties
backend.base-url=http://localhost:8081
```
Replace `localhost` with **Laptop B's IP address**, e.g.:
```properties
backend.base-url=http://192.168.1.101:8081
```

### Step 2: Run the frontend
```powershell
cd e:\film-rental-store-frontend
mvn spring-boot:run
```

The UI is available at: **http://localhost:8080**  
(Or from any other device on the same network: **http://[LaptopC-IP]:8080**)

---

## Quick Start (All on One Laptop — Development)

If everyone is working from the **same laptop**, no IP changes needed. Just run:

**Terminal 1 (Backend):**
```powershell
cd e:\film-rental-store-main
mvn spring-boot:run
```

**Terminal 2 (Frontend):**
```powershell
cd e:\film-rental-store-frontend
mvn spring-boot:run
```

Then open: **http://localhost:8080**

---

## Project Structure

```
e:\
├── film-rental-store-main\         ← BACKEND (port 8081)
│   ├── src\main\java\com\example\demo\
│   │   ├── auth\          — JWT Security
│   │   ├── customer\      — Customer APIs
│   │   ├── catalog\       — Film/Actor/Category APIs
│   │   ├── rental\        — Rental & Inventory APIs
│   │   ├── admin\         — Admin Staff APIs
│   │   └── common\        — WebController (documentation proxy)
│   └── src\main\resources\
│       └── application.properties  ← Set DB IP here
│
└── film-rental-store-frontend\     ← FRONTEND (port 8080)
    ├── src\main\java\com\filmrental\frontend\
    │   └── controller\FrontendController.java
    └── src\main\resources\
        ├── application.properties  ← Set BACKEND IP here
        ├── templates\              — index, endpoints, results
        └── static\css\main.css    — Stylesheet
```

---

## Troubleshooting

| Problem | Solution |
|---|---|
| Frontend shows "Network Error" | Check `backend.base-url` in frontend's `application.properties` |
| Backend can't connect to DB | Check `spring.datasource.url` in backend's `application.properties` |
| 403 Unauthorized on API | Paste your JWT token in the "Global Authentication" field |
| CORS error in browser | Restart backend — CORS is auto-configured |
