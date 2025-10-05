package model;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.time.LocalDate;
public class Habit {
    private String name;
    private int target;
    private String targetBasis;
    private int streak;
    private boolean isAdaptive = false;
    private ArrayList<HabitLog> logs;
    public Habit(String name, int target, String basis, boolean isAdaptive){
        this.name = name;
        this.target = target;
        this.targetBasis = basis;
        this.streak = 0;
        this.logs = new ArrayList<>();
        this.isAdaptive = isAdaptive;
    }
    public void addLog(boolean status){
        HabitLog newLog = new HabitLog(LocalDate.now(),status);
        LocalDate currentDate = newLog.getDate();
        if(!this.logs.isEmpty()){
            LocalDate previousDate = this.logs.getLast().getDate();
            if(ChronoUnit.DAYS.between(previousDate,currentDate)!=0){
                this.logs.add(newLog);
                if(newLog.getStatus() && ChronoUnit.DAYS.between(previousDate,currentDate)==1){
                    increaseStreak();
                    System.out.printf("Log Added Successfully, Streak increased to : %d\n\n",this.streak);
                }
                else{
                    resetStreak();
                    System.out.printf("Log Added Successfully, Streak reset to : %d\n\n",this.streak);
                }

            }
            else{
                System.out.println("Cannot add two logs in the same day !\n");
            }
        }
        else{
            this.logs.add(newLog);
            if(newLog.getStatus()){
                increaseStreak();
                System.out.printf("Log Added Successfully, Streak increased to : %d\n\n",this.streak);
            }
            else{
                resetStreak();
                System.out.printf("Log Added Successfully, Streak reset to : %d\n\n",this.streak);
            }
        }
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
        if(!(totalLogs>0)){
            return 0;
        }
        else {
            for (HabitLog log : logs) {
                if (log.getStatus()) {
                    completedLogs++;
                }
            }
            return ((double) completedLogs / totalLogs) * 100;
        }
    }

    public String getReport(){
        String report = String.format("Report for habit : %s\n", this.name);
        report += String.format("Target : %d %s\n", this.target, this.targetBasis);
        report += String.format("Current Streak : %d days\n", this.streak);
        report += String.format("Success Rate : %.2f\n", this.successRate());
        return report;
    }

    public ArrayList<HabitLog> getLogs(){
        return this.logs;
    }

    public String getWeeklyReport(){
        String weeklyReport = String.format("Weekly report for %s:\n", this.name);
        int count = 0;
        for(HabitLog logs : this.logs){
            weeklyReport += logs.toString()+'\n';
            count ++;
            if(count==7){
                break;
            }
        }
        return weeklyReport.trim();
    }

    public String getName(){
        return this.name;
    }

    public int getTarget() {
        return this.target;
    }

    public void scanHabitStreaks(){
        LocalDate last = this.logs.getLast().getDate();
        LocalDate currentDate = LocalDate.now();
        if(!(last.isEqual(currentDate))){
            this.resetStreak();
        }
    }

    public boolean[] getWeeklyBool(){
        boolean[] array = new boolean[7];
        int counter = 0;
        for(HabitLog habit : this.logs){
            array[counter] = habit.getStatus();
        }
        return array;
    }

    public boolean isAdaptive() {
        return this.isAdaptive;
    }

    public void setTarget(int newTarget) {
        this.target = newTarget;
    }
}
