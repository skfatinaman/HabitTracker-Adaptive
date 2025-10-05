package model;

import java.util.ArrayList;

public class User {
    private String userName;
    private ArrayList<Habit> habits;

    public User(String userName) {
        this.userName = userName;
        this.habits = new ArrayList<>();
    }

    public User() {
        this("default_user");
    }

    // Add habit object (used by storage loader)
    public void addHabit(Habit habit) {
        // prevent duplicate name (case-insensitive)
        Habit existing = findHabitByName(habit.getName());
        if (existing != null) {
            System.out.println("Habit with that name already exists: " + habit.getName());
            return;
        }
        this.habits.add(habit);
    }

    // Convenience method to create and add
    public void addHabit(String name, int target, String targetBasis, boolean isAdaptive) {
        if (findHabitByName(name) != null) {
            System.out.println("Habit with that name already exists: " + name);
            return;
        }
        Habit habit = new Habit(name, target, targetBasis, isAdaptive);
        this.habits.add(habit);
        System.out.println("Habit added: " + name);
    }

    // Remove habit by name (case-insensitive)
    public boolean removeHabit(String name) {
        for (int i = 0; i < habits.size(); i++) {
            if (habits.get(i).getName().equalsIgnoreCase(name)) {
                habits.remove(i);
                System.out.println("Habit removed: " + name);
                return true;
            }
        }
        System.out.println("No habit found to remove: " + name);
        return false;
    }

    // Find a habit by name (case-insensitive)
    public Habit findHabitByName(String name) {
        for (Habit h : this.habits) {
            if (h.getName().equalsIgnoreCase(name)) return h;
        }
        return null;
    }

    public ArrayList<Habit> getHabits() {
        return this.habits;
    }

    public String getUserName() {
        return this.userName;
    }

    // Add log to a named habit
    public void addHabitLog(String name, boolean status) {
        Habit habit = findHabitByName(name);
        if (habit == null) {
            System.out.println("No such habit to log for: " + name);
            return;
        }
        habit.addLog(status);
    }

    // Reports
    public String getSingleReport(String name) {
        Habit h = findHabitByName(name);
        if (h == null) return "No such habit.";
        return h.getReport();
    }

    public String getAllReport() {
        if (habits.isEmpty()) return "No habits in tracker.";
        StringBuilder sb = new StringBuilder();
        sb.append("All Habits Report:\n");
        for (Habit h : habits) {
            sb.append(h.getReport()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public String getWeeklyReport(String name) {
        Habit h = findHabitByName(name);
        if (h == null) return "No such habit.";
        return h.getWeeklyReport();
    }
}