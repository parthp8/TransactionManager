package com.banking;

import java.text.DecimalFormat;

/**
 * Represents a bank account with a holder and balance
 * @author Jeeva Ramasamy, Parth Patel
 */
public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;
    public abstract double monthlyInterest();
    public abstract double monthlyFee();
    public abstract String getAcctType();
    public abstract String getAcctSymbol();

    /**
     * Represents an Account object with the specified holder and balance
     * @param holder account holder's profile
     * @param balance account balance
     */
    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * Represents an Account object with the specified holder
     * @param holder account holder's profile
     */
    public Account(Profile holder) {
        this.holder = holder;
    }

    /**
     * Returns the account holder's profile
     * @return profile
     */
    public Profile getProfile() {
        return holder;
    }

    /**
     * Returns the account balance
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Adds funds to the account balance
     * @param amount deposit amount
     */
    public void addBalance(double amount) {
        this.balance += amount;
    }

    /**
     * Removes funds from the account balance
     * @param amount withdrawal amount
     */
    public void subBalance(double amount) {
        this.balance -= amount;
    }

    /**
     * Returns a string representation of the fees and interests
     * @return string version of fees and interests
     */
    public String getFeesAndInterests() {
        DecimalFormat money = new DecimalFormat("$#,##0.00");
        return "::fee " + money.format(monthlyFee())
                + "::monthly interest "
                + money.format(balance * monthlyInterest());
    }

    /**
     * Checks if this Account is equal to the specified object
     * @param obj specified object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account acct = (Account) obj;
            return this.holder.compareTo(acct.holder) == 0;
        }
        return false;
    }

    /**
     * Returns a string representation of the Account
     * in the format: holder::Balance $balance
     * @return string version of account
     */
    @Override
    public String toString() {
        DecimalFormat money = new DecimalFormat("$#,###,##0.00");
        return holder + "::Balance " + money.format(balance);
    }
}