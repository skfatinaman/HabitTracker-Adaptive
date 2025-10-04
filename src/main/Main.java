package main;

import model.Habit;
import model.HabitLog;

public class Main {
    static void main(String[] args) {
        Habit habit = new Habit("Drinking Water", 8,"glasses/day");
        System.out.println(habit.getReport());
        habit.addLog(true);
        System.out.println(habit.getReport());
        System.out.println(habit.getWeeklyReport());
    }
}
