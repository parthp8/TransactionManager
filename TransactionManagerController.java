package com.banking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

/**
 * This class is the controller for the Transaction Manager GUI.
 * @author Jeeva Ramasamy, Parth Patel
 */
public class TransactionManagerController {
    @FXML
    private ToggleGroup accountType, campus;
    @FXML
    private Tab openTab, closeTab, dwTab;
    @FXML
    private TextField firstNameO, lastNameO, amountO;
    @FXML
    private TextField firstNameC, lastNameC;
    @FXML
    private TextField firstNameDW, lastNameDW, amountDW;
    @FXML
    private DatePicker dobO, dobC, dobDW;
    @FXML
    private RadioButton checkingO, collegeCheckingO, savingsO, moneyMarketO;
    @FXML
    private RadioButton checkingC, collegeCheckingC, savingsC, moneyMarketC;
    @FXML
    private RadioButton checkingDW, collegeCheckingDW, savingsDW, moneyMarketDW;
    @FXML
    private RadioButton newBrunswick, newark, camden;
    @FXML
    private RadioButton acctsOnly, acctsInterestsFees, acctsUpdatedInterestsFees;
    @FXML
    private CheckBox loyalCustomer;
    @FXML
    private TextArea output;

    private AccountDatabase acctDb = new AccountDatabase();
    private static final double EMPTY = 0.0;
    private static final int MIN_AGE = 16;
    private static final int COLLEGE_MAX_AGE = 24;
    private static final int MIN_MONEYMARKET_DEPOSIT = 2000;
    private static final int DOB_CREDENTIALS = 3;

    /**
     * Disables campus and loyal customer options when checking is selected
     * @param event the event
     */
    @FXML
    void onCheckingRadioButtonClick(ActionEvent event){
        campus.selectToggle(null);
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        loyalCustomer.setSelected(false);
        loyalCustomer.setDisable(true);
    }

    /**
     * Enables campus options and disables loyal customer options
     * when college checking is selected
     * @param event the action event
     */
    @FXML
    void onCollegeCheckingRadioButtonClick(ActionEvent event){
        newBrunswick.setDisable(false);
        newark.setDisable(false);
        camden.setDisable(false);
        loyalCustomer.setSelected(false);
        loyalCustomer.setDisable(true);
    }

    /**
     * Enables loyal customer options and disables campus options
     * when savings is selected
     * @param event the action event
     */
    @FXML
    void onSavingsRadioButtonClick(ActionEvent event){
        campus.selectToggle(null);
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        loyalCustomer.setSelected(false);
        loyalCustomer.setDisable(false);
    }

    /**
     * Disables campus and loyal customer options
     * when money market is selected
     * @param event the action event
     */
    @FXML
    void onMoneyMarketRadioButtonClick(ActionEvent event){
        campus.selectToggle(null);
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        loyalCustomer.setSelected(true);
        loyalCustomer.setDisable(true);
    }

    /**
     * Clears all fields in the open tab
     * @param event the action event
     */
    @FXML
    void onClearOButtonClick(ActionEvent event) {
        firstNameO.clear();
        lastNameO.clear();
        dobO.getEditor().clear();
        amountO.clear();
        accountType.selectToggle(null);
        campus.selectToggle(null);
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        loyalCustomer.setSelected(false);
        loyalCustomer.setDisable(true);
    }

    /**
     * Clears all fields in the close tab
     * @param event the action event
     */
    @FXML
    void onClearCButtonClick(ActionEvent event) {
        firstNameC.clear();
        lastNameC.clear();
        dobC.getEditor().clear();
        accountType.selectToggle(null);
    }

    /**
     * Clears all fields in the deposit/withdraw tab
     * @param event the action event
     */
    @FXML
    void onClearDWButtonClick(ActionEvent event) {
        firstNameDW.clear();
        lastNameDW.clear();
        dobDW.getEditor().clear();
        amountDW.clear();
        accountType.selectToggle(null);
    }

    /**
     * Clears all fields in the account database tab
     */
    @FXML
    private void clearAcctDbTab(ActionEvent event) {
        acctsOnly.setSelected(false);
        acctsInterestsFees.setSelected(false);
        acctsUpdatedInterestsFees.setSelected(false);
    }

