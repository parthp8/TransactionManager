package com.banking;

/**
 * Represents predefined campus locations for a College Checking Account
 * @author Jeeva Ramasamy, Parth Patel
 */
public enum Campus {
    NEW_BRUNSWICK (0),
    NEWARK (1),
    CAMDEN (2);

    private final int campusCode;

    /**
     * Creates a predefined campus with campus code
     * @param campusCode campus code
     */
    Campus(int campusCode) {
        this.campusCode = campusCode;
    }
}
