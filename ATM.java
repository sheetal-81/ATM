import java.util.*;

class Account {
    private String userID;
    private String pin;
    private double balance;
    private List<String> transactions;

    public Account(String userID, String pin) {
        this.userID = userID;
        this.pin = pin;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public boolean authenticate(String userID, String pin) {
        return this.userID.equals(userID) && this.pin.equals(pin);
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add("Withdrew: " + amount);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void transfer(Account other, double amount) {
        if (balance >= amount) {
            balance -= amount;
            other.deposit(amount);
            transactions.add("Transferred: " + amount + " to " + other.userID);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void printTransactions() {
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class ATM {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Create some sample accounts
        accounts.put("user1", new Account("user1", "1234"));
        accounts.put("user2", new Account("user2", "5678"));

        System.out.print("Enter user ID: ");
        String userID = scanner.next();
        System.out.print("Enter pin: ");
        String pin = scanner.next();

        if (accounts.containsKey(userID) && accounts.get(userID).authenticate(userID, pin)) {
            Account account = accounts.get(userID);
            while (true) {
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Transfer");
                System.out.println("4. Balance");
                System.out.println("5. Transactions");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        account.withdraw(withdrawalAmount);
                        break;
                    case 3:
                        System.out.print("Enter recipient user ID: ");
                        String recipientID = scanner.next();
                        if (accounts.containsKey(recipientID)) {
                            System.out.print("Enter transfer amount: ");
                            double transferAmount = scanner.nextDouble();
                            account.transfer(accounts.get(recipientID), transferAmount);
                        } else {
                            System.out.println("Invalid recipient user ID!");
                        }
                        break;
                    case 4:
                        System.out.println("Current balance: " + account.getBalance());
                        break;
                    case 5:
                        account.printTransactions();
                        break;
                    case 6:
                        System.exit(0);
                }
            }
        } else {
            System.out.println("Invalid user ID or pin!");
        }
    }
}
