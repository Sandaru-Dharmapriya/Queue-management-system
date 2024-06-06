package com.example.task_04;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.File;              // Import the File class for file handling
import java.io.FileWriter;       // Import the FileWriter class for writing to a file
import java.io.IOException;      // Import the IOException class for handling I/O exceptions
import java.util.ArrayList;       // Import the ArrayList class for storing data dynamically
import java.util.Collections;    // Import the Collections class for sorting
import java.util.Scanner;         // Import the Scanner class for user input
import com.example.task_04.HelloApplication;

public class Main {
    private static final Scanner userInput = new Scanner(System.in);    // Create a Scanner object for user input
    public static int[] maxQueueLimit = {2, 3, 5};           // Maximum capacity of each queue

    // Create three instances of FoodQueue with the specified capacities
    public static FoodQueue queue1 = new FoodQueue(maxQueueLimit[0]);
    public static FoodQueue queue2 = new FoodQueue(maxQueueLimit[1]);
    public static FoodQueue queue3 = new FoodQueue(maxQueueLimit[2]);

    public static FoodQueue[] queues = {queue1, queue2, queue3};     // Array to store the queues

    public static int[] income = {0, 0, 0};      // Array to store the income of each queue

    public static int burgersInStock = 50;           // Initial stock of burgers
    public static final int warningLimit = 10;         // Warning limit for low stock
    public static ArrayList<Customer> waitingList = new ArrayList<>();  // List to store customers in the waiting list


    private static volatile boolean javaFXLaunched = false;

