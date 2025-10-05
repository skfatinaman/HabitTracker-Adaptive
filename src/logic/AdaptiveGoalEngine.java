package logic;

import model.Habit;
import java.util.ArrayList;

public class AdaptiveGoalEngine {

    public static void analyzeHabits(ArrayList<Habit> habits) {
        for (Habit habit : habits) {
            if (habit.isAdaptive() && habit.getLogs().size() >= 7) {
                adjustTarget(habit);
            }
        }
    }

    private static void adjustTarget(Habit habit) {
        boolean[] weeklyStatus = habit.getWeeklyBool();
        int completed = 0;
        int total = 0;

        for (boolean status : weeklyStatus) {
            total++;
            if (status) completed++;
        }

        double percentage = ((double) completed / total) * 100;
        int oldTarget = habit.getTarget();
        int newTarget = oldTarget;


        if (percentage >= 80) {
            newTarget = (int) Math.round(oldTarget * 1.2);
            System.out.printf("Target increased by 20%% (%d → %d)%n/n", oldTarget, newTarget);
        } else if (percentage < 40) {
            newTarget = Math.max(1, (int) Math.round(oldTarget * 0.8));
            System.out.printf("Target decreased by 20%% (%d → %d)%n/n", oldTarget, newTarget);
        } else {
            System.out.printf("Target remains the same (%d)%n/n", oldTarget);
        }

        habit.setTarget(newTarget);
    }
}