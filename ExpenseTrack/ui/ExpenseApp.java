package ui;

import model.User;
import service.UserService;
import service.ExpenseService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ExpenseApp {
    private static UserService userService = new UserService();
    private static ExpenseService expenseService = new ExpenseService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }

    static class LoginFrame extends JFrame {
        JTextField usernameField;
        JPasswordField passwordField;

        LoginFrame() {
            setTitle("Login");
            setSize(300, 150);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Username:"));
            usernameField = new JTextField();
            panel.add(usernameField);

            panel.add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            panel.add(passwordField);

            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(e -> login());
            panel.add(loginButton);

            add(panel);
            setVisible(true);
        }

        void login() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = userService.loginUser(username, password);
            if (user != null) {
                dispose(); // Close login window
                new ExpenseFrame(user);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        }
    }

    static class ExpenseFrame extends JFrame {
        JTextField categoryField, amountField, descriptionField;
        User currentUser;

        ExpenseFrame(User user) {
            this.currentUser = user;

            setTitle("Add Expense");
            setSize(400, 250);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(5, 2));

            add(new JLabel("Category:"));
            categoryField = new JTextField();
            add(categoryField);

            add(new JLabel("Amount:"));
            amountField = new JTextField();
            add(amountField);

            add(new JLabel("Description:"));
            descriptionField = new JTextField();
            add(descriptionField);

            JButton addButton = new JButton("Add Expense");
            addButton.addActionListener(e -> addExpense());
            add(addButton);

            setVisible(true);
        }

        void addExpense() {
            try {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();
                LocalDate date = LocalDate.now();

                expenseService.addExpense(currentUser.getUsername(), date, category, amount, description);
                JOptionPane.showMessageDialog(this, "Expense added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        }
    }
}
