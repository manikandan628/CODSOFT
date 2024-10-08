import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountType;  // Savings or Checking
    private double balance;
    private String accountHolderName;
    private int pin;
    private ArrayList<String> transactionHistory = new ArrayList<>();
    private double dailyWithdrawLimit = 500.0;
    private double dailyTransferLimit = 1000.0;
    private double withdrawnToday = 0.0;
    private double transferredToday = 0.0;
    private boolean isLocked = false;
    private int pinAttempts = 0;

    public BankAccount(String accountHolderName, double balance, int pin, String accountType) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.pin = pin;
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }

    // Validate PIN
    public boolean validatePin(int inputPin) {
        if (isLocked) {
            System.out.println("Account is locked due to too many incorrect attempts.");
            return false;
        }
        if (inputPin == pin) {
            pinAttempts = 0; // Reset attempts on successful login
            return true;
        } else {
            pinAttempts++;
            if (pinAttempts >= 3) {
                isLocked = true;
                System.out.println("Too many incorrect PIN attempts. Account is now locked.");
            }
            return false;
        }
    }

    public void unlockAccount() {
        isLocked = false;
        pinAttempts = 0;
        System.out.println("Account unlocked.");
    }

    // Increase balance (for deposit)
    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount + " | New Balance: " + balance);
    }

    // Decrease balance (for withdraw)
    public void withdraw(double amount) {
        if (withdrawnToday + amount > dailyWithdrawLimit) {
            System.out.println("Daily withdrawal limit exceeded. You can only withdraw up to " + (dailyWithdrawLimit - withdrawnToday) + " more today.");
            return;
        }
        balance -= amount;
        withdrawnToday += amount;
        transactionHistory.add("Withdrew: " + amount + " | New Balance: " + balance);
    }

    // Transfer money to another account
    public void transfer(BankAccount receiverAccount, double amount) {
        if (transferredToday + amount > dailyTransferLimit) {
            System.out.println("Daily transfer limit exceeded. You can only transfer up to " + (dailyTransferLimit - transferredToday) + " more today.");
            return;
        }
        balance -= amount;
        receiverAccount.deposit(amount);
        transferredToday += amount;
        transactionHistory.add("Transferred: " + amount + " to " + receiverAccount.getAccountHolderName() + " | New Balance: " + balance);
    }

    // Calculate interest for savings accounts
    public void calculateInterest() {
        if (accountType.equals("Savings")) {
            double interestRate = 0.03; // Example: 3% annual interest rate
            double interest = balance * interestRate / 12;  // Monthly interest calculation
            balance += interest;
            transactionHistory.add("Interest added: " + interest + " | New Balance: " + balance);
        }
    }

    // View mini-statement (last 5 transactions)
    public void printMiniStatement() {
        System.out.println("Mini Statement:");
        int count = 0;
        for (int i = transactionHistory.size() - 1; i >= 0 && count < 5; i--, count++) {
            System.out.println(transactionHistory.get(i));
        }
    }

    // Change PIN
    public void changePin(int newPin) {
        pin = newPin;
        System.out.println("PIN changed successfully.");
    }

    public void resetDailyLimits() {
        withdrawnToday = 0.0;
        transferredToday = 0.0;
    }

    public void viewProfile() {
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Type: " + accountType);
        System.out.println("Current Balance: " + balance);
    }
}

// ATM Machine class
class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void withdraw(double amount) {
        if (amount > 0 && account.getBalance() >= amount) {
            account.withdraw(amount);
            System.out.println("Withdrawal successful! Amount withdrawn: " + amount);
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            account.deposit(amount);
            System.out.println("Deposit successful! Amount deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void checkBalance() {
        System.out.println("Your current balance is: " + account.getBalance());
    }

    public void transfer(BankAccount receiverAccount, double amount) {
        if (amount > 0 && account.getBalance() >= amount) {
            account.transfer(receiverAccount, amount);
            System.out.println("Transfer successful! Amount transferred: " + amount);
        } else {
            System.out.println("Transfer failed. Insufficient balance or invalid amount.");
        }
    }

    public void printMiniStatement() {
        account.printMiniStatement();
    }

    public void calculateInterest() {
        account.calculateInterest();
    }

    public void changePin(int newPin) {
        account.changePin(newPin);
    }

    public void viewProfile() {
        account.viewProfile();
    }

    public void unlockAccount() {
        account.unlockAccount();
    }

    public void resetDailyLimits() {
        account.resetDailyLimits();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Mini-Statement");
            System.out.println("6. Calculate Interest");
            System.out.println("7. Change PIN");
            System.out.println("8. View Profile");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    withdraw(withdrawAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    deposit(depositAmount);
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter receiver account holder's name: ");
                    String receiverName = scanner.nextLine();
                    BankAccount receiverAccount = new BankAccount(receiverName, 0, 1234, "Savings"); // Dummy account
                    transfer(receiverAccount, transferAmount);
                    break;
                case 5:
                    printMiniStatement();
                    break;
                case 6:
                    calculateInterest();
                    break;
                case 7:
                    System.out.print("Enter new PIN: ");
                    int newPin = scanner.nextInt();
                    changePin(newPin);
                    break;
                case 8:
                    viewProfile();
                    break;
                case 9:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 9);

        scanner.close();
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create two accounts for demonstration
        BankAccount account1 = new BankAccount("MANI", 1000.0, 1234, "Savings");
        BankAccount account2 = new BankAccount("KAILASH", 1500.0, 5678, "Checking");

        // Create the ATM and associate it with account1
        ATM atm = new ATM(account1);

        // PIN validation
        System.out.print("Enter your PIN: ");
        int enteredPin = scanner.nextInt();
        if (!account1.validatePin(enteredPin)) {
            System.out.println("Incorrect PIN. Exiting...");
            return;
        }

        // Display the ATM menu
        atm.showMenu();

        // Daily reset of withdrawal and transfer limits at midnight (dummy implementation)
        atm.resetDailyLimits();
    }
}
