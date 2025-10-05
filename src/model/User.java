package model;

import java.util.ArrayList;

public class User {
    private String userName;
    private ArrayList<Habit> habits;
    private int daysUsed = 0;
    public User(String userName){
        this.userName = userName;
        this.habits = new ArrayList<>();
    }

    public User(){
        this.userName = "Default User";
        this.habits = new ArrayList<>();
    }

    public void addHabit(String name, int target, String targetBasis, boolean isAdaptive){
        boolean exists = false;
        for(Habit tempHabit : habits){
            if(tempHabit.getName().equals(name) && tempHabit.getTarget()==target){
                System.out.println("Habit already exists\n");
                exists = true;
                break;
            }
        }
        if(!exists){
            Habit newHabit = new Habit(name,target,targetBasis,isAdaptive);
            this.habits.add(newHabit);
            System.out.println("Habit Added Successfully\n");
        }
    }

    public void removeHabit(String name, int target){
        boolean removed = false;
        for(int i = 0; i < this.habits.size(); i++){
            Habit tempHabit = this.habits.get(i);
            if(tempHabit.getName().equals(name) && tempHabit.getTarget()==target){
                this.habits.remove(i);
                removed = true;
                break;
            }
        }
        if(removed){
            System.out.println("Habit removed from tracker\n");
        }
        else{
            System.out.println("No such habit exists\n");
        }
    }

    public String getSingleReport(String name){
        for (Habit tempHabit : this.habits) {
            if (tempHabit.getName().equals(name)) {
                return tempHabit.getReport();
            }
        }
        return "No such habit !";
    }

    public String getAllReport(){
        if(this.habits.isEmpty()){
            return "No Habits in Tracker";
        }
        String allReport = "All Habits Report: \n";
        for(Habit tempHabit : this.habits){
            allReport += tempHabit.getReport() + '\n';
        }
        return allReport;
    }

    public String singleWeeklyReport(String name){
        for (Habit tempHabit : this.habits) {
            if (tempHabit.getName().equals(name)) {
                return tempHabit.getWeeklyReport();
            }
        }
        return "No such habit !";
    }

    public void addHabitLog(String name, boolean status){
        for (Habit tempHabit : this.habits){
            if(tempHabit.getName().equals(name)){
                tempHabit.addLog(status);
                return;
            }
        }
        System.out.println("No such habit to log for\n");
    }

    public ArrayList<Habit> getHabits() {
        return this.habits;
    }

}
