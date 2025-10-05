package main;

import model.*;
import logic.AdaptiveGoalEngine;
import storage.DataManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) username = "default_user";

        User user = DataManager.loadUserData(username);
        System.out.println("Welcome, " + user.getUserName() + ".");
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("--- HabitFlow CLI ---");
            System.out.println("1. Create new habit");
            System.out.println("2. Remove habit");
            System.out.println("3. Add log to habit");
            System.out.println("4. View habit report");
            System.out.println("5. View weekly report");
            System.out.println("6. Run adaptive adjustments (for adaptive habits)");
            System.out.println("7. List all habits");
            System.out.println("8. Save and Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter habit name: ");
                    String habitName = scanner.nextLine().trim();
                    System.out.print("Enter target amount (number): ");
                    int target = readInt(scanner, 1);
                    System.out.print("Enter target basis (e.g., mins/reps/pages): ");
                    String basis = scanner.nextLine().trim();
                    System.out.print("Is this habit adaptive? (true/false): ");
                    boolean isAdaptive = Boolean.parseBoolean(scanner.nextLine().trim());
                    user.addHabit(habitName, target, basis, isAdaptive);
                }

                case 2 -> {
                    System.out.print("Enter habit name to remove: ");
                    String habitName = scanner.nextLine().trim();
                    user.removeHabit(habitName);
                }

                case 3 -> {
                    System.out.print("Enter habit name to log: ");
                    String habitName = scanner.nextLine().trim();
                    Habit h = user.findHabitByName(habitName);
                    if (h == null) {
                        System.out.println("No habit found with that name.");
                        break;
                    }
                    System.out.print("Was it completed today? (true/false): ");
                    boolean status = Boolean.parseBoolean(scanner.nextLine().trim());
                    h.addLog(status);
                }

                case 4 -> {
                    System.out.print("Enter habit name: ");
                    String habitName = scanner.nextLine().trim();
                    System.out.println(user.getSingleReport(habitName));
                }

                case 5 -> {
                    System.out.print("Enter habit name: ");
                    String habitName = scanner.nextLine().trim();
                    System.out.println(user.getWeeklyReport(habitName));
                }

                case 6 -> {
                    AdaptiveGoalEngine.analyzeHabits(user.getHabits());
                }

                case 7 -> {
                    System.out.println("Your habits:");
                    for (Habit habit : user.getHabits()) {
                        System.out.println("- " + habit.getName());
                    }
                }

                case 8 -> {
                    System.out.println("Saving data...");
                    DataManager.saveUserData(user);
                    System.out.println("Saved. Exiting.");
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    private static int readInt(Scanner scanner, int defaultValue) {
        try {
            String s = scanner.nextLine().trim();
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
