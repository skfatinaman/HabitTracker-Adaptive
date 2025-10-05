package model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Habit {
    private String name;
    private int target;
    private String targetBasis;
    private int streak;
    private boolean isAdaptive = false;
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
        HabitLog newLog = new HabitLog(LocalDate.now(), status);
        if (!this.logs.isEmpty()) {
            LocalDate previousDate = this.logs.getLast().getDate();
            long diff = ChronoUnit.DAYS.between(previousDate, newLog.getDate());
            if (diff >= 1) {
                this.logs.add(newLog);
                if (newLog.getStatus()) {
                    increaseStreak();
                    System.out.printf("Log Added Successfully, Streak increased to : %d\n\n", this.streak);
                } else {
                    resetStreak();
                    System.out.printf("Log Added Successfully, Streak reset to : %d\n\n", this.streak);
                }
            } else {
                System.out.println("Cannot add two logs in the same day!\n");
            }
        } else {
            this.logs.add(newLog);
            if (newLog.getStatus()) {
                increaseStreak();
                System.out.printf("Log Added Successfully, Streak increased to : %d\n\n", this.streak);
            } else {
                resetStreak();
                System.out.printf("Log Added Successfully, Streak reset to : %d\n\n", this.streak);
            }
        }
    }

    public void increaseStreak() {
        this.streak++;
    }

    public void resetStreak() {
        this.streak = 0;
    }

    public double successRate() {
        int totalLogs = this.logs.size();
        if (totalLogs == 0) return 0;
        int completedLogs = 0;
        for (HabitLog log : logs) {
            if (log.getStatus()) completedLogs++;
        }
        return ((double) completedLogs / totalLogs) * 100;
    }

    public String getReport() {
        String report = String.format("Report for habit: %s\n", this.name);
        report += String.format("Target: %d %s\n", this.target, this.targetBasis);
        report += String.format("Current Streak: %d days\n", this.streak);
        report += String.format("Success Rate: %.2f%%\n", this.successRate());
        return report;
    }

    public ArrayList<HabitLog> getLogs() {
        return this.logs;
    }

    public String getWeeklyReport() {
        StringBuilder weeklyReport = new StringBuilder(String.format("Weekly report for %s:\n", this.name));
        int count = 0;
        for (HabitLog log : this.logs) {
            weeklyReport.append(log.toString()).append('\n');
            count++;
            if (count == 7) break;
        }
        return weeklyReport.toString().trim();
    }

    public String getName() {
        return this.name;
    }

    public int getTarget() {
        return this.target;
    }

    public String getTargetBasis() {
        return this.targetBasis;
    }

    public int getStreak() {
        return this.streak;
    }

    public boolean isAdaptive() {
        return this.isAdaptive;
    }
}
