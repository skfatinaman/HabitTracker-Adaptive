package storage;

import model.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class DataManager {
    private static final String DATA_FOLDER = "data";

    // Save all habits for a user - creates data/<username>/ files
    public static void saveUserData(User user) {
        if (user == null) return;

        String userDirPath = DATA_FOLDER + File.separator + sanitizeFileName(user.getUserName());
        File userDir = new File(userDirPath);
        if (!userDir.exists() && !userDir.mkdirs()) {
            System.out.println("Failed to create user data directory: " + userDirPath);
            return;
        }

        for (Habit habit : user.getHabits()) {
            String fileName = sanitizeFileName(habit.getName()) + ".txt";
            File habitFile = new File(userDir, fileName);

            try (PrintWriter writer = new PrintWriter(new FileWriter(habitFile))) {
                writer.println("Name: " + habit.getName());
                writer.println("Target: " + habit.getTarget());
                writer.println("Basis: " + habit.getTargetBasis());
                writer.println("Adaptive: " + habit.isAdaptive());
                writer.println("Streak: " + habit.getStreak());
                writer.println("Logs:");
                for (HabitLog log : habit.getLogs()) {
                    writer.println(log.getDate().toString() + " " + log.getStatus());
                }
            } catch (IOException e) {
                System.out.println("Error saving habit '" + habit.getName() + "': " + e.getMessage());
            }
        }
    }

    // Load user data from data/<username>/ folder
    public static User loadUserData(String userName) {
        if (userName == null || userName.trim().isEmpty()) userName = "default_user";
        User user = new User(userName);
        String userDirPath = DATA_FOLDER + File.separator + sanitizeFileName(userName);
        File userDir = new File(userDirPath);
        if (!userDir.exists() || !userDir.isDirectory()) {
            // no data yet
            return user;
        }

        File[] files = userDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null) return user;

        for (File f : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                Map<String, String> header = new HashMap<>();
                List<String> logLines = new ArrayList<>();

                // Read header until "Logs:" line
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    if (line.startsWith("Logs:")) {
                        break;
                    }
                    int colon = line.indexOf(':');
                    if (colon > 0) {
                        String key = line.substring(0, colon).trim();
                        String value = line.substring(colon + 1).trim();
                        header.put(key, value);
                    }
                }

                // Now read logs (remaining lines)
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    logLines.add(line.trim());
                }

                // Parse header
                String name = header.getOrDefault("Name", "unknown");
                int target = Integer.parseInt(header.getOrDefault("Target", "1"));
                String basis = header.getOrDefault("Basis", "");
                boolean adaptive = Boolean.parseBoolean(header.getOrDefault("Adaptive", "false"));
                int streak = Integer.parseInt(header.getOrDefault("Streak", "0"));

                Habit habit = new Habit(name, target, basis, adaptive);

                // Restore logs (preserve order)
                for (String logLine : logLines) {
                    String[] parts = logLine.split("\\s+");
                    // Expected: YYYY-MM-DD true/false
                    if (parts.length >= 2) {
                        try {
                            LocalDate date = LocalDate.parse(parts[0]);
                            boolean status = Boolean.parseBoolean(parts[1]);
                            habit.getLogs().add(new HabitLog(date, status));
                        } catch (Exception ex) {
                            // skip malformed log lines
                        }
                    }
                }

                habit.setStreak(streak);
                // Rescan streak safety (optional)
                habit.scanHabitStreaks();

                user.addHabit(habit);

            } catch (IOException ex) {
                System.out.println("Error reading file " + f.getName() + ": " + ex.getMessage());
            }
        }

        return user;
    }

    // Simple filename sanitizer
    private static String sanitizeFileName(String input) {
        if (input == null) return "unknown";
        return input.trim().replaceAll("[^a-zA-Z0-9-_\\. ]", "_").replace(' ', '_');
    }
}
