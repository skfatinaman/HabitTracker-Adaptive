package logic;

import model.Habit;
import java.util.List;

public class AdaptiveGoalEngine {

    // Analyze a list of habits and adjust only adaptive ones with >= 7 logs
    public static void analyzeHabits(List<Habit> habits) {
        if (habits == null) return;
        for (Habit habit : habits) {
            if (habit == null) continue;
            if (!habit.isAdaptive()) continue;
            if (habit.getLogs().size() < 7) continue;
            adjustGoal(habit);
        }
    }

    // Adjust a single habit's target based on last 7 logs
    private static void adjustGoal(Habit habit) {
        boolean[] weekly = habit.getWeeklyBool();
        int total = weekly.length; // always 7
        int completed = 0;
        for (boolean b : weekly) if (b) completed++;

        double pct = total == 0 ? 0.0 : ((double) completed / total) * 100.0;
        int oldTarget = habit.getTarget();
        int newTarget = oldTarget;

        if (pct >= 80.0) {
            newTarget = (int) Math.round(oldTarget * 1.2);
            System.out.printf("Increase: %s performed well (%.1f%%). Target %d -> %d%n",
                    habit.getName(), pct, oldTarget, newTarget);
        } else if (pct < 40.0) {
            newTarget = Math.max(1, (int) Math.round(oldTarget * 0.8));
            System.out.printf("Decrease: %s performed poorly (%.1f%%). Target %d -> %d%n",
                    habit.getName(), pct, oldTarget, newTarget);
        } else {
            System.out.printf("No change: %s weekly performance %.1f%%. Target remains %d%n",
                    habit.getName(), pct, oldTarget);
        }

        habit.setTarget(newTarget);
    }
}