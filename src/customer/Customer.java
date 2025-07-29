/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package customer;

// Stores basic information about a bank customer
public class Customer {
    // Customer's first name (private to protect data)
    private String firstName;
    
    // Customer's last name (private to protect data)
    private String lastName;

    // Returns the customer's first name
    public String getFirstName() {
        return firstName;
    }

    // Sets/updates the customer's first name
    // firstName - New first name to store
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Returns the customer's last name
    public String getLastName() {
        return lastName;
    }

    // Sets/updates the customer's last name
    // lastName - New last name to store
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