    /**
     * Opens an account if the input is valid
     * @param event the action event
     */
    @FXML
    void onOpenButtonClick(ActionEvent event) {
        Profile profile = getProfile("opening");
        if (profile == null) {
            return;
        }
        double initialDep = 0;
        try {
            initialDep = Double.parseDouble(amountO.getText());
        }
        catch (NumberFormatException e) {
            output.appendText("Not a valid amount.\n");
            return;
        }
        Account acct = makeAccount(profile, initialDep);
        if (acct == null) {
            output.appendText("Missing data for opening an account.\n");
            return;
        }
        if (!isValidCredentials(acct.getAcctSymbol(), profile.getDOB(),
                initialDep)) {
            return;
        }
        if (isValidOpenAcct(acctDb, acct)) {
            acctDb.open(acct);
            output.appendText(profile + "(" + acct.getAcctSymbol()
                    + ") opened.\n");
            onClearOButtonClick(event);
        }
    }

    /**
     * Closes an account if the input is valid
     * @param event the action event
     */
    @FXML
    void onCloseButtonClick(ActionEvent event) {
        Profile profile = getProfile("closing");
        if (profile == null) {
            return;
        }
        Account acct = makeAccount(profile);
        if (acct == null) {
            output.appendText("Missing data for closing an account.\n");
            return;
        }
        if (acctDb.close(acct)) {
            output.appendText(profile + "(" + acct.getAcctSymbol()
                    + ") has been closed.\n");
            onClearCButtonClick(event);
        }
        else {
            output.appendText(profile + "(" + acct.getAcctSymbol()
                    + ") is not in the database.\n");
        }
    }

    /**
     * Deposits the amount into the account if the input is valid
     * @param event the action event
     */
    @FXML
    void onDepositButtonClick(ActionEvent event) {
        Profile profile = getProfile("depositing into");
        if (profile == null) {
            return;
        }
        double deposit = 0;
        try {
            deposit = Double.parseDouble(amountDW.getText());
        }
        catch (NumberFormatException e) {
            output.appendText("Not a valid amount.\n");
            return;
        }
        if (deposit <= EMPTY) {
            output.appendText("Deposit - amount cannot be "
                    + "0 or negative.\n");
            return;
        }
        Account acct = makeAccount(profile, deposit);
        if (acct == null) {
            output.appendText("Missing data for depositing into"
                    + " an account.\n");
            return;
        }
        if (acctDb.contains(acct)) {
            acctDb.deposit(acct);
            output.appendText(profile + "(" + acct.getAcctSymbol() +
                    ") Deposit - balance updated.\n");
            onClearDWButtonClick(event);
        }
        else {
            output.appendText(profile + "(" + acct.getAcctSymbol() +
                    ") is not in the database.\n");
        }
    }

    /**
     * Withdraws the amount from the account if the input is valid
     * @param event the action event
     */
    @FXML
    void onWithdrawButtonClick(ActionEvent event) {
        Profile profile = getProfile("withdrawing from");
        if (profile == null) {
            return;
        }
        double amount = 0;
        try {
            amount = Double.parseDouble(amountDW.getText());
        }
        catch (NumberFormatException e) {
            output.appendText("Not a valid amount.\n");
            return;
        }
        if (amount <= EMPTY) {
            output.appendText("Withdraw - amount cannot be "
                    + "0 or negative.\n");
            return;
        }
        isWithdrawable(profile, amount);
        onClearDWButtonClick(event);
    }

    /**
     * Prints the account database based on the selected option
     * @param event the action event
     */
    @FXML
    void onPrintButtonClick(ActionEvent event) {
        if (acctsOnly.isSelected()) {
            output.appendText(acctDb.printSorted());
        }
        else if (acctsInterestsFees.isSelected()) {
            output.appendText(acctDb.printFeesAndInterests());
        }
        else if (acctsUpdatedInterestsFees.isSelected()) {
            output.appendText(acctDb.printUpdatedBalances());
        }
        clearAcctDbTab(event);
    }

