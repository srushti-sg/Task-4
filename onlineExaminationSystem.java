package com.online_exam_system;

import java.util.*;

public class OnlineExaminationSystem {

    // User class for storing user details
    static class User {
        String username;
        String password;
        boolean isAdmin;

        public User(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }
    }

    // Question class for storing question details
    static class Question {
        String questionText;
        List<String> options;
        int correctOption;

        public Question(String questionText, List<String> options, int correctOption) {
            this.questionText = questionText;
            this.options = options;
            this.correctOption = correctOption;
        }

        public boolean isCorrect(int selectedOption) {
            return selectedOption == correctOption;
        }

        public String getQuestion() {
            return questionText;
        }

        public List<String> getOptions() {
            return options;
        }

        public int getCorrectOption() {
            return correctOption;
        }
    }

    // Admin and User databases
    private static Map<String, User> users = new HashMap<>();
    private static List<Question> questions = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize users and questions
        Scanner scanner = new Scanner(System.in);

        // Predefined admin user
        users.put("admin", new User("admin", "admin123", true));

        // Sample questions (you can replace them with actual questions)
        questions.add(new Question("What is the capital of France?", List.of("Paris", "London", "Berlin", "Rome"), 1));
        questions.add(new Question("Who painted the Mona Lisa?", List.of("Leonardo da Vinci", "Michelangelo", "Raphael", "Donatello"), 1));
        questions.add(new Question("What is the largest planet in our solar system?", List.of("Jupiter", "Saturn", "Earth", "Mars"), 1));
        questions.add(new Question("What is 5 + 3?", List.of("6", "7", "8", "9"), 3)); // New simple question
        questions.add(new Question("What is the color of the sky?", List.of("Red", "Blue", "Green", "Yellow"), 2)); // New simple question

        // Main execution loop
        boolean running = true;
        while (running) {
            System.out.println("1. Login\n2. Register\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            User user = users.get(username);
            System.out.println("Login successful!");

            // After successful login, present the questions and evaluate performance
            userMenu(scanner);

        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        System.out.print("Are you an admin? (yes/no): ");
        String isAdminInput = scanner.nextLine();

        boolean isAdmin = isAdminInput.equalsIgnoreCase("yes");

        // Register the new user
        users.put(username, new User(username, password, isAdmin));
        System.out.println("Registration successful! You can now login.");
    }

    private static void userMenu(Scanner scanner) {
        // Conduct the exam after login
        List<String> userAnswers = new ArrayList<>();

        // Present the questions
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((char)('A' + i) + ") " + options.get(i));
            }

            System.out.print("Enter your answer (A/B/C/D): ");
            String userAnswer = scanner.nextLine().toUpperCase();
            userAnswers.add(userAnswer);
        }

        // Evaluate the exam performance
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers.get(i).equalsIgnoreCase(getAnswerLetter(questions.get(i).getCorrectOption()))) {
                correctAnswers++;
            }
        }

        // Calculate and display results
        double percentage = (double) correctAnswers / questions.size() * 100;
        System.out.println("\nResults:");
        System.out.println("Correct Answers: " + correctAnswers);
        System.out.println("Total Questions: " + questions.size());
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");

        // Display grade based on performance
        if (percentage >= 90) {
            System.out.println("Grade: A+");
        } else if (percentage >= 80) {
            System.out.println("Grade: A");
        } else if (percentage >= 70) {
            System.out.println("Grade: B");
        } else if (percentage >= 60) {
            System.out.println("Grade: C");
        } else {
            System.out.println("Grade: F");
        }
    }

    // Utility function to map correct option number to letter (A/B/C/D)
    private static String getAnswerLetter(int option) {
        switch (option) {
            case 1: return "A";
            case 2: return "B";
            case 3: return "C";
            case 4: return "D";
            default: return "";
        }
    }
}
