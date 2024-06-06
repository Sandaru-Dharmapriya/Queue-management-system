import java.io.File;    // Import the File class from the java.io package
import java.io.IOException;   // Import the IOException class from the java.io package
import java.util.*;   // Import all classes from the java.util package
import java.io.*;   // Import all classes from the java.io package

public class FoodiesFaveFoodcenter
{
    public static String[][] queues = new String[3][];  // 2D Array to store the queues
    public static int[] maxCapacity = {2, 3, 5};  // Maximum capacity for each queue



    private static int stock = 50;  // Initial stock of burgers

    public static Scanner userInput = new Scanner((System.in));   //user input method

    public static void main(String[] args) {

        try{
            File file = new File("Text.txt");  // to store data create a file
            file.createNewFile();

        }
        catch (IOException ioe){
            System.out.println();
        }

        queues[0] = new String[maxCapacity[0]];
        queues[1] = new String[maxCapacity[1]];
        queues[2] = new String[maxCapacity[2]];

        String[] queue1 = queues[0];
        String[] queue2 = queues[1];
        String[] queue3 = queues[2];

        Scanner userInput = new Scanner(System.in);
        int choice;

        do {
            displayMenu();   // Display the menu options
            choice = userInput.nextInt();
            userInput.nextLine();

            switch (choice) {
                case 100:
                    viewAllQueues(queue1,queue2,queue3);
                    break;
                case 101:
                    viewAllEmptyQueues(queue1);
                    viewAllEmptyQueues(queue2);
                    viewAllEmptyQueues(queue3);
                    break;
                case 102:
                    addCustomer(queue1,queue2,queue3);
                    break;
                case 103:
                    removeCustomer();
                    break;
                case 104:
                    removeServedCustomer();
                    break;
                case 105:
                    viewCustomersSorted();
                    break;
                case 106:
                    storeProgramData(queue1);
                    storeProgramData(queue2);
                    storeProgramData(queue3);
                    break;
                case 107:
                    loadProgramData();
                    break;
                case 108:
                    viewRemainingStock();
                    break;
                case 109:
                    addBurgersToStock();
                    break;
                case 999:
                    System.exit(999);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 999);
    }

    private static void displayMenu() {
        System.out.println("\t\t*********************");
        System.out.println("\t\t* Food Center Menu *");
        System.out.println("\t\t*********************");
        System.out.println("\n\t100 or VFQ: View all Queues");
        System.out.println("\t101 or VEQ: View all Empty Queues");
        System.out.println("\t102 or ACQ: Add customer to a Queue");
        System.out.println("\t103 or RCQ: Remove a customer from a Queue");                   //menu options
        System.out.println("\t104 or PCQ: Remove a served customer");
        System.out.println("\t105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("\t106 or SPD: Store Program Data into file");
        System.out.println("\t107 or LPD: Load Program Data from file");
        System.out.println("\t108 or STK: View Remaining burgers Stock");
        System.out.println("\t109 or AFS: Add burgers to Stock");
        System.out.println("\t999 or EXT: Exit the Program");
        System.out.println("\n\t\tEnter your choice:  ");
    }

    public static void viewAllQueues(String[] queue1, String[] queue2, String[] queue3) {
        System.out.println("*****************");
        System.out.println("*   Cashiers   *");
        System.out.println("*****************");


        for (int i = 0; i < queue3.length; i++) {

            if(i<2){
                System.out.print(queue1[i] == null ? "X": "O");
            }
            if(i<3){
                System.out.print(queue2[i] == null ? "\t\tX": "\t\tO");
            }
            if(i<5){
                if (i==3||i==4){
                    System.out.print("\t\t");
                }
                System.out.print(queue3[i] == null ? "\t\tX": "\t\tO");
            }
            System.out.println();
        }
    }

    private static void viewAllEmptyQueues(String[] queue) {
        System.out.println(" Queue :");

        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                System.out.println("\t\tSlot " + (i + 1));
            }
        }
    }

    private static void addCustomer(String[] queue1, String[] queue2, String[] queue3) {
        int queueNumber;

        System.out.println("Enter the queue number (1, 2, or 3):");
        try {
            queueNumber = userInput.nextInt(); // Read the queue number input from the user
            userInput.nextLine(); // Move to the next line to clear the input buffer
        } catch (InputMismatchException e) {
            System.out.println("Invalid queue number. Please enter a valid integer."); // Print an error message for an invalid queue number
            return;
        }

        while (queueNumber < 1 || queueNumber > 3) {
            System.out.println("Invalid queue number.");
            System.out.println("Enter the queue number (1, 2, or 3):");
            try {
                queueNumber = userInput.nextInt(); // Read the queue number input from the user
                userInput.nextLine();  // Move to the next line to clear the input buffer
            } catch (InputMismatchException e) {
                System.out.println("Invalid queue number. Please enter a valid integer.");
                return;
            }
        }

        System.out.println("Enter the customer name:");
        String customerName = userInput.nextLine();

        if (queueNumber == 1) {
            add(queue1, customerName);
            System.out.println(customerName + " added to queue 1 successfully!");
        } else if (queueNumber == 2) {
            add(queue2, customerName);
            System.out.println(customerName + " added to queue 2 successfully!");
        } else if (queueNumber == 3) {
            add(queue3, customerName);
            System.out.println(customerName + " added to queue 3 successfully!");
        }

        // Update stock
        stock -= 5;
        if (stock <= 10) {
            System.out.println("Warning: Low stock! Remaining stock: " + stock + " burgers");
        }
    }

