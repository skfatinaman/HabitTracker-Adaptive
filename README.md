# 🧠 Habit Tracker (Adaptive)

A simple **Command Line Habit Tracker App** that helps you build consistency and stay motivated.  
It automatically adjusts your goals based on your performance — increasing targets when you’re doing great and easing them when you’re struggling.  

✨ *My first full Java project — built with care and curiosity!* ❤️  

---

## 🚀 Features

- 🎯 **Adaptive Goal Engine**  
  Tracks your success rate and adjusts your targets:
  - Increases target if your record is good  
  - Decreases target if progress drops  

- 💬 **Minimal CLI Interface**  
  Simple, clean, and easy to use — no extra fluff.

- 💾 **Automatic Data Saving**  
  All your progress is saved locally:
  ```
  data/
   └── username/
        ├── exercise.txt
        ├── reading.txt
        └── meditation.txt
  ```
  - Each **user** has their own folder  
  - Each **habit** is stored as a text file inside that folder  

- 🔁 **Tracks streaks, completion rate, and logs**

- 🧩 **Lightweight — No external database or frameworks**

---

## 🧱 Tech Stack

- **Language:** Java  
- **Interface:** CLI (Command Line)  
- **Storage:** Local file system (`java.io` & `java.nio`)  
- **Date Handling:** `java.time.LocalDate`  

---

## ⚙️ How to Run

### Option 1: Run the JAR file
```bash
java -jar HabitTracker-Adaptive.jar
```

### Option 2: Use the EXE file (for Windows)
You can run the provided `HabitTracker-Adaptive.exe` file directly to launch the CLI app.

---

## 📁 Project Structure

| Folder | Description |
|---------|--------------|
| `model` | Core classes like `Habit`, `HabitLog`, and `User` |
| `logic` | Adaptive goal engine that updates targets |
| `storage` | Handles saving and loading user data |
| `main` | CLI and entry point of the program |
| `data` | Automatically created folder for user data |

---

## 🧠 How It Works

1. Create or load a user profile.  
2. Add habits you want to track (e.g., “Exercise”, “Read”, “Sleep early”).  
3. Log your progress daily.  
4. The app tracks:
   - ✅ Success rate  
   - 🔁 Streaks  
   - 🎯 Adaptive goal adjustments  

Over time, your targets automatically adapt:
- **Good performance → higher targets**  
- **Poor consistency → easier targets**  

---

## 🪄 Example Habit File

```
Name: Exercise
Target: 30
Basis: minutes
Adaptive: true
Streak: 4
Logs:
2025-10-01 true
2025-10-02 false
2025-10-03 true
2025-10-04 true
```

---

## ❤️ About This Project

This is my **first full Java project**, where I learned:
- Object-oriented programming
- File handling and persistence
- CLI design
- Adaptive logic systems

> It’s a simple start — but the goal is to keep improving, just like the app itself!

---

## 🧩 Future Ideas

- Add graphs for progress visualization  
- Optional GUI mode  
- Cloud save or JSON export  
- Daily reminders  

---

## 👤 Author -- Sheikh Fatin Aman

**Fatin Aman**  
GitHub: [@skfatinaman](https://github.com/skfatinaman)
