import java.util.ArrayList;
import java.util.Scanner;

public class atmsource {
    private static ArrayList<User> users = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Create some dummy users
        users.add(new User("ashish", "1234", 1000));
        users.add(new User("oasis", "4321", 2000));

        // Start the ATM
        while (true) {
            System.out.println("Welcome to the ATM!");
            System.out.print("Please enter your user id: ");
            String userId = sc.nextLine();
            System.out.print("Please enter your pin: ");
            String pin = sc.nextLine();

            User currentUser = null;
            for (User user : users) {
                if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                    currentUser = user;
                    break;
                }
            }

            if (currentUser == null) {
                System.out.println("Invalid user id or pin. Please try again.");
                continue;
            }

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Check balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. Transaction history");
                System.out.println("6. Quit");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Your current balance is: " + currentUser.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter the amount to deposit: ");
                        double depositAmount = Double.parseDouble(sc.nextLine());
                        currentUser.deposit(depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter the amount to withdraw: ");
                        double withdrawAmount = Double.parseDouble(sc.nextLine());
                        currentUser.withdraw(withdrawAmount);
                        break;
                    case 4:
                        System.out.print("Enter the user id of the recipient: ");
                        String recipientUserId = sc.nextLine();
                        User recipient = null;
                        for (User user : users) {
                            if (user.getUserId().equals(recipientUserId)) {
                                recipient = user;
                                break;
                            }
                        }
                        if (recipient == null) {
                            System.out.println("Invalid recipient user id.");
                            break;
                        }
                        System.out.print("Enter the amount to transfer: ");
                        double transferAmount = Double.parseDouble(sc.nextLine());
                        currentUser.transfer(recipient, transferAmount);
                        break;
                    case 5:
                        currentUser.printTransactionHistory();
                        break;
                    case 6:
                        currentUser = null;
                        break;
                }

                if (currentUser == null) {
                    break;
                }
            }
        }
    }

    public static class User {
        private String userId;
        private String pin;
        private double balance;
        private ArrayList<String> transactionHistory;

        public User(String userId, String pin, double balance) {
            this.userId = userId;
            this.pin = pin;
            this.balance = balance;
            this.transactionHistory = new ArrayList<>();
        }

        public String getUserId() {
            return userId;
        }

        public String getPin() {
            return pin;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Invalid deposit amount.");
                return;
            }
            balance += amount;
            transactionHistory.add("Deposited " + amount);
            System.out.println("Deposit successful.");
        }

        public void withdraw(double amount) {
            if (amount <= 0 || amount > balance) {
                System.out.println("Invalid withdrawal amount.");
                return;
            }
            balance -= amount;
            transactionHistory.add("Withdrew " + amount);
            System.out.println("Withdrawal successful.");
        }

        public void transfer(User recipient, double amount) {
            if (amount <= 0 || amount > balance) {
                System.out.println("Invalid transfer amount.");
                return;
            }
            balance -= amount;
            recipient.balance += amount;
            transactionHistory.add("Transferred " + amount + " to " + recipient.getUserId());
            recipient.transactionHistory.add("Received " + amount + " from " + getUserId());
            System.out.println("Transfer successful.");
        }

        public void printTransactionHistory() {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}
