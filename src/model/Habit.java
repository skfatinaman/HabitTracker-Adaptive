package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

public class Habit {
    private String name;
    private int target;
    private String targetBasis;
    private int streak;
    private boolean isAdaptive;
    private ArrayList<HabitLog> logs;

    public Habit(String name, int target, String basis, boolean isAdaptive) {
        this.name = name;
        this.target = target;
        this.targetBasis = basis;
        this.streak = 0;
        this.logs = new ArrayList<>();
        this.isAdaptive = isAdaptive;
    }

    public void addLog(boolean status) {
        LocalDate today = LocalDate.now();
        HabitLog newLog = new HabitLog(today, status);

        if (!this.logs.isEmpty()) {
            LocalDate previousDate = this.logs.get(this.logs.size() - 1).getDate();
            long diff = ChronoUnit.DAYS.between(previousDate, today);

            if (diff == 0) {
                System.out.println("Cannot add two logs for the same day.");
                return;
            }

            this.logs.add(newLog);

            if (diff == 1) {
                // consecutive day
                if (status) {
                    increaseStreak();
                    System.out.printf("Log added. Streak increased to: %d%n%n", this.streak);
                } else {
                    resetStreak();
                    System.out.printf("Log added. Streak reset to: %d%n%n", this.streak);
                }
            } else { // diff > 1 (missed day(s))
                if (status) {
                    // missed days, starting a new streak of length 1
                    this.streak = 1;
                    System.out.printf("Log added after missing days. Streak set to: %d%n%n", this.streak);
                } else {
                    resetStreak();
                    System.out.printf("Log added after missing days. Streak reset to: %d%n%n", this.streak);
                }
            }
        } else {
            // first log ever
            this.logs.add(newLog);
            if (status) {
                this.streak = 1;
                System.out.printf("Log added. Streak increased to: %d%n%n", this.streak);
            } else {
                this.streak = 0;
                System.out.printf("Log added. Streak is: %d%n%n", this.streak);
            }
        }
    }

    // Increase streak by 1
    public void increaseStreak() {
        this.streak++;
    }

    // Reset streak to 0
    public void resetStreak() {
        this.streak = 0;
    }

    // Calculate success rate (%) across all logs
    public double successRate() {
        int totalLogs = this.logs.size();
        if (totalLogs == 0) return 0.0;
        int completed = 0;
        for (HabitLog log : logs) if (log.getStatus()) completed++;
        return ((double) completed / totalLogs) * 100.0;
    }

    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Report for habit: %s%n", this.name));
        sb.append(String.format("Target: %d %s%n", this.target, this.targetBasis));
        sb.append(String.format("Current Streak: %d days%n", this.streak));
        sb.append(String.format("Success Rate: %.2f%%%n", this.successRate()));
        return sb.toString();
    }

    // Return internal logs reference for storage/restore (we use it cautiously)
    public ArrayList<HabitLog> getLogs() {
        return this.logs;
    }

    // Weekly report: most recent up to 7 logs (most recent first)
    public String getWeeklyReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Weekly report for %s:%n", this.name));
        int printed = 0;
        for (int i = this.logs.size() - 1; i >= 0 && printed < 7; i--, printed++) {
            sb.append(this.logs.get(i).toString()).append(System.lineSeparator());
        }
        if (printed == 0) sb.append("No logs for this habit yet.").append(System.lineSeparator());
        return sb.toString().trim();
    }

    // Return a boolean array of size 7 representing last up to 7 logs (false for missing)
    // array[6] = most recent, array[0] = oldest in the 7-day window
    public boolean[] getWeeklyBool() {
        boolean[] arr = new boolean[7];
        Arrays.fill(arr, false);
        int idx = 6;
        for (int i = this.logs.size() - 1; i >= 0 && idx >= 0; i--, idx--) {
            arr[idx] = this.logs.get(i).getStatus();
        }
        return arr;
    }

    // Adjust streak at startup if last log was more than one day ago
    public void scanHabitStreaks() {
        if (this.logs.isEmpty()) {
            this.streak = 0;
            return;
        }
        LocalDate last = this.logs.get(this.logs.size() - 1).getDate();
        long diff = ChronoUnit.DAYS.between(last, LocalDate.now());
        if (diff > 1) {
            this.resetStreak();
        }
        // if diff == 1 or diff == 0 we keep the streak as-is
    }

    // ----- Getters / Setters needed for persistence & engine -----
    public String getName() {
        return this.name;
    }

    public int getTarget() {
        return this.target;
    }

    public void setTarget(int target) {
        this.target = Math.max(1, target);
    }

    public String getTargetBasis() {
        return this.targetBasis;
    }

    public int getStreak() {
        return this.streak;
    }

    public void setStreak(int streak) {
        this.streak = Math.max(0, streak);
    }

    public boolean isAdaptive() {
        return this.isAdaptive;
    }
}
