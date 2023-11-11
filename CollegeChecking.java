package com.banking;

/**
 * Represents a College Checking account with a holder, balance, and campus
 * @author Jeeva Ramasamy, Parth Patel
 */
public class CollegeChecking extends Checking {
    private Campus campus; //campus code
    private static final double INTEREST_RATE = 0.01;
    private static final double MONTHLY_FEE = 0;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private static final String ACCT_TYPE = "College";
    private static final String ACCT_SYMBOL = "CC";

    /**
     * Creates a College Checking account object with the specified
     * holder, balance, and campus
     * @param holder account holder's profile
     * @param balance account balance
     * @param campus account holder's campus
     */
    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
    }

    /**
     * Creates a College Checking account object with the specified
     * holder and balance
     * @param holder account holder's profile
     * @param balance account balance
     */
    public CollegeChecking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Creates a College Checking account object with the specified holder
     * @param holder account holder's profile
     */
    public CollegeChecking(Profile holder) {
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
     * Returns the monthly fee for the college checking account
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
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
     * Checks if this College Checking object is equal to the
     * specified object
     * @param obj specified object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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
     * Returns a string representation of the College Checking account
     * in the format: College Checking_account::campus
     * @return string version of college checking account
     */
    @Override
    public String toString() {
        return ACCT_TYPE + " " + super.toString() + "::" + campus.name();
    }
}
