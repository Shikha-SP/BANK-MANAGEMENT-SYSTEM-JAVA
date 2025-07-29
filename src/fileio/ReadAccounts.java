/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fileio;

import account.Account;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;

// Reads bank account data from a file and creates Account objects
public class ReadAccounts {
    // Stores the location of the accounts file
    private String filePath;

    // Prepares to read accounts from specified file
    // filePath - Location of the CSV file containing account data
    public ReadAccounts(String filePath) {
        this.filePath = filePath;
    }

    // Reads the file and creates Account objects for each record
    // Returns: List of all accounts found in the file
    // Throws Exception if file can't be read or has wrong format
    public LinkedList<Account> readAccounts() throws Exception {
        // Create empty list to store accounts we'll find
        LinkedList<Account> accounts = new LinkedList<>();
        
        // Open the file for reading
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Read each line until end of file
        while ((line = reader.readLine()) != null) {
            // Split line into parts: "John,Doe,1001,5000" becomes ["John","Doe","1001","5000"]
            String[] data = line.split(",");
            
            // Extract customer information
            String firstName = data[0].trim();    // First column: first name
            String lastName = data[1].trim();     // Second column: last name
            int accountNumber = Integer.parseInt(data[2].trim());  // Third column: account number
            int balance = Integer.parseInt(data[3].trim());        // Fourth column: balance
            
            // Create new account and add to our list
            accounts.add(new Account(firstName, lastName, accountNumber, balance));
        }
        
        // Close the file and return all accounts we found
        reader.close();
        return accounts;
    }
}