    public static void add(String[] queue, String name) {
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                queue[i] = name;   // Add the customer to the first available slot in the queue
                break;
            }
        }
    }

    private static void removeCustomer() {
        Scanner scanner = new Scanner(System.in);
        int queueNumber;

        System.out.println("Enter the queue number (1, 2, or 3):");
        try {
            queueNumber = Integer.parseInt(scanner.nextLine()); // Read the queue number input from the user
        } catch (NumberFormatException e) {
            System.out.println("Invalid queue number. Please enter a valid integer.");
            return;
        }

        if (queueNumber < 1 || queueNumber > 3) {
            System.out.println("Invalid queue number.");
            return;
        }

        String[] queue = queues[queueNumber - 1]; // Get the selected queue

        if (queue.length == 0) {
            System.out.println("Queue is already empty.");
            return;
        }

        System.out.println("Enter the customer index to remove (0 to " + (queue.length - 1) + "):");
        int customerIndex;
        try {
            customerIndex = Integer.parseInt(scanner.nextLine()); // Read the customer index input from the user
        } catch (NumberFormatException e) {
            System.out.println("Invalid customer index. Please enter a valid integer.");
            return;
        }

        if (customerIndex < 0 || customerIndex >= queue.length) {
            System.out.println("Invalid customer index.");
            return;
        }

        for (int i = customerIndex; i < queue.length - 1; i++) {  // Shift the customers to the left to remove the selected customer
            queue[i] = queue[i + 1];
        }

        queue[queue.length - 1] = null;   // Set the last element to null to indicate an empty slot

        System.out.println("Customer removed from Queue " + queueNumber);
    }
    private static void removeServedCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the queue number (1, 2, or 3):");
        int queueNumber;
        try {
            queueNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid queue number. Please enter a valid integer.");
            return;
        }

        if (queueNumber < 1 || queueNumber > 3) {
            System.out.println("Invalid queue number.");
            return;
        }

        String[] queue = queues[queueNumber - 1];

        if (queue.length > 0) {
            System.out.println("Enter the position of the served customer (0 to " + (queue.length - 1) + "):");
            int position;
            try {
                position = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid position. Please enter a valid integer.");
                return;
            }

            if (position < 0 || position >= queue.length) {
                System.out.println("Invalid position.");
                return;
            }

            String servedCustomer = queue[position]; // Get the customer at the specified position

            // Shift the customers to the left to remove the served customer
            for (int i = position; i < queue.length - 1; i++) {
                queue[i] = queue[i + 1];
            }

            // Set the last element to null to indicate an empty slot
            queue[queue.length - 1] = null;

            System.out.println("Customer " + servedCustomer + " served from Queue " + queueNumber);
        } else {
            System.out.println("No customers to serve in Queue " + queueNumber);
        }
    }
    private static void viewCustomersSorted() {
        int totalCustomers = 0;

        for (String[] queue : queues) {
            for (String customer : queue) {
                if (customer != null) {
                    totalCustomers++; // Count the number of non-null customers
                }
            }
        }

        String[] allCustomers = new String[totalCustomers];
        int index = 0;

        for (String[] queue : queues) {
            for (String customer : queue) {
                if (customer != null) {
                    allCustomers[index++] = customer; // Add non-null customers to the array
                }
            }
        }

        // Sort the customer array using a simple bubble sort algorithm
        int n = allCustomers.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (allCustomers[j].compareTo(allCustomers[j + 1]) > 0) {
                    // Swap customers if they are out of order
                    String temp = allCustomers[j];
                    allCustomers[j] = allCustomers[j + 1];
                    allCustomers[j + 1] = temp;
                }
            }
        }

        System.out.println("Customers Sorted in alphabetical order:");

        for (String customer : allCustomers) {
            System.out.println(customer); // Print the sorted customers
        }
    }

    private static void storeProgramData(String[] queue) {
        try {
            FileWriter write = new FileWriter("Text.txt", true);   // Create a FileWriter object to write data to the file
            for (int i = 0; i < queue.length; i++) {
                if (queue[i] != null) {
                    write.append(queue[i]);  // Append the customer data to the file
                    write.append(System.lineSeparator());   // Add a new line after each customer
                }
            }
            write.close();   // Close the FileWriter object to release resources
        } catch (IOException ex) {      // Exception handling code can be added here to handle any IO errors that may occur

        }
    }

    private static void loadProgramData() {
        try {
            File readFile = new File("Text.txt");   // Create a File object to read data from the file
            Scanner reader = new Scanner(readFile);   // Create a Scanner object to read the file
            while (reader.hasNextLine()) {   // Loop through each line in the file
                String text = reader.nextLine();   // Read the current line of text from the file
                System.out.println(text);  // Print the text to the console
            }
            reader.close();  // Close the Scanner object to release resources
        } catch (IOException e) {
            System.out.println("Error File Reading");  // Handle any IO errors that may occur
        }
    }



    private static void viewRemainingStock() {
        System.out.println("Remaining burgers in stock: " + stock); // Print the remaining stock of burgers
    }

    private static void addBurgersToStock() {
        Scanner scanner = new Scanner(System.in);  //getting inputs
        int quantity;

        System.out.println("Enter the quantity of burgers to add:");
        quantity = scanner.nextInt();
        scanner.nextLine();

        stock += quantity;
        System.out.println(quantity + " burgers added to stock. Total stock: " + stock);
    }
}
