package main;

import model.Habit;
import model.HabitLog;
import model.User;

public class Main {
    static void main(String[] args) {
        User user = new User("Aman");
        user.getAllReport();
        user.addHabit("Drink Water", 8, "glasses");
        user.addHabit("Exercise", 1, "hour");
        System.out.println(user.getSingleReport("Drink Water"));
        System.out.println(user.getAllReport());
        user.addHabitLog("Exercise", true);
        user.addHabitLog("Exercise", true);
        System.out.println(user.getAllReport());
    }
}