    public static void userInterface(Class<? extends Application> applicationClass) {
        if (!javaFXLaunched) {
            Platform.setImplicitExit(false);
            new Thread(() -> Application.launch(applicationClass)).start();
            javaFXLaunched = true;
        } else {
            Platform.runLater(() -> {
                try {
                    Application application = applicationClass.newInstance();
                    Stage primaryStage = new Stage();
                    application.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) {

        try {
            File file = new File("Text.txt");        // Create a file object with the file name "Text.txt"
            file.createNewFile();        // Create a new file if it does not exist
        } catch (IOException ioe) {
            System.out.println();
        }

        String choice;     // Variable to store the user's menu choice

        do {
            displayMenu();
            choice = userInput.nextLine();

            switch (choice) {
                case "100", "VFQ":
                    viewAllQueues();
                    break;

                case "101", "VEQ":
                    viewAllEmptyQueues();
                    break;

                case "102", "ACQ":
                    addCustomer();
                    break;

                case "103", "RCQ":
                    removeCustomer();
                    break;

                case "104", "PCQ":
                    removeServedCustomer();
                    break;

                case "105", "VCS":
                    viewCustomersSorted();
                    break;

                case "106", "SPD":
                    storeProgramData();
                    break;

                case "107", "LPD":
                    loadProgramData();
                    break;

                case "108", "STK":
                    viewRemainingStock();
                    break;

                case "109", "AFS":
                    addBurgersToStock();
                    break;

                case "110", "INC":
                    incomeOfEachQueue();
                    break;

                case "112" , "GUI":
                    userInterface(HelloApplication.class);
                    System.out.println("\tG U I loaded ...... ");


                case "999", "EXT":
                    System.exit(0);    // Terminate the program

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != "999" || choice != "EXT");
    }


    private static void displayMenu() {
        System.out.println("\n\t\t*********************");
        System.out.println("\t\t* Food Center Menu *");
        System.out.println("\t\t*********************");
        System.out.println("\n\t100 or VFQ: View all Queues");
        System.out.println("\t101 or VEQ: View all Empty Queues");
        System.out.println("\t102 or ACQ: Add customer to a Queue");
        System.out.println("\t103 or RCQ: Remove a customer from a Queue");
        System.out.println("\t104 or PCQ: Remove a served customer");                             // Display the menu options
        System.out.println("\t105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("\t106 or SPD: Store Program Data into file");
        System.out.println("\t107 or LPD: Load Program Data from file");
        System.out.println("\t108 or STK: View Remaining burgers Stock");
        System.out.println("\t109 or AFS: Add burgers to Stock");
        System.out.println("\t110 or INC: Get income of each queue separately");
        System.out.println("\t112 or GUI: View Grafical User Interface");
        System.out.println("\t999 or EXT: Exit the Program");
        System.out.print("\n\t\tEnter your choice:  ");
    }

    // View all the queues
    private static void viewAllQueues() {
        System.out.println("\n***  Cashiers  ***\n");
        System.out.println("1       2       3");
        System.out.println("__      __      __");
        int maxCapacity = Math.max(queue1.getCapacity(), Math.max(queue2.getCapacity(), queue3.getCapacity()));

        for (int i = 0; i < maxCapacity; i++) {
            if (i < queue1.getCapacity()) {
                System.out.print(queue1.getCustomers()[i] != null ? "O" : "X");  // Print 'O' if a customer exists, 'X' otherwise
            }
            System.out.print("\t\t");
            if (i < queue2.getCapacity()) {
                System.out.print(queue2.getCustomers()[i] != null ? "O" : "X");
            }
            System.out.print("\t\t");
            if (i < queue3.getCapacity()) {
                System.out.print(queue3.getCustomers()[i] != null ? "O" : "X");
            }
            System.out.println();
        }
        System.out.println("\nX - Not Occupied   O - Occupied");
    }


    private static void viewAllEmptyQueues() {
        int index = 1;
        for (FoodQueue queue : queues) {
            System.out.println("Queue " + index);
            for (int i = 0; i < queue.getCapacity(); i++) {
                if (queue.getCustomers()[i] == null) {
                    System.out.println("Slot " + (i + 1) + " : Empty");
                } else {
                    System.out.println("Slot " + (i + 1) + " : " + queue.getCustomers()[i].getFirstName());  // If the slot has a customer, print the customer's first name
                }
            }
            index++;
        }
    }



    private static int waitingListIndex = 0;   // Index for circular queue implementation

    private static void addCustomer() {
        if (burgersInStock > 0) {
            System.out.print("Enter First Name: ");
            String firstName = userInput.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = userInput.nextLine();
            System.out.print("Enter Burgers Needed: ");
            int burgersNeeded = Integer.parseInt(userInput.nextLine());     // Read the number of burgers needed

            if (burgersNeeded < 1)
                System.out.println("You can not add zero or minus burgers !");

            else if (burgersNeeded < burgersInStock) {
                Customer customer = new Customer(firstName, lastName, burgersNeeded);    // Create a new customer object with the entered details

                int minIndex = 0;       // Initialize the index of the queue with the minimum length
                int minLength = Integer.MAX_VALUE;     // Initialize the minimum length of the queues

                // Find the queue with the minimum length
                for (int i = 0; i < queues.length; i++) {
                    int queueLength = queues[i].getQueueFilledLength();

                    if (queueLength == queues[i].getCapacity()) {
                        continue;     // Skip if the queue is already full
                    } else if (queueLength < minLength) {
                        minLength = queueLength;
                        minIndex = i;  // Update the index of the queue with the minimum length
                    }
                }

                if (minLength >= queues[minIndex].getCapacity()) {
                    // If the minimum length is equal to the capacity, add the customer to the waiting list
                    System.out.println("Added customer to the Waiting List.");
                    waitingList.add(waitingListIndex, customer);  // Add the customer to the waiting list at the current index
                    waitingListIndex = (waitingListIndex + 1) % maxQueueLimit.length;  // Implement circular queue for the waiting list
                } else {
                    if (!waitingList.isEmpty()) {
                        // If the waiting list is not empty, add the next customer from the waiting list to the selected queue
                        Customer nextCustomer = waitingList.remove(waitingListIndex);  // Get the next customer from the waiting list
                        queues[minIndex].addCustomer(nextCustomer);  // Add the customer to the selected queue
                        System.out.println("Added customer from waiting list to Queue " + (minIndex + 1));
                        burgersInStock -= nextCustomer.getNobr();
                        waitingListIndex = (waitingListIndex - 1 + maxQueueLimit.length) % maxQueueLimit.length;  // Update the waiting list index using circular queue logic
                    } else {
                        // If the waiting list is empty, add the customer directly to the selected queue
                        queues[minIndex].addCustomer(customer);  // Add the customer to the selected queue
                        System.out.println("Added customer to Cashier " + (minIndex + 1) + " Queue.");
                        burgersInStock -= burgersNeeded;
                    }

                    if (burgersInStock <= warningLimit) {
                        System.out.println("Warning: Low stock. Remaining stock: " + burgersInStock);
                    }
                }
            } else {
                System.out.println("Enter an amount below " + burgersInStock);
            }
        } else {
            System.out.println("Burgers Out of Stock");
        }
    }

    private static void removeCustomer() {
        System.out.println("Enter Queue Number: ");
        int queueNumber = Integer.parseInt(userInput.nextLine());  // Read the queue number from the user
        System.out.println("Enter Queue Index: ");
        int queueIndex = Integer.parseInt(userInput.nextLine());  // Read the queue index from the user

        if (queueNumber > 0 && queueNumber < 4 && queueIndex > 0 && queueIndex <= queues[queueNumber - 1].getQueueFilledLength()) {
            // Check if the queue number and index are valid

            FoodQueue selectedQueue = queues[queueNumber - 1];  // Get the selected queue based on the queue number
            Customer[] customers = selectedQueue.getCustomers();  // Get the array of customers in the selected queue

            int removedCustomerBurgers = customers[queueIndex - 1].getNobr();  // Get the number of burgers of the removed customer
            burgersInStock += removedCustomerBurgers;  // Increase the number of burgers in stock

            // Shift customers to fill the empty position caused by the removal
            for (int i = queueIndex - 1; i < selectedQueue.getQueueFilledLength() - 1; i++) {
                customers[i] = customers[i + 1];
            }
            customers[selectedQueue.getQueueFilledLength() - 1] = null;  // Set the last position as null

            System.out.println("Customer Removed Successfully");

            if (!waitingList.isEmpty()) {
                // If the waiting list is not empty, add the next customer to the selected queue
                Customer nextCustomer = waitingList.remove(0);  // Get the next customer from the waiting list
                queues[queueNumber - 1].addCustomer(nextCustomer);  // Add the customer to the selected queue
                System.out.println("Customer Added From Waiting List");
                burgersInStock -= nextCustomer.getNobr();
            }
        } else {
            System.out.println("Invalid Queue or Index");
        }
    }


    private static void removeServedCustomer() {
        System.out.println("Enter Queue Number: ");
        int queueNumber = Integer.parseInt(userInput.nextLine());  // Read the queue number from the user

        if (queues[queueNumber - 1] == null)   //// Check if the selected queue is empty
            System.out.println("Queue is Empty !");

        else if (queueNumber > 0 && queueNumber < 4) {    // If the queue number is valid

            income[queueNumber - 1] += queues[queueNumber - 1].getCustomers()[0].getNobr() * 650;    // Increase the income of the corresponding queue by the number of burgers served multiplied by the price
            queues[queueNumber - 1].getCustomers()[0] = null;  // Set the first customer as null to remove the served customer
            System.out.println("Served Customer Removed Successfully");

            for (int i = 0; i < queues[queueNumber - 1].getCapacity() - 1; i++) {   // Shift the customers to fill the empty position caused by the removal

                queues[queueNumber - 1].getCustomers()[i] = queues[queueNumber - 1].getCustomers()[i + 1];
            }

            queues[queueNumber - 1].getCustomers()[queues[queueNumber - 1].getCapacity() - 1] = null;  // Set the last position as null


            if (!waitingList.isEmpty()) {   // If the waiting list is not empty, add the next customer to the selected queue

                queues[queueNumber - 1].getCustomers()[queues[queueNumber - 1].getQueueFilledLength()] = waitingList.get(0);  // Add the customer from the waiting list to the selected queue
                System.out.println("Customer Added From Waiting List");
                burgersInStock -= waitingList.get(0).getNobr();
                waitingList.remove(0);  // Remove the customer from the waiting list
            }
        } else {
            System.out.println("Invalid Queue number");
        }
    }

    private static void viewCustomersSorted() {
        int queueIndex = 1;
        for (FoodQueue queue : queues) {
            System.out.println("\nQueue " + queueIndex);

            ArrayList<String> sorting = new ArrayList<>();  // Create an ArrayList to store the customer names for sorting

            for (int i = 0; i < queue.getCustomers().length; i++) {
                if (queue.getCustomers()[i] != null) {
                    sorting.add(queue.getCustomers()[i].getFullName());  // Add the full name of each customer to the sorting ArrayList
                }
            }

            Collections.sort(sorting);  // Sort the ArrayList in alphabetical order

            for (int j = 0; j < sorting.size(); j++) {
                System.out.println(sorting.get(j));
            }

            queueIndex++;  // Increment the queue index
        }
    }


    private static void storeProgramData() {
        try {
            FileWriter write = new FileWriter("Text.txt", true);          // Create a FileWriter object with the file name "Text.txt"
            for (FoodQueue queue : queues) {
                for (int i = 0; i < queue.getCustomers().length; i++) {
                    if (queue.getCustomers()[i] != null) {
                        write.append(queue.getCustomers()[i].getFullName());  // Append the full name of each customer to the file
                    }
                }
            }
            write.close();                                                 // Close the FileWriter object
            System.out.println("Program Data Stored Successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while storing program data.");
            e.printStackTrace();                                         // Print the stack trace if an exception occurs
        }
    }


    private static void loadProgramData() {
        try {
            File readFile = new File("Text.txt");  // Create a File object with the file name "Text.txt"
            Scanner reader = new Scanner(readFile);     // Create a Scanner object to read from the file

            while (reader.hasNextLine()) {
                String text = reader.nextLine();    // Read the next line from the file
                System.out.println(text);    // Print the line to the console
            }

            System.out.println("\nStored data in file");
            reader.close();    // Close the Scanner object
        } catch (IOException e) {
            System.out.println("Error File Reading");
        }
    }



    private static void viewRemainingStock() {
        System.out.println("Remaining Stock of Burgers: " + burgersInStock);
    }


    private static void addBurgersToStock() {
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = Integer.parseInt(userInput.nextLine());  // Read the number of burgers to add from the user

        burgersInStock += burgersToAdd;
        System.out.println("Burgers added to the stock.");
    }

    private static void incomeOfEachQueue() {
        for (int i = 0; i < income.length; i++) {
            System.out.println("Income of Queue " + (i + 1) + ": " + income[i]);
        }
    }


}

