package com.banking;

/**
 * Represents a client's profile
 * @author Jeeva Ramasamy, Parth Patel
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;

    /**
     * Creates a Profile object with the first name, last name,
     * and date of birth
     * @param fname the first name of the client
     * @param lname the last name of the client
     * @param dob the date of birth of the client
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Returns the profile's date of birth
     * @return
     */
    public Date getDOB(){
        return dob;
    }

    /**
     * Compares this profile with the specified profile in the order of
     * last name, first name, and date of birth
     * @param profile the profile to be compared
     * @return 1 if this profile is ordered after the specified profile,
     *         -1 if this profile is ordered before the specified profile,
     *         0 if the profiles are the same
     */
    @Override
    public int compareTo(Profile profile) {
        if (this.lname.compareToIgnoreCase(profile.lname) > 0) {
            return GREATER_THAN;
        }
        else if (this.lname.compareToIgnoreCase(profile.lname) < 0) {
            return LESS_THAN;
        }
        else {
            if (this.fname.compareToIgnoreCase(profile.fname) > 0) {
                return GREATER_THAN;
            }
            else if (this.fname.compareToIgnoreCase(profile.fname) < 0) {
                return LESS_THAN;
            }
            else {
                return this.dob.compareTo(profile.dob);
            }
        }
    }

    /**
     * Returns a string representation of profile in the order:
     * first name, last name, date of birth
     * @return string version of profile
     */
    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + this.dob;
    }
}