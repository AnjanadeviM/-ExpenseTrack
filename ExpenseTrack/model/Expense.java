package model;

public class Expense {
    private String username;
    private double amount;
    private String category;
    private String date;
    private String description;

    public Expense(String username, String date, String category, double amount, String description) {
        this.username = username;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }
    
    public String getUsername() {
        return username;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String toDataString() {
        return username + "," + amount + "," + category + "," + date + "," + description;
    }

    public static Expense fromString(String data) {
        String[] parts = data.split(",");
        String username = parts[0];
        String date = parts[1];
        String category = parts[2];
        double amount = Double.parseDouble(parts[3]);
        String description = parts[4];
    
        return new Expense(username, date, category, amount, description);
    }
    
}
