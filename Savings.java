package com.banking;

/**
 * Represents a Savings account with a holder,
 * balance, and loyalty
 * @author Jeeva Ramasamy, Parth Patel
 */
public class Savings extends Account {
    protected boolean isLoyal; //loyal customer status
    private static final double INTEREST_RATE = 0.04;
    private static final double MONTHLY_FEE = 25;
    private static final double NO_FEE = 0;
    private static final double MIN_BAL_FOR_BENEFITS = 500;
    private static final double LOYAL_EXTRA_RATE = 0.0025;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private static final String ACCT_TYPE = "Savings";
    private static final String ACCT_SYMBOL = "S";

    /**
     * Creates a Savings account object with the specified
     * holder, balance, and loyalty
     * @param holder account holder's profile
     * @param balance account balance
     * @param isLoyal whether the account holder is loyal
     */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Creates a Savings account object with the specified
     * holder and balance
     * @param holder account holder's profile
     * @param balance account balance
     */
    public Savings(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Creates a Savings account object with the specified holder
     * @param holder account holder's profile
     */
    public Savings(Profile holder) {
        super(holder);
    }

    /**
     * Returns the monthly interest for the savings account
     * @return monthly interest
     */
    @Override
    public double monthlyInterest() {
        double interestRate = INTEREST_RATE;
        if (this.isLoyal) {
            interestRate += LOYAL_EXTRA_RATE;
        }
        return interestRate / MONTHS_IN_YEAR;
    }

    /**
     * Returns the monthly fee for the savings account
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        if (this.balance >= MIN_BAL_FOR_BENEFITS) {
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
     * Checks if this Savings object is equal to the specified object
     * @param obj specified object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Savings) {
            Savings acct = (Savings) obj;
            if (!(acct instanceof MoneyMarket)) {
                return this.holder.compareTo(acct.holder) == 0;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the Savings account
     * in the format: banking.Savings::Account(::is loyal)
     * @return string version of savings account
     */
    @Override
    public String toString() {
        String output = "banking." + ACCT_TYPE + "::" + super.toString();
        output += this.isLoyal ? "::is loyal" : "";
        return output;
    }
}
