/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import account.Account;
import fileio.ReadAccounts;
import gui.GUI;
import java.util.LinkedList;
import javax.swing.JFrame;

// The starting point of the banking application
public class Main {
    
    // Launch the program
    public static void main(String[] args) {
        try {
            // Step 1: Load existing accounts from file
            ReadAccounts reader = new ReadAccounts("Accounts.csv");
            LinkedList<Account> accounts = reader.readAccounts();
            
            // Step 2: Start the banking interface with loaded accounts
            launchGUI(accounts);

        } catch (Exception e) {
            // If something goes wrong (like missing file):
            System.err.println("Error loading accounts: " + e.getMessage());
            
            // Still start the program but with empty account list
            launchGUI(new LinkedList<>());
        }
    }

    // Creates and displays the banking window
    private static void launchGUI(LinkedList<Account> accounts) {
        // This makes sure the GUI loads properly
        java.awt.EventQueue.invokeLater(() -> {
            // Create the main banking window
            GUI gui = new GUI(accounts);
            
            // Make the window open fullscreen
            gui.setExtendedState(JFrame.MAXIMIZED_BOTH);  
            
            // Show the window to the user
            gui.setVisible(true);
        });
    }
}
