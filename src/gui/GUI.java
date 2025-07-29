/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

//
//  Bank Management System GUI
// 
// This class represents the main user interface for a banking application.
// It provides functionality for:
// - Account creation
// - Deposits and withdrawals
// - Funds transfer between accounts
// - Account information display
// 
package gui;


import account.Account;
import fileio.ReadAccounts;
import fileio.WriteAccounts;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

// Main GUI class for the Bank Management System
 
public class GUI extends javax.swing.JFrame {

// UI Components
    private final JButton[] functionButtons;// Array to hold navigation buttons
    private final Color defaultButtonColor = new Color(255, 204, 102);// Default button color
    private final Color selectedButtonColor = new Color(255, 179, 102);// Selected button color
    
    // Data Components
    private LinkedList<Account> accounts;// List to store all accounts
    private DefaultTableModel tableModel; // Model for account information table
    private final Random random = new Random();// For generating account numbers
    
    //Constructor to initialize the GUI
    public GUI(LinkedList<Account> accounts) {
        this.accounts = accounts;
        
        // Initialize components
        initComponents();
        initializeTable();
        loadAccounts();
        
        // Initialize the button array
functionButtons = new JButton[]{createAccountButton, depositWithdrawButton, transferPanelButton};

// Set createAccountPanel as default visible panel
showPanel("createAccount");
updateButtonSelection(createAccountButton);

// Add action listeners to buttons
createAccountButton.addActionListener(e -> {
    CardLayout cl = (CardLayout)(cardHolderPanel.getLayout());
    cl.show(cardHolderPanel, "card4");  // Matches createAccountPanel
    updateButtonSelection(createAccountButton);
});

depositWithdrawButton.addActionListener(e -> {
    CardLayout cl = (CardLayout)(cardHolderPanel.getLayout());
    cl.show(cardHolderPanel, "card3");  // Matches depositWithdrawPanel
    updateButtonSelection(depositWithdrawButton);
});
transferPanelButton.addActionListener(e -> {
    CardLayout cl = (CardLayout)(cardHolderPanel.getLayout());
    cl.show(cardHolderPanel, "card2");  // Matches transferPanel
    updateButtonSelection(transferPanelButton);
});
// Set default panel
CardLayout cl = (CardLayout)(cardHolderPanel.getLayout());
cl.show(cardHolderPanel, "card4");  // Show createAccountPanel by default
updateButtonSelection(createAccountButton);
        
        firstNameLabel.setForeground(java.awt.Color.WHITE);
        lastNameLabel.setForeground(java.awt.Color.WHITE);
        ageCheckBox.setForeground(java.awt.Color.WHITE);
        depositaccNumberLabel.setForeground(java.awt.Color.WHITE);
        depositAmountLabel.setForeground(java.awt.Color.WHITE);
        withdrawAccNumberLabel.setForeground(java.awt.Color.WHITE);
        withdrawAmountLabel.setForeground(java.awt.Color.WHITE);  
        senderAccountLabel.setForeground(java.awt.Color.WHITE);
        receiverAccountLabel.setForeground(java.awt.Color.WHITE);
        amountLabel.setForeground(java.awt.Color.WHITE);     
        ageCheckBox.setForeground(java.awt.Color.WHITE);
        ageCheckBox.setBackground(new Color(71, 95, 118));
        ageCheckBox.setFocusPainted(false);
        createAccountButton.setFocusPainted(false);
        depositWithdrawButton.setFocusPainted(false);
        transferPanelButton.setFocusPainted(false);
        createButton.setFocusPainted(false);
        transferButton.setFocusPainted(false);
        depositButton.setFocusPainted(false);
        withdrawButton.setFocusPainted(false);
        
         infoTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 20));
                 JTableHeader header = infoTable.getTableHeader();
    header.setPreferredSize(new Dimension(
        header.getPreferredSize().width,  // Keep original width
        40  // New height in pixels (adjust as needed)
    ));
            // Create center-aligned renderer for DATA CELLS ONLY
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Center alignment
    
    // Apply to all columns
    for (int i = 0; i < infoTable.getColumnCount(); i++) {
        infoTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
        // Set uniform row height (in pixels)
    infoTable.setRowHeight(50);  // Adjust value as needed
    
    // Optional: Increase font size for better visibility
    infoTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    
    DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
headerRenderer.setHorizontalAlignment(JLabel.CENTER); // Center align
headerRenderer.setBackground(new Color(0xFFCC66)); // Set background color
headerRenderer.setOpaque(true); // Make sure background is painted
for (int i = 0; i < infoTable.getColumnCount(); i++) {
    infoTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
}


    }
    //Initializes the table model for account display
    private void initializeTable() {
        tableModel = (DefaultTableModel) infoTable.getModel();
        infoTable.setModel(tableModel);
    }
