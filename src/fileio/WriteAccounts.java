/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fileio;

import account.Account;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;

// Saves bank account information to a file
public class WriteAccounts {
    // Stores the location where we'll save the account data
    private String filePath;

    // Prepares to write accounts to specified file
    // filePath - Location of the file where we'll save account data
    public WriteAccounts(String filePath) {
        this.filePath = filePath;
    }

    // Saves all accounts to file in CSV format
    // accounts - List of accounts to save
    // Throws IOException if file can't be written
    public void updateAccounts(LinkedList<Account> accounts) throws IOException {
        // Open the file for writing (this will overwrite existing file)
        FileWriter writer = new FileWriter(filePath);
        
        // Process each account in the list
        for (Account acc : accounts) {
            // Convert account to CSV line: "John,Doe,1001,5000"
            writer.write(
                acc.getFirstName() + "," +  // First name
                acc.getLastName() + "," +   // Last name
                acc.getAccountNum() + "," + // Account number
                acc.getBalance() + "\n"     // Balance with newline
            );
        }
        
        // Close the file when done
        writer.close();
    }
}