package com.banking;

/**
 * Represents a Money Market account with a holder, balance, and withdrawal
 * @author Jeeva Ramasamy, Parth Patel
 */
public class MoneyMarket extends Savings {
    private int withdrawal; //number of withdrawal;
    private static final double INTEREST_RATE = 0.045;
    private static final double MONTHLY_FEE = 25;
    private static final double NO_FEE = 0;
    private static final double MIN_BAL_FOR_BENEFITS = 2000;
    private static final double LOYAL_EXTRA_RATE = 0.0025;
    private static final int WITHDRAWAL_FEE = 10;
    private static final int NONE = 0;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private static final String ACCT_TYPE = "Money Market";
    private static final String ACCT_SYMBOL = "MM";

    /**
     * Creates a Money Market account object with the specified
     * holder and balance
     * @param holder account holder's profile
     * @param balance account balance
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, true);
        this.withdrawal = NONE;
    }

    /**
     * Creates a Money Market account object with the specified holder
     * @param holder account holder's profile
     */
    public MoneyMarket(Profile holder) {
        super(holder);
    }

    /**
     * Returns the monthly interest for the money market account
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
     * Returns the monthly fee for the money market account
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        double fee = MONTHLY_FEE;
        if (this.balance >= MIN_BAL_FOR_BENEFITS) {
            fee = NO_FEE;
        }
        if (this.withdrawal > 3) {
            fee += WITHDRAWAL_FEE;
        }
        return fee;
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
     * Increments for each time money is withdrawn
     */
    public void addWithdrawal() {
        ++this.withdrawal;
    }

    /**
     * Clears withdrawal count
     */
    public void clearWithdrawals() {
        this.withdrawal = NONE;
    }

    /**
     * Updates loyalty of account holder based on balance
     */
    public void updateLoyalty() {
        if (this.balance < MIN_BAL_FOR_BENEFITS) {
            this.isLoyal = false;
        }
        else {
            this.isLoyal = true;
        }
    }

    /**
     * Checks if this MoneyMarket object is equal to the specified object
     * @param obj specified object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MoneyMarket) {
            MoneyMarket acct = (MoneyMarket) obj;
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
     * Returns a string representation of the Money Market account
     * in the format: Money Market::Savings_account::withdrawal: numOfWithdrawals
     * @return string version of money market account
     */
    @Override
    public String toString() {
        return ACCT_TYPE + "::" + super.toString() + "::withdrawal: "
                + this.withdrawal;
    }
}
