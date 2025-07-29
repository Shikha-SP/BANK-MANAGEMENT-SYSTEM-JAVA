/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package account;

import customer.Customer;

// A bank account that stores money and handles transactions
// Inherits customer details (name) from the Customer class
public class Account extends Customer {
    // Current money in the account (private for security)
    private int balance;
    
    // Unique account ID (can't change after creation)
    private final int accountNumber;

    // Creates a new account for a customer
    // firstName/lastName - Customer's name (stored in parent Customer class)
    // accountNumber - Unique ID for this account
    // balance - Starting amount of money
    public Account(String firstName, String lastName, int accountNumber, int balance) {
        // Store customer name using parent class methods
        setFirstName(firstName);
        setLastName(lastName);
        
        // Set up account details
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Returns how much money is currently in the account
    public int getBalance() {
        return balance;
    }

    // Returns this account's unique number
    public int getAccountNum() {
        return accountNumber;
    }

    // Adds money to the account
    // amount - Must be positive number
    // Returns true if deposit worked, false if amount was invalid
    public boolean deposit(int amount) {
        // Only allow positive amounts
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    // Takes money out of the account
    // amount - Must be positive and <= current balance
    // Returns true if withdrawal worked, false if invalid amount or not enough money
    public boolean withdraw(int amount) {
        // Check if amount is valid and account has enough funds
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