//Loads accounts from file or initializes empty list if file doesn't exist
private void loadAccounts() {
    try {
        ReadAccounts reader = new ReadAccounts("Accounts.csv");
        accounts = reader.readAccounts();
        displayAllAccounts();
    } catch (Exception e) {
        System.err.println("Error loading accounts: " + e.getMessage());
        accounts = new LinkedList<>(); // Initialize empty list if file doesn't exist
    }
}
//Displays all accounts in the table
private void displayAllAccounts() {
    tableModel.setRowCount(0); // Clear existing data
    
    for (Account acc : accounts) {
        Object[] row = {
            acc.getFirstName(),
            acc.getLastName(),
            acc.getAccountNum(),
            "Rs." + acc.getBalance() // Format balance with currency
        };
        tableModel.addRow(row);
    }
}
//Searches for an account by number and displays it in the table
//accountNumber: The account number to search for
        private void searchAccount(int accountNumber) {
        tableModel.setRowCount(0); // Clear existing data
        
        for (Account acc : accounts) {
            if (acc.getAccountNum() == accountNumber) {
                Object[] row = {
                    acc.getFirstName(),
                    acc.getLastName(),
                    acc.getAccountNum(),
                    acc.getBalance()
                };
                tableModel.addRow(row);
                break; // Found the account, no need to continue
            }
        }
    }
        //Creates a new bank account with user-provided information
        private void createAccount() {
    // 1. Validate age checkbox
    if (!ageCheckBox.isSelected()) {
        JOptionPane.showMessageDialog(this, 
            "You must be 18+ to create an account",
            "Age Verification Failed",
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 2. Get and validate names
    String firstName = firstNameFormattedField.getText().trim();
    String lastName = lastNameFormattedField.getText().trim();

    if (firstName.isEmpty() || lastName.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Please enter both first and last names",
            "Missing Information",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 3. Confirmation dialog
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Create account for:\n\n" +
        "Name: " + firstName + " " + lastName + "\n" +
        "Age Verified",
        "Confirm Account Creation",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            // 4. Generate account number
            int accountNumber = generateAccountNumber();
            
            // 5. Create and save account
            Account newAccount = new Account(firstName, lastName, accountNumber, 0);
            accounts.add(newAccount);
            
            // Write to file
            WriteAccounts writer = new WriteAccounts("Accounts.csv");
            writer.updateAccounts(accounts);

            // 6. Show success
            JOptionPane.showMessageDialog(this,
                "Account Created Successfully\n" +
                "Account Number: " + accountNumber + "\n" +
                "Account Holder: " + firstName + " " + lastName,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

            // 7. Reset form and refresh table
            firstNameFormattedField.setText("");
            lastNameFormattedField.setText("");
            ageCheckBox.setSelected(false);
            displayAllAccounts();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Failed to save account: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
     //Shows the specified panel in the card layout
     //cardName: The name of the panel to show
private void showPanel(String cardName) {
    CardLayout cl = (CardLayout)(cardHolderPanel.getLayout());
    cl.show(cardHolderPanel, cardName);
}

//Updates the visual selection state of navigation buttons
//selectedButton: The currently selected button
private void updateButtonSelection(JButton selectedButton) {
    // Reset all buttons to default style
    for (JButton button : functionButtons) {
        button.setBackground(defaultButtonColor);
        button.setFont(button.getFont().deriveFont(Font.PLAIN));
    }
    
    // Highlight the selected button
    selectedButton.setBackground(selectedButtonColor);
    selectedButton.setFont(selectedButton.getFont().deriveFont(Font.BOLD));
}

//Generates a unique account number
//returns A unique account number
private int generateAccountNumber() {
    Random rand = new Random();
    int attempts = 0;
    int maxAttempts = 100;
    
    while (attempts < maxAttempts) {
        int potentialNumber = rand.nextInt(9000) + 1000; // 1000-9999 range
        
        boolean exists = accounts.stream()
            .anyMatch(acc -> acc.getAccountNum() == potentialNumber);
            
        if (!exists) {
            return potentialNumber;
        }
        attempts++;
    }
    
    // Fallback if couldn't find unique number
    return rand.nextInt(90000) + 10000; // 10000-99999 range
}
        
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bankingPanel = new javax.swing.JPanel();
        depositWithdrawButton = new javax.swing.JButton();
        transferPanelButton = new javax.swing.JButton();
        createAccountButton = new javax.swing.JButton();
        cardHolderPanel = new javax.swing.JPanel();
        depositWithdrawPanel = new javax.swing.JPanel();
        depositaccNumberLabel = new javax.swing.JLabel();
        depositAmountLabel = new javax.swing.JLabel();
        accDeposit = new javax.swing.JFormattedTextField();
        depositInput = new javax.swing.JFormattedTextField();
        depositButton = new javax.swing.JButton();
        withdrawButton = new javax.swing.JButton();
        withdrawInput = new javax.swing.JFormattedTextField();
        accWithdraw = new javax.swing.JFormattedTextField();
        withdrawAccNumberLabel = new javax.swing.JLabel();
        withdrawAmountLabel = new javax.swing.JLabel();
        createAccountPanel = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        lastNameFormattedField = new javax.swing.JFormattedTextField();
        firstNameFormattedField = new javax.swing.JFormattedTextField();
        ageCheckBox = new javax.swing.JCheckBox();
        createButton = new javax.swing.JButton();
        transferPanel = new javax.swing.JPanel();
        senderAccountLabel = new javax.swing.JLabel();
        acc1Transfer = new javax.swing.JFormattedTextField();
        receiverAccountLabel = new javax.swing.JLabel();
        acc2Transfer = new javax.swing.JFormattedTextField();
        amountLabel = new javax.swing.JLabel();
        transferAmount = new javax.swing.JFormattedTextField();
        transferButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        infoTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        accountNumberFormattedField = new javax.swing.JFormattedTextField();
        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bankingPanel.setBackground(new java.awt.Color(71, 95, 118));

        depositWithdrawButton.setBackground(new java.awt.Color(255, 204, 102));
        depositWithdrawButton.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        depositWithdrawButton.setForeground(new java.awt.Color(0, 0, 0));
        depositWithdrawButton.setText("Deposit-Withdraw");
        depositWithdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositWithdrawButtonActionPerformed(evt);
            }
        });

        transferPanelButton.setBackground(new java.awt.Color(255, 204, 102));
        transferPanelButton.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        transferPanelButton.setForeground(new java.awt.Color(0, 0, 0));
        transferPanelButton.setText("Transfer");

        createAccountButton.setBackground(new java.awt.Color(255, 204, 102));
        createAccountButton.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        createAccountButton.setForeground(new java.awt.Color(0, 0, 0));
        createAccountButton.setText("Create Account");

        cardHolderPanel.setBackground(new java.awt.Color(255, 51, 51));
        cardHolderPanel.setLayout(new java.awt.CardLayout());

        depositWithdrawPanel.setBackground(new java.awt.Color(71, 95, 118));

        depositaccNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        depositaccNumberLabel.setText("Account Number:");

        depositAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        depositAmountLabel.setText("Amount:");

        accDeposit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        accDeposit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        depositInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        depositInput.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        depositButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        depositButton.setText("Deposit");
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });

        withdrawButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        withdrawButton.setText("Withdraw");
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });

        withdrawInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        withdrawInput.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        accWithdraw.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        accWithdraw.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        withdrawAccNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        withdrawAccNumberLabel.setText("Account Number:");

        withdrawAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        withdrawAmountLabel.setText("Amount:");

        javax.swing.GroupLayout depositWithdrawPanelLayout = new javax.swing.GroupLayout(depositWithdrawPanel);
        depositWithdrawPanel.setLayout(depositWithdrawPanelLayout);
        depositWithdrawPanelLayout.setHorizontalGroup(
            depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, depositWithdrawPanelLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(withdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(depositWithdrawPanelLayout.createSequentialGroup()
                        .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(withdrawAccNumberLabel)
                            .addComponent(withdrawAmountLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(withdrawInput)
                            .addComponent(accWithdraw, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(depositButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(depositWithdrawPanelLayout.createSequentialGroup()
                        .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(depositaccNumberLabel)
                            .addComponent(depositAmountLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(depositInput)
                            .addComponent(accDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );
        depositWithdrawPanelLayout.setVerticalGroup(
            depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositWithdrawPanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(depositaccNumberLabel))
                .addGap(15, 15, 15)
                .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(depositInput, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(depositAmountLabel))
                .addGap(18, 18, 18)
                .addComponent(depositButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accWithdraw, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(withdrawAccNumberLabel))
                .addGap(15, 15, 15)
                .addGroup(depositWithdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(withdrawInput, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(withdrawAmountLabel))
                .addGap(18, 18, 18)
                .addComponent(withdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        cardHolderPanel.add(depositWithdrawPanel, "card3");

        createAccountPanel.setBackground(new java.awt.Color(71, 95, 118));

        firstNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        firstNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        firstNameLabel.setText("First Name:");

        lastNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lastNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lastNameLabel.setText("Last Name:");

        lastNameFormattedField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lastNameFormattedField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        firstNameFormattedField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        firstNameFormattedField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        ageCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ageCheckBox.setText("  I am over 18");

        createButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        createButton.setText("Create");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout createAccountPanelLayout = new javax.swing.GroupLayout(createAccountPanel);
        createAccountPanel.setLayout(createAccountPanelLayout);
        createAccountPanelLayout.setHorizontalGroup(
            createAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(firstNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAccountPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(createAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAccountPanelLayout.createSequentialGroup()
                        .addComponent(lastNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAccountPanelLayout.createSequentialGroup()
                        .addComponent(firstNameFormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAccountPanelLayout.createSequentialGroup()
                        .addGroup(createAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lastNameFormattedField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                            .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ageCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35))))
        );
        createAccountPanelLayout.setVerticalGroup(
            createAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createAccountPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(firstNameLabel)
                .addGap(18, 18, 18)
                .addComponent(firstNameFormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lastNameLabel)
                .addGap(18, 18, 18)
                .addComponent(lastNameFormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(ageCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        cardHolderPanel.add(createAccountPanel, "card4");

        transferPanel.setBackground(new java.awt.Color(71, 95, 118));

        senderAccountLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        senderAccountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        senderAccountLabel.setText("Sender's Account:");

        acc1Transfer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        acc1Transfer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        receiverAccountLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        receiverAccountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        receiverAccountLabel.setText("Receiver's Account:");

        acc2Transfer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        acc2Transfer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        amountLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        amountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        amountLabel.setText("Amount:");

        transferAmount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        transferAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        transferButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        transferButton.setText("Transfer");
        transferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout transferPanelLayout = new javax.swing.GroupLayout(transferPanel);
        transferPanel.setLayout(transferPanelLayout);
        transferPanelLayout.setHorizontalGroup(
            transferPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transferPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(amountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(transferPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(transferPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(receiverAccountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(senderAccountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transferPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(transferPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transferPanelLayout.createSequentialGroup()
                        .addComponent(acc1Transfer, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transferPanelLayout.createSequentialGroup()
                        .addComponent(acc2Transfer, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
            .addGroup(transferPanelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(transferPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(transferButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transferAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        transferPanelLayout.setVerticalGroup(
            transferPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transferPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(senderAccountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(acc1Transfer, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(receiverAccountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(acc2Transfer, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(amountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transferAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(transferButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        cardHolderPanel.add(transferPanel, "card2");

        javax.swing.GroupLayout bankingPanelLayout = new javax.swing.GroupLayout(bankingPanel);
        bankingPanel.setLayout(bankingPanelLayout);
        bankingPanelLayout.setHorizontalGroup(
            bankingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankingPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bankingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bankingPanelLayout.createSequentialGroup()
                        .addComponent(createAccountButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(depositWithdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transferPanelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cardHolderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        bankingPanelLayout.setVerticalGroup(
            bankingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankingPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(bankingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(depositWithdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transferPanelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cardHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tablePanel.setBackground(new java.awt.Color(255, 255, 255));
        tablePanel.setForeground(new java.awt.Color(255, 255, 255));

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        infoTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Account Number", "Balance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(infoTable);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 51));
        jLabel9.setText("Account Number:");

        accountNumberFormattedField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        accountNumberFormattedField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        accountNumberFormattedField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                accountNumberFormattedFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(accountNumberFormattedField, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accountNumberFormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane)
                .addContainerGap())
        );

        headerPanel.setBackground(new java.awt.Color(255, 204, 102));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bank Management System");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bankingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bankingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void depositWithdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositWithdrawButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_depositWithdrawButtonActionPerformed

    private void accountNumberFormattedFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accountNumberFormattedFieldKeyReleased
    try {
        String input = accountNumberFormattedField.getText().trim();
        if (input.isEmpty()) {
            displayAllAccounts();
        } else {
            int accountNumber = Integer.parseInt(input);
            searchAccount(accountNumber);
        }
    } catch (NumberFormatException e) {
        // Clear table if invalid input
        if (!accountNumberFormattedField.getText().trim().isEmpty()) {
            tableModel.setRowCount(0);
        }
    }      // TODO add your handling code here:
    }//GEN-LAST:event_accountNumberFormattedFieldKeyReleased

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
      createAccount();    
      // TODO add your handling code here:
    }//GEN-LAST:event_createButtonActionPerformed

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositButtonActionPerformed
    try {
        // Get input values
        String accNumberText = accDeposit.getText().trim();
        String amountText = depositInput.getText().trim();
        
        // Validate fields aren't empty
        if (accNumberText.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int accountNumber = Integer.parseInt(accNumberText);
        int amount = Integer.parseInt(amountText);
        
        // Validate amount is positive
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Deposit amount must be positive",
                "Invalid Amount",
                JOptionPane.ERROR_MESSAGE);
            clearDepositFields();
            return;
        }
        
        // Find account and deposit
        boolean accountFound = false;
        for (Account acc : accounts) {
            if (acc.getAccountNum() == accountNumber) {
                acc.deposit(amount);
                accountFound = true;
                    try {
        WriteAccounts writer = new WriteAccounts("Accounts.csv");
        writer.updateAccounts(accounts); // Updates all accounts in the file
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, 
            "Deposit successful but failed to update file: " + e.getMessage(),
            "File Error",
            JOptionPane.WARNING_MESSAGE);
    }
                JOptionPane.showMessageDialog(this, 
                    "Successfully deposited Rs." + amount,
                    "Transaction Complete",
                    JOptionPane.INFORMATION_MESSAGE);
                displayAllAccounts(); // Refresh table
                clearDepositFields();
                break;
            }
        }
        
        if (!accountFound) {
            JOptionPane.showMessageDialog(this, 
                "Account not found",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            clearDepositFields();
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Please enter valid numbers",
            "Invalid Input",
            JOptionPane.ERROR_MESSAGE);
        clearDepositFields();
    }
}  

private void clearDepositFields() {
    accDeposit.setText("");
    depositInput.setText("");
    accDeposit.requestFocus(); // Put cursor back in account field        // TODO add your handling code here:
    }//GEN-LAST:event_depositButtonActionPerformed

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawButtonActionPerformed
    try {
        // Get input values
        String accNumberText = accWithdraw.getText().trim();
        String amountText = withdrawInput.getText().trim();
        
        // Validate fields aren't empty
        if (accNumberText.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int accountNumber = Integer.parseInt(accNumberText);
        int amount = Integer.parseInt(amountText);
        
        // Validate amount is positive
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Withdrawal amount must be positive",
                "Invalid Amount",
                JOptionPane.ERROR_MESSAGE);
            clearWithdrawFields();
            return;
        }
        
        // Find account and withdraw
        boolean accountFound = false;
        for (Account acc : accounts) {
            if (acc.getAccountNum() == accountNumber) {
                accountFound = true;
                
                // Check sufficient balance
                if (acc.getBalance() >= amount) {      
                    acc.withdraw(amount);
                        try {
        WriteAccounts writer = new WriteAccounts("Accounts.csv");
        writer.updateAccounts(accounts); // Updates all accounts in the file
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, 
            "Withdrawal successful but failed to update file: " + e.getMessage(),
            "File Error",
            JOptionPane.WARNING_MESSAGE);
    }
                    JOptionPane.showMessageDialog(this, 
                        "Successfully withdrew Rs." + amount,
                        "Transaction Complete",
                        JOptionPane.INFORMATION_MESSAGE);
                    displayAllAccounts(); // Refresh table
                    clearWithdrawFields();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Insufficient funds!\nCurrent balance: Rs." + acc.getBalance(),
                        "Transaction Failed",
                        JOptionPane.ERROR_MESSAGE);
                    clearWithdrawFields();
                }
                break;
            }
        }
        
        if (!accountFound) {
            JOptionPane.showMessageDialog(this, 
                "Account not found",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            clearWithdrawFields();
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Please enter valid numbers",
            "Invalid Input",
            JOptionPane.ERROR_MESSAGE);
        clearWithdrawFields();
    }
}

    //Clears the withdrawal form fields
private void clearWithdrawFields() {
    accWithdraw.setText("");
    withdrawInput.setText("");
    accWithdraw.requestFocus(); // Put cursor back in account field        // TODO add your handling code here:
    }//GEN-LAST:event_withdrawButtonActionPerformed

    private void transferButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferButtonActionPerformed
            try {
        // Get input values
        String sourceAccText = acc1Transfer.getText().trim();
        String targetAccText = acc2Transfer.getText().trim();
        String amountText = transferAmount.getText().trim();
        
        // Validate fields aren't empty
        if (sourceAccText.isEmpty() || targetAccText.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Parse values
        int sourceAccountNumber = Integer.parseInt(sourceAccText);
        int targetAccountNumber = Integer.parseInt(targetAccText);
        int amount = Integer.parseInt(amountText);
        
        // Validate accounts are different
        if (sourceAccountNumber == targetAccountNumber) {
            JOptionPane.showMessageDialog(this, 
                "Source and target accounts cannot be the same",
                "Invalid Transfer",
                JOptionPane.ERROR_MESSAGE);
            clearTransferFields();
            return;
        }
        
        // Validate amount is positive
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Transfer amount must be positive",
                "Invalid Amount",
                JOptionPane.ERROR_MESSAGE);
            clearTransferFields();
            return;
        }
        
        // Find accounts
        Account sourceAccount = null;
        Account targetAccount = null;
        
        for (Account acc : accounts) {
            if (acc.getAccountNum() == sourceAccountNumber) {
                sourceAccount = acc;
            }
            if (acc.getAccountNum() == targetAccountNumber) {
                targetAccount = acc;
            }
            if (sourceAccount != null && targetAccount != null) break;
        }
        
        // Validate accounts exist
        if (sourceAccount == null) {
            JOptionPane.showMessageDialog(this, 
                "Source account not found",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            clearTransferFields();
            return;
        }
        
        if (targetAccount == null) {
            JOptionPane.showMessageDialog(this, 
                "Target account not found",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            clearTransferFields();
            return;
        }
        
        // Check sufficient balance
        if (sourceAccount.getBalance() < amount) {
            JOptionPane.showMessageDialog(this, 
                "Insufficient funds in source account!\nCurrent balance: Rs." + sourceAccount.getBalance(),
                "Transfer Failed",
                JOptionPane.ERROR_MESSAGE);
            clearTransferFields();
            return;
        }
        
        // Confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Transfer Rs." + amount + " from account " + sourceAccountNumber + 
            " to account " + targetAccountNumber + "?",
            "Confirm Transfer",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Perform transfer
            sourceAccount.withdraw(amount);
            targetAccount.deposit(amount);
            
            // Update CSV file if needed
            WriteAccounts writer = new WriteAccounts("Accounts.csv");
            writer.updateAccounts(accounts);
            
            JOptionPane.showMessageDialog(this, 
                "Transfer completed successfully",
                "Transaction Complete",
                JOptionPane.INFORMATION_MESSAGE);
            
            displayAllAccounts(); // Refresh table
            clearTransferFields();
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Please enter valid numbers",
            "Invalid Input",
            JOptionPane.ERROR_MESSAGE);
        clearTransferFields();
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, 
            "Failed to update account records: " + e.getMessage(),
            "File Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
//Clears the transfer form fields
private void clearTransferFields() {
    acc1Transfer.setText("");
    acc2Transfer.setText("");
    transferAmount.setText("");
    acc1Transfer.requestFocus(); // Return focus to first field
// TODO add your handling code here:
    
    
    }//GEN-LAST:event_transferButtonActionPerformed


//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new GUI(accounts).setVisible(true);
//            }
//            
//        });
//            }
//            
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField acc1Transfer;
    private javax.swing.JFormattedTextField acc2Transfer;
    private javax.swing.JFormattedTextField accDeposit;
    private javax.swing.JFormattedTextField accWithdraw;
    private javax.swing.JFormattedTextField accountNumberFormattedField;
    private javax.swing.JCheckBox ageCheckBox;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JPanel bankingPanel;
    private javax.swing.JPanel cardHolderPanel;
    private javax.swing.JButton createAccountButton;
    private javax.swing.JPanel createAccountPanel;
    private javax.swing.JButton createButton;
    private javax.swing.JLabel depositAmountLabel;
    private javax.swing.JButton depositButton;
    private javax.swing.JFormattedTextField depositInput;
    private javax.swing.JButton depositWithdrawButton;
    private javax.swing.JPanel depositWithdrawPanel;
    private javax.swing.JLabel depositaccNumberLabel;
    private javax.swing.JFormattedTextField firstNameFormattedField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTable infoTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JFormattedTextField lastNameFormattedField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JLabel receiverAccountLabel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel senderAccountLabel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JFormattedTextField transferAmount;
    private javax.swing.JButton transferButton;
    private javax.swing.JPanel transferPanel;
    private javax.swing.JButton transferPanelButton;
    private javax.swing.JLabel withdrawAccNumberLabel;
    private javax.swing.JLabel withdrawAmountLabel;
    private javax.swing.JButton withdrawButton;
    private javax.swing.JFormattedTextField withdrawInput;
    // End of variables declaration//GEN-END:variables
}
