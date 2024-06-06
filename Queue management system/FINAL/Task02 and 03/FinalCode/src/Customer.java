public class Customer {
    private String firstName;  //Declaration of three public instance variables
    private String lastName;
    private int nobr;

    public Customer(String firstName, String lastName, int nobr) {
        this.firstName = firstName;  //Assigning the values of the constructor parameters to the corresponding instance variables using the this keyword
        this.lastName = lastName;
        this.nobr = nobr;
    }

    public String getLastName() {
        return lastName;
    }  //A getter method that returns the last name of the customer

    public String getFirstName() {
        return firstName;
    }  // getter method that returns the first name of the customer

    public int getNobr() {
        return nobr;
    }  //A getter method that returns the number of burgers needed by the customer.

    public String getFullName() {
        return firstName + " " + lastName;
    }   //A method that returns the full name of the customer by concatenating the first name and last name with a space in between

}
