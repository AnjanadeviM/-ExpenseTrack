package ui;

import model.User;
import service.ExpenseService;
import service.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final ExpenseService expenseService = new ExpenseService();
    private static User currentUser;  // ✅ Define currentUser here

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Expense Tracker ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        boolean success = userService.registerUser(username, password);
        if (success) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User user = userService.loginUser(username, password);
        if (user != null) {
            currentUser = user;  // ✅ Set currentUser after login
            System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
            showExpenseMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void showExpenseMenu() {
        while (true) {
            System.out.println("\n--- Expense Menu ---");
            System.out.println("1. Add Expense");
            System.out.println("2. List Expenses");
            System.out.println("3. Summary by Category");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addExpense();
                    break;
                case "2":
                    expenseService.listExpenses();
                    break;
                case "3":
                    expenseService.summarizeByCategory();
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addExpense() {
        try {
            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter category: ");
            String category = scanner.nextLine();

            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            // ✅ Use currentUser
            expenseService.addExpense(currentUser.getUsername(), date, category, amount, description);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }
}
