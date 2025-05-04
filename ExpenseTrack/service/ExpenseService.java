package service;

import model.Expense;
import java.time.LocalDate;
import java.util.*;

public class ExpenseService {
    private static final String EXPENSE_FILE = "data/expenses.txt";
    private List<Expense> expenses;

    public ExpenseService() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    private void loadExpenses() {
        List<String> lines = FileService.loadFromFile(EXPENSE_FILE);
        for (String line : lines) {
            expenses.add(Expense.fromString(line));
        }
    }

    private void saveExpenses() {
        List<String> data = new ArrayList<>();
        for (Expense expense : expenses) {
            data.add(expense.toString());
        }
        FileService.saveToFile(EXPENSE_FILE, data);
    }

    public void addExpense(String username, LocalDate date, String category, double amount, String description) {
        Expense expense = new Expense(username, date.toString(), category, amount, description);
        expenses.add(expense);
        saveExpenses();
        System.out.println("Expense added successfully.");
    }
    

    public void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("\n--- All Expenses ---");
        for (Expense e : expenses) {
            System.out.println(e.getDate() + " | " + e.getCategory() + " | $" + e.getAmount());
        }
    }

    public void summarizeByCategory() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to summarize.");
            return;
        }

        Map<String, Double> summary = new HashMap<>();
        for (Expense e : expenses) {
            summary.put(e.getCategory(),
                        summary.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
        }

        System.out.println("\n--- Category-wise Summary ---");
        for (String category : summary.keySet()) {
            System.out.println(category + ": $" + summary.get(category));
        }
    }
}
