package com.banking;

/**
 * Represents an organized list of accounts
 * @author Jeeva Ramasamy, Parth Patel
 */
public class AccountDatabase {
    private Account [] accounts; //list of various types of accounts
    private int numAcct; //number of accounts in the array

    private static final int NOT_FOUND = -1;
    private static final int INITIAL_CAPACITY = 4;
    private static final int CAPACITY_INCREASE = 4;
    private static final int EMPTY = 0;

    /**
     * Creates an empty list of accounts with an initial capacity of 4
     */
    public AccountDatabase() {
        accounts = new Account[INITIAL_CAPACITY];
        numAcct = EMPTY;
    }

    /**
     * Search for an account in the array
     * @param account account to be searched
     * @return index of where the account if it is located, -1 otherwise
     */
    private int find(Account account) {
        for (int i = 0; i < numAcct; ++i) {
            if (accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases size by database by 4
     */
    private void grow() {
        Account[] increasedAccounts = new Account[numAcct + CAPACITY_INCREASE];
        for (int i = 0; i < numAcct; ++i) {
            increasedAccounts[i] = accounts[i];
        }
        accounts = increasedAccounts;
    }

    /**
     * Checks whether is account is in the database
     * @param account account to be searched
     * @return true if in database, false otherwise
     */
    public boolean contains(Account account) {
        for (int i = 0; i < numAcct; ++i) {
            if (accounts[i].equals(account)) {
                if (accounts[i].getAcctType().equals(account.getAcctType())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether is account is in the database
     * and considers Checking equal to CollegeChecking
     * @param account account to be searched
     * @param isOpening true if account is being opened, false otherwise
     * @return true if in database, false otherwise
     */
    public boolean contains(Account account, boolean isOpening) {
        if (isOpening) {
            for (int i = 0; i < numAcct; ++i) {
                if (accounts[i].equals(account)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Add a new account to the database
     * @param account account to be added
     * @return true if successfully added, false otherwise
     */
    public boolean open(Account account) {
        if (numAcct == accounts.length) {
            grow();
        }
        accounts[numAcct] = account;
        ++numAcct;
        return true;
    }

    /**
     * Remove the given account from the database
     * @param account account to be closed
     * @return true if successfully closed, false otherwise
     */
    public boolean close(Account account) {
        if (numAcct == EMPTY) {
            return false;
        }

        int acctIndex = find(account);
        if (acctIndex == NOT_FOUND) {
            return false;
        }
        if (!accounts[acctIndex].getAcctType().equals(account.getAcctType())) {
            return false;
        }

        for (int i = acctIndex; i < numAcct - 1; ++i) {
            accounts[i] = accounts[i + 1];
        }

        --numAcct;
        accounts[numAcct] = null;

        return true;
    }

    /**
     * Withdraws the amount from the account
     * @param account account money is withdrawn from
     * @return false if insufficient fund, true otherwise
     */
    public boolean withdraw(Account account) {
        int acctIndex = find(account);
        if (accounts[acctIndex].getBalance() >= account.getBalance()) {
            accounts[acctIndex].subBalance(account.getBalance());
            if (accounts[acctIndex] instanceof MoneyMarket) {
                MoneyMarket acct = (MoneyMarket) accounts[acctIndex];
                acct.addWithdrawal();
                acct.updateLoyalty();
            }
            return true;
        }
        return false;
    }

    /**
     * Deposits amount into the account
     * @param account account money is deposited to
     */
    public void deposit(Account account) {
        int acctIndex = find(account);
        accounts[acctIndex].addBalance(account.getBalance());
        if (accounts[acctIndex] instanceof MoneyMarket) {
            MoneyMarket acct = (MoneyMarket) accounts[acctIndex];
            acct.updateLoyalty();
        }
    }

    /**
     * Gets the sorted accounts
     * @return a string of sorted accounts
     */
    public String printSorted() {
        if (numAcct == EMPTY) {
            return "banking.Account Database is empty!\n";
        }

        String output = "*Accounts sorted by account type and profile.\n";
        sortAccounts();
        for (int i = 0; i < numAcct; ++i) {
            output += accounts[i] + "\n";
        }
        output += "*end of list.\n";
        return output;
    }

    /**
     * Gets the accounts along with interests/fees
     * @return a string of accounts along with interests/fees
     */
    public String printFeesAndInterests() {
        if (numAcct == EMPTY) {
            return "banking.Account Database is empty!\n";
        }

        String output = "*list of accounts with fee and monthly interest\n";
        sortAccounts();
        for (int i = 0; i < numAcct; ++i) {
            output += accounts[i]
                    + accounts[i].getFeesAndInterests() + "\n";
        }
        output += "*end of list.\n";
        return output;
    }

    /**
     * Gets the accounts after applying the interests/fees
     * @return a string of accounts after applying the interests/fees
     */
    public String printUpdatedBalances(){
        if (numAcct == EMPTY) {
           return "banking.Account Database is empty!\n";
        }

        String output = "*list of accounts with fees"
                + " and interests applied.\n";
        sortAccounts();

        for (int i = 0; i < numAcct; ++i) {
            accounts[i].addBalance(accounts[i].monthlyInterest()
                    * accounts[i].getBalance());
            accounts[i].subBalance(accounts[i].monthlyFee());
            if (accounts[i] instanceof MoneyMarket) {
                MoneyMarket acct = (MoneyMarket) accounts[i];
                acct.clearWithdrawals();
            }
        }

        for (int i = 0; i < numAcct; ++i) {
            output += accounts[i] + "\n";
        }
        output += "*end of list.\n";
        return output;
    }

    /**
     * Sorts the accounts by account type and profile
     */
    private void sortAccounts() {
        for (int i = 1; i < numAcct; ++i) {
            Account key = accounts[i];
            int j = i - 1;
            while (j >= 0 && accounts[j].compareTo(key) > 0) {
                accounts[j + 1] = accounts[j];
                --j;
            }
            accounts[j + 1] = key;
        }
    }

}