/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transaction;

import account.Account;

// Handles money transfers between bank accounts
public class Transaction {
    
    // Moves money from one account to another
    // source - Account where money comes from
    // destination - Account where money goes to
    // amount - How much to transfer (must be positive)
    public void transfer(Account source, Account destination, int amount) {
        // Take money out of source account
        source.withdraw(amount);
        
        // Add money to destination account
        destination.deposit(amount);
    }
}