import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ATM {
    static int balance = 5000; // balance is static to maintain the same balance for all instances
    static List<Account> accounts = new ArrayList<>(); // To store account information
    static int currentAccountIndex = -1; // Variable to keep track of the currently inserted card's index
    static JFrame frame = new JFrame("ATM Machine");
    static CardLayout cardLayout = new CardLayout();
    static JPanel mainPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        SwingUtilities.invokeLater(() -> {
            initializeGUI();
            cardLayout.show(mainPanel, "Home");
            frame.setVisible(true);
        });

        // To ensure proper shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
    }

    private static void initializeGUI() {
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Home Panel
        JPanel homePanel = new JPanel(new GridLayout(3, 1));
        JButton insertCardButton = new JButton("Insert Card");
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        // Add color to buttons
        insertCardButton.setBackground(Color.WHITE);
        loginButton.setBackground(Color.WHITE);
        exitButton.setBackground(Color.WHITE);
        insertCardButton.setForeground(Color.BLACK);
        loginButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);

        insertCardButton.addActionListener(e -> cardLayout.show(mainPanel, "InsertCard"));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        exitButton.addActionListener(e -> {
            System.out.println("Thank you for using our ATM. Goodbye!");
            System.exit(0);
        });

        homePanel.add(insertCardButton);
        homePanel.add(loginButton);
        homePanel.add(exitButton);

        // Insert Card Panel
        JPanel insertCardPanel = new JPanel(new GridLayout(3, 1));
        JButton createAccountButton = new JButton("Create Account");
        JButton backButton1 = new JButton("Back");

        createAccountButton.addActionListener(e -> cardLayout.show(mainPanel, "CreateAccount"));
        backButton1.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        insertCardPanel.add(createAccountButton);
        insertCardPanel.add(backButton1);

        // Create Account Panel
        JPanel createAccountPanel = new JPanel(new GridLayout(9, 2));
        JTextField cardNumberField = new JTextField();
        JTextField pinField = new JPasswordField();
        JTextField nameField = new JTextField();
        JTextField bloodGroupField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField nationalityField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField mobileNumberField = new JTextField();
        JButton createButton = new JButton("Create Account");
        JButton backButton2 = new JButton("Back");

        createButton.addActionListener(e -> {
            int cardNumber = Integer.parseInt(cardNumberField.getText());
            int pin = Integer.parseInt(pinField.getText());
            String name = nameField.getText();
            String bloodGroup = bloodGroupField.getText();
            int age = Integer.parseInt(ageField.getText());
            String nationality = nationalityField.getText();
            String address = addressField.getText();
            String mobileNumber = mobileNumberField.getText();

            Account account = new Account(cardNumber, name, bloodGroup, age, nationality, address, mobileNumber, pin);
            accounts.add(account);
            JOptionPane.showMessageDialog(frame, "Account created successfully!");

            cardLayout.show(mainPanel, "Home");
        });

        backButton2.addActionListener(e -> cardLayout.show(mainPanel, "InsertCard"));

        createAccountPanel.add(new JLabel("Card Number:"));
        createAccountPanel.add(cardNumberField);
        createAccountPanel.add(new JLabel("PIN:"));
        createAccountPanel.add(pinField);
        createAccountPanel.add(new JLabel("Name:"));
        createAccountPanel.add(nameField);
        createAccountPanel.add(new JLabel("Blood Group:"));
        createAccountPanel.add(bloodGroupField);
        createAccountPanel.add(new JLabel("Age:"));
        createAccountPanel.add(ageField);
        createAccountPanel.add(new JLabel("Nationality:"));
        createAccountPanel.add(nationalityField);
        createAccountPanel.add(new JLabel("Address:"));
        createAccountPanel.add(addressField);
        createAccountPanel.add(new JLabel("Mobile Number:"));
        createAccountPanel.add(mobileNumberField);
        createAccountPanel.add(createButton);
        createAccountPanel.add(backButton2);

        // Login Panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JTextField loginCardNumberField = new JTextField();
        JTextField loginPinField = new JPasswordField();
        JButton loginSubmitButton = new JButton("Login");
        JButton backButton3 = new JButton("Back");

        loginSubmitButton.addActionListener(e -> {
            int cardNumber = Integer.parseInt(loginCardNumberField.getText());
            int pin = Integer.parseInt(loginPinField.getText());

            for (Account account : accounts) {
                if (account.cardNumber == cardNumber && account.pin == pin) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    currentAccountIndex = accounts.indexOf(account);
                    cardLayout.show(mainPanel, "Operations");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Invalid card number or PIN. Login failed.");
        });

        backButton3.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        loginPanel.add(new JLabel("Card Number:"));
        loginPanel.add(loginCardNumberField);
        loginPanel.add(new JLabel("PIN:"));
        loginPanel.add(loginPinField);
        loginPanel.add(loginSubmitButton);
        loginPanel.add(backButton3);

        // Operations Panel
        JPanel operationsPanel = new JPanel(new GridLayout(8, 1));
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton showReceiptButton = new JButton("Show Receipt");
        JButton printReceiptButton = new JButton("Print Receipt");
        JButton showTransactionHistoryButton = new JButton("Show Transaction History");
        JButton currencyConverterButton = new JButton("Currency Converter");
        JButton logoutButton = new JButton("Logout");
        // Add color to buttons
        withdrawButton.setBackground(Color.WHITE);
        depositButton.setBackground(Color.WHITE);
        checkBalanceButton.setBackground(Color.WHITE);
        showReceiptButton.setBackground(Color.MAGENTA);
        printReceiptButton.setBackground(Color.WHITE);
        showTransactionHistoryButton.setBackground(Color.LIGHT_GRAY);
        currencyConverterButton.setBackground(Color.DARK_GRAY);
        logoutButton.setBackground(Color.GRAY);
        withdrawButton.setForeground(Color.WHITE);
        depositButton.setForeground(Color.WHITE);
        checkBalanceButton.setForeground(Color.WHITE);
        showReceiptButton.setForeground(Color.WHITE);
        printReceiptButton.setForeground(Color.WHITE);
        showTransactionHistoryButton.setForeground(Color.WHITE);
        currencyConverterButton.setForeground(Color.WHITE);
        logoutButton.setForeground(Color.WHITE);

        withdrawButton.addActionListener(e -> performWithdraw());
        depositButton.addActionListener(e -> performDeposit());
        checkBalanceButton.addActionListener(e -> checkBalance());
        showReceiptButton.addActionListener(e -> showReceipt());
        printReceiptButton.addActionListener(e -> printReceipt());
        showTransactionHistoryButton.addActionListener(e -> showTransactionHistory());
        currencyConverterButton.addActionListener(e -> currencyConverter());
        logoutButton.addActionListener(e -> {
            currentAccountIndex = -1;
            cardLayout.show(mainPanel, "Home");
        });

        operationsPanel.add(withdrawButton);
        operationsPanel.add(depositButton);
        operationsPanel.add(checkBalanceButton);
        operationsPanel.add(showReceiptButton);
        operationsPanel.add(printReceiptButton);
        operationsPanel.add(showTransactionHistoryButton);
        operationsPanel.add(currencyConverterButton);
        operationsPanel.add(logoutButton);

        mainPanel.add(homePanel, "Home");
        mainPanel.add(insertCardPanel, "InsertCard");
        mainPanel.add(createAccountPanel, "CreateAccount");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(operationsPanel, "Operations");

        frame.add(mainPanel, BorderLayout.CENTER);
    }

    static void performWithdraw() {
        String pinInput = JOptionPane.showInputDialog("Enter your PIN:");
        int pin = Integer.parseInt(pinInput);
        if (!verifyPIN(pin)) {
            JOptionPane.showMessageDialog(frame, "Invalid PIN. Transaction aborted.");
            return;
        }
        String withdrawInput = JOptionPane.showInputDialog("Enter money to be withdrawn:");
        int withdraw = Integer.parseInt(withdrawInput);
        Runnable withdrawTask = () -> {
            synchronized (ATM.class) {
                if (balance >= withdraw) {
                    balance -= withdraw;
                    JOptionPane.showMessageDialog(frame, "Please collect your money");
                    logTransaction("Withdraw", withdraw);
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient Balance");
                }
            }
        };
        withdrawTask.run();
    }

    static void performDeposit() {
        String pinInput = JOptionPane.showInputDialog("Enter your PIN:");
        int pin = Integer.parseInt(pinInput);
        if (!verifyPIN(pin)) {
            JOptionPane.showMessageDialog(frame, "Invalid PIN. Transaction aborted.");
            return;
        }
        String depositInput = JOptionPane.showInputDialog("Enter money to be deposited:");
        int deposit = Integer.parseInt(depositInput);
        Runnable depositTask = () -> {
            synchronized (ATM.class) {
                balance += deposit;
                JOptionPane.showMessageDialog(frame, "Your Money has been successfully deposited");
                logTransaction("Deposit", deposit);
            }
        };
        depositTask.run();
    }

    static void checkBalance() {
        JOptionPane.showMessageDialog(frame, "Balance: " + balance);
    }

    static void showReceipt() {
        if (currentAccountIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please insert your card to show receipt.");
            return;
        }
        Account account = accounts.get(currentAccountIndex);
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("*** Receipt ***\n");
        receiptContent.append("Date and Time: ").append(new Date()).append("\n");
        receiptContent.append("Name: ").append(account.name).append("\n");
        receiptContent.append("Blood Group: ").append(account.bloodGroup).append("\n");
        receiptContent.append("Age: ").append(account.age).append("\n");
        receiptContent.append("Nationality: ").append(account.nationality).append("\n");
        receiptContent.append("Address: ").append(account.address).append("\n");
        receiptContent.append("Mobile Number: ").append(account.mobileNumber).append("\n");
        receiptContent.append("Balance: ").append(balance).append("\n");
        receiptContent.append("Transaction History:\n");

        List<String> transactionHistory = account.getTransactionHistory();
        for (String transaction : transactionHistory) {
            receiptContent.append(transaction).append("\n");
        }

        JOptionPane.showMessageDialog(frame, receiptContent.toString());
    }

    static void printReceipt() {
        if (currentAccountIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please insert your card to print receipt.");
            return;
        }
        Account account = accounts.get(currentAccountIndex);

        JFrame receiptFrame = new JFrame("ATM Receipt");
        receiptFrame.setSize(300, 400);
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setBackground(Color.WHITE);

        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("*** Receipt ***\n");
        receiptContent.append("Date and Time: ").append(new Date()).append("\n");
        receiptContent.append("Name: ").append(account.name).append("\n");
        receiptContent.append("Blood Group: ").append(account.bloodGroup).append("\n");
        receiptContent.append("Age: ").append(account.age).append("\n");
        receiptContent.append("Nationality: ").append(account.nationality).append("\n");
        receiptContent.append("Address: ").append(account.address).append("\n");
        receiptContent.append("Mobile Number: ").append(account.mobileNumber).append("\n");
        receiptContent.append("Balance: ").append(balance).append("\n");
        receiptContent.append("Transaction History:\n");

        List<String> transactionHistory = account.getTransactionHistory();
        for (String transaction : transactionHistory) {
            receiptContent.append(transaction).append("\n");
        }

        receiptArea.setText(receiptContent.toString());

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        receiptFrame.add(scrollPane);

        receiptFrame.setVisible(true);
    }

    static void showTransactionHistory() {
        if (currentAccountIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please insert your card to view transaction history.");
            return;
        }
        Account account = accounts.get(currentAccountIndex);
        StringBuilder historyContent = new StringBuilder();
        historyContent.append("*** Transaction History ***\n");

        List<String> transactionHistory = account.getTransactionHistory();
        for (String transaction : transactionHistory) {
            historyContent.append(transaction).append("\n");
        }

        JOptionPane.showMessageDialog(frame, historyContent.toString());
    }

    static boolean verifyPIN(int pin) {
        if (currentAccountIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please insert your card to verify PIN.");
            return false;
        }
        Account account = accounts.get(currentAccountIndex);
        return account.pin == pin;
    }

    static void logTransaction(String type, int amount) {
        if (currentAccountIndex != -1) {
            Account account = accounts.get(currentAccountIndex);
            account.addTransaction(type, amount);
        }
    }

    static void currencyConverter() {
        if (currentAccountIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please insert your card to use the currency converter.");
            return;
        }

        String[] options = {"USD", "EUR", "GBP"};
        String choice = (String) JOptionPane.showInputDialog(frame, "Choose the currency to convert to:",
                "Currency Converter", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        double convertedAmount = 0;
        String currencySymbol = "";

        switch (choice) {
            case "USD":
                convertedAmount = convertToUSD(balance);
                currencySymbol = NumberFormat.getCurrencyInstance(Locale.US).getCurrency().getSymbol();
                break;
            case "EUR":
                convertedAmount = convertToEUR(balance);
                currencySymbol = NumberFormat.getCurrencyInstance(Locale.FRANCE).getCurrency().getSymbol();
                break;
            case "GBP":
                convertedAmount = convertToGBP(balance);
                currencySymbol = NumberFormat.getCurrencyInstance(Locale.UK).getCurrency().getSymbol();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Invalid choice. Conversion aborted.");
                return;
        }

        JOptionPane.showMessageDialog(frame, "Converted Balance: " + currencySymbol + String.format("%.2f", convertedAmount));
    }

    static double convertToUSD(int amount) {
        double exchangeRate = 0.012; // Example exchange rate
        return amount * exchangeRate;
    }

    static double convertToEUR(int amount) {
        double exchangeRate = 0.011; // Example exchange rate
        return amount * exchangeRate;
    }

    static double convertToGBP(int amount) {
        double exchangeRate = 0.009; // Example exchange rate
        return amount * exchangeRate;
    }
}

class Account {
    int cardNumber;
    String name;
    String bloodGroup;
    int age;
    String nationality;
    String address;
    String mobileNumber;
    int pin;
    List<String> transactionHistory = new ArrayList<>();

    public Account(int cardNumber, String name, String bloodGroup, int age, String nationality, String address, String mobileNumber, int pin) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.nationality = nationality;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.pin = pin;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(String type, int amount) {
        String transaction = type + " : " + amount;
        transactionHistory.add(transaction);
    }
}


