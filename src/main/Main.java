package main;

import model.*;
import logic.AdaptiveGoalEngine;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        System.out.println("=======================================");
        System.out.println("           HabitFlow CLI Tracker       ");
        System.out.println("=======================================");

        boolean running = true;

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Habit");
            System.out.println("2. Remove Habit");
            System.out.println("3. Add Habit Log");
            System.out.println("4. View Single Habit Report");
            System.out.println("5. View All Reports");
            System.out.println("6. Run Adaptive Goal Engine");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter habit name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter target amount (number): ");
                    int target = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter target basis (e.g., reps, hours, pages): ");
                    String basis = scanner.nextLine();

                    System.out.print("Is this habit adaptive? (true/false): ");
                    boolean isAdaptive = Boolean.parseBoolean(scanner.nextLine());

                    user.addHabit(name, target, basis, isAdaptive);
                }

                case 2 -> {
                    System.out.print("Enter habit name to remove: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter target of that habit: ");
                    int target = Integer.parseInt(scanner.nextLine());

                    user.removeHabit(name, target);
                }

                case 3 -> {
                    System.out.print("Enter habit name to log: ");
                    String name = scanner.nextLine();

                    System.out.print("Was it completed today? (true/false): ");
                    boolean status = Boolean.parseBoolean(scanner.nextLine());

                    user.addHabitLog(name, status);
                }

                case 4 -> {
                    System.out.print("Enter habit name: ");
                    String name = scanner.nextLine();
                    System.out.println(user.getSingleReport(name));
                }

                case 5 -> {
                    System.out.println(user.getAllReport());
                }

                case 6 -> {
                    System.out.println("Running Adaptive Goal Engine...");
                    AdaptiveGoalEngine.analyzeHabits(user.getHabits());
                }

                case 7 -> {
                    System.out.println("Exiting HabitFlow. Goodbye.");
                    running = false;
                }

                default -> System.out.println("Invalid option. Please choose between 1-7.");
            }
        }

        scanner.close();
    }
}