package tcss450.uw.edu.mainproject;

import android.util.Log;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import tcss450.uw.edu.mainproject.model.User;

/**
 * Created by Mehgan on 5/22/2016.
 */
public class UserTest extends TestCase {

    @Test
    public void testConstructor() {
        User user = new User("mehgan", "mehganc@uw.edu",
                "awesome", "1");
        assertNotNull(user);
    }

    @Test
    public void testParseUserJSON() {
        String userJSON = "[{\"username\":\"mehganc\",\"email\":\"mehganc@uw.edu\",\"password\":\"awesome\",\"userid\":\"1\"}" +
                ",{\"username\":\"tony\",\"email\":\"anromeo@uw.edu\",\"password\":\"awesome\",\"userid\":\"2\"}]";
        String message =  User.parseUserJSON(userJSON
                , new ArrayList<User>());
        assertTrue("JSON With Valid String", message == null);
    }

    private User mUser;

    @Before
    public void setUp() {
        mUser = new User("mehgan", "mehgancc@uw.edu", "awesome", "1");
    }

    @Test
    public void testGetUserEmail() {
        assertEquals("mehgancc@uw.edu", mUser.getEmail());
    }

    @Test
    public void testGetUsername() {
        assertEquals("mehgan", mUser.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("awesome", mUser.getPassword());
    }

    @Test
    public void testGetUserID() {
        assertEquals(1, mUser.getUserID());
    }





}