    /**
     * Loads accounts from a file
     * @param event the action event
     * @throws FileNotFoundException if the file is not found
     */
    @FXML
    void onLoadButtonClick(ActionEvent event) throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage);
        if (sourceFile == null) {
            return;
        }
        Scanner scanner = new Scanner(sourceFile);
        while (scanner.hasNextLine()) {
            addLoadedAccount(scanner.nextLine());
        }
        output.appendText("Accounts loaded.\n");
    }

    /**
     * Adds an account to the database based on the input line
     * @param line the input account
     */
    private void addLoadedAccount(String line) {
        String[] tokens = line.split(",");
        String acctType = tokens[0];
        String fName = tokens[1], lName = tokens[2];
        String[] dobCreds = tokens[3].split("/");
        Date dob = null;
        double initialDep = 0;
        Campus campus = null;
        boolean isLoyal = false;
        dob = new Date(Integer.parseInt(dobCreds[2]),
                Integer.parseInt(dobCreds[0]),
                Integer.parseInt(dobCreds[1]));
        initialDep = Double.parseDouble(tokens[4]);
        if (acctType.equals("CC")) {
            campus = getCampus(Integer.parseInt(tokens[5]));
            if (campus == null) {
                output.appendText("Invalid campus code.\n");
                return;
            }
        }
        else if (acctType.equals("S")) {
            isLoyal = tokens[5].equals("1");
        }
        Profile profile = new Profile(fName, lName, dob);
        if (!isValidCredentials(acctType, dob, initialDep)) {
            return;
        }
        Account acct = makeAccount(profile, initialDep, campus, isLoyal, acctType);
        acctDb.open(acct);
    }

    /**
     * Returns the selected campus
     * @return selected campus
     */
    private Campus getCampus() {
        if (newBrunswick.isSelected()) {
            return Campus.NEW_BRUNSWICK;
        }
        else if (newark.isSelected()) {
            return Campus.NEWARK;
        }
        else if (camden.isSelected()) {
            return Campus.CAMDEN;
        }
        return null;
    }

    /**
     * Returns enum campus that corresponds to input campus
     * if there is one
     * @param campus the input campus
     * @return enum campus if it exists, null otherwise
     */
    private Campus getCampus(int campus) {
        for (Campus c: Campus.values()) {
            if (c.ordinal() == campus) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns the account holder's profile
     * @param acctAction the type of account transaction being done
     * @return profile
     */
    private Profile getProfile(String acctAction) {
        String fName = "", lName = "";
        String[] dobCreds = null;
        if (openTab.isSelected()) {
            fName = firstNameO.getText();
            lName = lastNameO.getText();
            dobCreds = dobO.getEditor().getText().split("/");
        }
        else if (closeTab.isSelected()) {
            fName = firstNameC.getText();
            lName = lastNameC.getText();
            dobCreds = dobC.getEditor().getText().split("/");
        }
        else if (dwTab.isSelected()) {
            fName = firstNameDW.getText();
            lName = lastNameDW.getText();
            dobCreds = dobDW.getEditor().getText().split("/");
        }
        if (fName.isEmpty() || lName.isEmpty()
                || dobCreds.length != DOB_CREDENTIALS) {
            output.appendText("Missing data for " + acctAction
                    + " an account.\n");
            return null;
        }
        Date dateOfBirth = new Date(Integer.parseInt(dobCreds[2]),
                Integer.parseInt(dobCreds[0]),
                Integer.parseInt(dobCreds[1]));
        Profile profile = new Profile(fName, lName, dateOfBirth);
        return profile;
    }

    /**
     * Creates an account using the specified profile
     * @param profile account holder's profile
     * @return account
     */
    private Account makeAccount(Profile profile) {
        if (checkingC.isSelected()) {
            return new Checking(profile);
        }
        else if (collegeCheckingC.isSelected()) {
            return new CollegeChecking(profile);
        }
        else if (savingsC.isSelected()) {
            return new Savings(profile);
        }
        else if (moneyMarketC.isSelected()) {
            return new MoneyMarket(profile);
        }
        return null;
    }

    /**
     * Creates an account using the specified attributes
     * @param profile account holder's profile
     * @param deposit initial deposit
     * @return account
     */
    private Account makeAccount(Profile profile, double deposit) {
        if (checkingO.isSelected() || checkingDW.isSelected()) {
            return new Checking(profile, deposit);
        }
        else if (collegeCheckingO.isSelected()
                || collegeCheckingDW.isSelected()) {
            if (collegeCheckingO.isSelected() && getCampus() == null) {
                return null;
            }
            return new CollegeChecking(profile, deposit, getCampus());
        }
        else if (savingsO.isSelected() || savingsDW.isSelected()) {
            return new Savings(profile, deposit, loyalCustomer.isSelected());
        }
        else if (moneyMarketO.isSelected() || moneyMarketDW.isSelected()) {
            return new MoneyMarket(profile, deposit);
        }
        return null;
    }

    /**
     * Creates an account using the specified attributes
     * @param profile account holder's profile
     * @param deposit initial deposit
     * @param campus campus of account holder if college checking
     * @param isLoyal boolean representing whether a
     *                savings account holder is loyal
     * @param acctType account type
     * @return account object
     */
    private Account makeAccount(Profile profile, double deposit, Campus campus,
                             boolean isLoyal, String acctType) {
        switch (acctType) {
            case "C":
                return new Checking(profile, deposit);
            case "CC":
                return new CollegeChecking(profile, deposit, campus);
            case "S":
                return new Savings(profile, deposit, isLoyal);
            case "MM":
                return new MoneyMarket(profile, deposit);
            default:
                return null;
        }
    }
    
    /**
     * Checks whether the input date of birth is valid
     * @param dob date of birth of account holder
     * @return true if dob is valid, false otherwise
     */
    private boolean isValidDob(Date dob) {
        if (!dob.isValid()) {
            output.appendText("DOB invalid: " + dob
                    + " not a valid calendar date!\n");
            return false;
        }
        if (dob.isToday_Or_FutureDate()) {
            output.appendText("DOB invalid: " + dob
                    + " cannot be today or a future day.\n");
            return false;
        }
        return true;
    }

    /**
     * Checks whether if the balance is withdrawn properly and
     * prints a message based on the action done
     * @param profile account holder's profile
     * @param amount withdrawal amount
     */
    private void isWithdrawable(Profile profile, double amount) {
        Account acct = makeAccount(profile, amount);
        if (acct == null) {
            output.appendText("Missing data for withdrawing from"
                    + " an account.\n");
            return;
        }
        if (acctDb.contains(acct)) {
            if (acctDb.withdraw(acct)) {
                output.appendText(profile + "(" + acct.getAcctSymbol() +
                        ") Withdraw - balance updated.\n");
                onClearDWButtonClick(new ActionEvent());
            }
            else {
                output.appendText(profile + "(" + acct.getAcctSymbol() +
                        ") Withdraw - insufficient fund.\n");
            }
        }
        else {
            output.appendText(profile + "(" + acct.getAcctSymbol() +
                    ") is not in the database.\n");
        }
    }

    /**
     * Checks if whether the account to be opened exists
     * and prints so if it does
     * @param acctDb the account database
     * @param acct the account
     * @return true if the account does not exist, false otherwise
     */
    private boolean isValidOpenAcct(AccountDatabase acctDb, Account acct) {
        boolean isOpening = true;
        if (acctDb.contains(acct, isOpening)) {
            output.appendText(acct.getProfile() + "(" + acct.getAcctSymbol()
                    + ") is already in the database.\n");
            return false;
        }
        return true;
    }

    /**
     * Checks whether the input credentials are valid
     * @param acctType account type
     * @param dob date of birth of account holder
     * @param deposit initial deposit
     * @return true if credentials are valid, false otherwise
     */
    private boolean isValidCredentials(String acctType, Date dob,
                                       double deposit) {
        if (!isValidDob(dob)) {
            return false;
        }
        if (dob.getAge() < MIN_AGE) {
            output.appendText("DOB invalid: " + dob
                    + " under 16.\n");
            return false;
        }
        if (deposit <= EMPTY) {
            output.appendText("Initial deposit cannot be "
                    + "0 or negative.\n");
            return false;
        }
        if (acctType.equals("CC") && dob.getAge() >= COLLEGE_MAX_AGE) {
            output.appendText("DOB invalid: " + dob + " over 24.\n");
            return false;
        }
        if (acctType.equals("MM") && deposit < MIN_MONEYMARKET_DEPOSIT) {
            output.appendText("Minimum of $2000 to open a "
                    + "Money Market account.\n");
            return false;
        }
        return true;
    }
}