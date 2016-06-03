/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import tcss450.uw.edu.mainproject.model.User;

/**
 * Junit tests on the user class
 */
public class UserTest extends TestCase {

    /**
     * tests the constructor
     * */
    @Test
    public void testConstructor() {
        User user = new User("mehgan", "mehganc@uw.edu",
                "awesome", "1");
        assertNotNull(user);
    }

    /**
     * Tests the parse user json
     *
     * */
    @Test
    public void testParseUserJSON() {
        String userJSON = "[{\"username\":\"mehganc\",\"email\":\"mehganc@uw.edu\",\"password\":\"awesome\",\"userid\":\"1\"}" +
                ",{\"username\":\"tony\",\"email\":\"anromeo@uw.edu\",\"password\":\"awesome\",\"userid\":\"2\"}]";
        String message =  User.parseUserJSON(userJSON
                , new ArrayList<User>());
        assertTrue("JSON With Valid String", message == null);
    }
    /**User used for testing*/
    private User mUser;

    /**
     * Sets up the test conditions
     * */
    @Before
    public void setUp() {
        mUser = new User("mehgan", "mehgancc@uw.edu", "awesome", "1");
    }

    /**
     * Test for getting the user email
     * */
    @Test
    public void testGetUserEmail() {
        assertEquals("mehgancc@uw.edu", mUser.getEmail());
    }

    /**
     * Test for getting the username
     * */
    @Test
    public void testGetUsername() {
        assertEquals("mehgan", mUser.getUsername());
    }

    /**
     * test for getting the user password
     * */
    @Test
    public void testGetPassword() {
        assertEquals("awesome", mUser.getPassword());
    }

    /**
     * test for getting the user id
     * */
    @Test
    public void testGetUserID() {
        assertEquals(1, mUser.getUserID());
    }
}
