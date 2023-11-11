package banking;

import static org.junit.Assert.*;

/**
 * Tests the AccountDatabase class
 * @author Jeeva Ramasamy, Parth Patel
 */
public class AccountDatabaseTest {

    /**
     * Test case #1
     * Tests if an account existing in the database is successfully removed
     */
    @org.junit.Test
    public void test_SuccessfulRemoval() {
        AccountDatabase database = new AccountDatabase();
        Profile profile = new Profile("Bill", "Gates", new Date(1976, 1, 1));
        Account account = new Checking(profile, 100);
        database.open(account);
        assertTrue(database.close(account));
    }

    /**
     * Test case #2
     * Tests if an account not in the database is not removed
     */
    @org.junit.Test
    public void test_NotInDatabase() {
        AccountDatabase database = new AccountDatabase();
        Profile profile = new Profile("Bill", "Gates", new Date(1976, 1, 1));
        Account account = new Checking(profile, 100);
        assertFalse(database.close(account));
    }
}