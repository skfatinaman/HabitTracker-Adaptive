package model;
import java.util.ArrayList;
import java.time.LocalDate;
public class Habit {
    private String name;
    private int target;
    private String targetBasis;
    private int streak;
    private ArrayList<HabitLog> logs;
    public Habit(String name, int target, String basis){
        this.name = name;
        this.target = target;
        this.targetBasis = basis;
        this.streak = 0;
    }
    public void addLog(boolean status){
        HabitLog newLog = new HabitLog(LocalDate.now(),status);
        this.logs.add(newLog);
    }
    public void increaseStreak(){
        this.streak++;
    }

    public void resetStreak(){
        this.streak = 0;
    }

    public double successRate(){
        int totalLogs = this.logs.size();
        int completedLogs = 0;
        for(HabitLog log : logs){
            if (log.getStatus()){
                completedLogs++;
            }
        }
        return ((double) completedLogs /totalLogs)*100;
    }
    public String getReport(){
        String report = "";
        report = String.format("Habit : %s\n", this.name);
        report += String.format("Target : %d %s\n", this.target, this.targetBasis);
        report += String.format("Current Streak : %d days\n", this.streak);
        report += String.format("Success Rate : %.2f\n", this.successRate());
        return report;
    }
}
