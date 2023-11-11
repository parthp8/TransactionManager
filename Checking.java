package com.banking;

/**
 * Represents a checking account with a holder and balance
 * @author Jeeva Ramasamy, Parth Patel
 */
public class Checking extends Account {
    private static final double INTEREST_RATE = 0.01;
    private static final double MONTHLY_FEE = 12;
    private static final double NO_FEE = 0;
    private static final double MIN_BAL_TO_WAIVE_FEE = 1000;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private static final String ACCT_TYPE = "Checking";
    private static final String ACCT_SYMBOL = "C";

    /**
     * Creates a Checking account object with the specified
     * holder and balance
     * @param holder account holder's profile
     * @param balance account balance
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Creates a Checking account object with the specified holder
     * @param holder account holder's profile
     */
    public Checking(Profile holder) {
        super(holder);
    }

    /**
     * Returns the monthly interest for the checking account
     * @return monthly interest
     */
    @Override
    public double monthlyInterest() {
        return INTEREST_RATE / MONTHS_IN_YEAR;
    }

    /**
     * Returns the monthly fee for the checking account
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        if (this.balance >= MIN_BAL_TO_WAIVE_FEE) {
            return NO_FEE;
        }
        return MONTHLY_FEE;
    }

    /**
     * Returns the account type
     * @return account type
     */
    @Override
    public String getAcctType() {
        return ACCT_TYPE;
    }

    /**
     * Returns the account symbol
     * @return account symbol
     */
    @Override
    public String getAcctSymbol() {
        return ACCT_SYMBOL;
    }

    /**
     * Checks if this Checking object is equal to the specified object
     * @param obj specified object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Checking) {
            Checking acct = (Checking) obj;
            return this.holder.compareTo(acct.holder) == 0;
        }
        return false;
    }

    /**
     * Compares this Account object with the specified object for order
     * @param acct the object to be compared.
     * @return 1 if this object is greater than the specified object,
     *         -1 if this object is less than the specified object,
     *         0 if this object is equal to the specified object
     */
    @Override
    public int compareTo(Account acct) {
        if (this.getAcctType().compareTo(acct.getAcctType()) > 0) {
            return GREATER_THAN;
        }
        else if (this.getAcctType().compareTo(acct.getAcctType()) < 0) {
            return LESS_THAN;
        }
        else {
            return this.holder.compareTo(acct.holder);
        }
    }

    /**
     * Returns a string representation of the Checking account
     * in the format: banking.Checking::Account
     * @return string version of checking account
     */
    @Override
    public String toString() {
        return "banking."+ ACCT_TYPE + "::" + super.toString();
    }
}
