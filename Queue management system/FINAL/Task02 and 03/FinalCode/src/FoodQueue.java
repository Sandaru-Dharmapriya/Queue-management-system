
public class FoodQueue {
    private int capacity;             // Maximum capacity of the queue
    private Customer[] customerObjects;   // Array to store Customer objects in the queue

    public FoodQueue(int capacity) {    // Constructor that initializes the FoodQueue with the given capacity
        this.capacity = capacity;
        customerObjects = new Customer[capacity];
    }

    public int getCapacity() {           // Getter method to retrieve the maximum capacity of the queue
        return this.capacity;
    }

    public Customer[] getCustomers() {   // Getter method to retrieve the Customer array in the queue
        return this.customerObjects;
    }

    public int getQueueFilledLength() {  // Method to get the filled length of the queue (number of non-null elements)
        int notNullIndexes = 0;
        for (int i = 0; i < customerObjects.length; i++) {
            if (customerObjects[i] != null) {
                notNullIndexes++;
            }
        }
        return notNullIndexes;
    }

    public void addCustomer(Customer customer) {  // Method to add a Customer object to the queue
        for (int i = 0; i < customerObjects.length; i++) {
            if (customerObjects[i] == null) {
                customerObjects[i] = customer;
                break;
            }
        }
    }
}