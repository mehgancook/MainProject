/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject;

import android.test.ActivityInstrumentationTestCase2;

import tcss450.uw.edu.mainproject.authenticate.LoginUserActivity;
import tcss450.uw.edu.mainproject.followers_askers_groups.MainViewUsersActivity;

import com.robotium.solo.Solo;
/**
 * Automatic testing on the main view users activity
 */
public class MainViewUsersActivityTest extends
        ActivityInstrumentationTestCase2<MainViewUsersActivity> {

    /**solo*/
    private Solo solo;

    /**
     * Constructor for test
     * */
    public MainViewUsersActivityTest() {
        super(MainViewUsersActivity.class);
    }

    /**
     * Sets up the test feilds
     * */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Tears down the test feilds
     * */
    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();

    }

    /**
     * tests the log out functionality
     * */
    public void testALogout() {
        solo.clickOnView(getActivity().findViewById(R.id.action_logout));
        boolean textFound = solo.searchText("Login");
        assertTrue("Main login page loaded!", textFound);
        solo.clickOnText("Login");
        solo.enterText(0, "mehganc@uw.edu");
        solo.enterText(1, "awesome");
        solo.clickOnButton("Sign In");
        boolean worked = solo.searchText("Followers");
        assertTrue("Sign in worked!", worked);

    }
    /**
     * Test the interaction when a user is clicked on
     * */
    public void testUserDetail() {
        solo.clickOnText("Followers");
        solo.clickInRecyclerView(0);
        boolean foundUserDetail = solo.searchText("anromeo@uw.edu");
        assertTrue("User Detail fragment loaded", foundUserDetail);
        solo.goBack();
        boolean foundFollowersList = solo.searchText("mehgan");
        assertTrue("Back to List works!", foundFollowersList);
    }
    /**
     * Test that the followers list loads
     * */
    public void testFollowersList() {
        solo.clickOnText("Followers");
        boolean fragmentLoaded = solo.searchText("mehgan");
        assertTrue("FollowersList fragment loaded", fragmentLoaded);
    }

    /**
     * tests that the askers list loads
     * */
    public void testAskersList() {
        solo.clickOnText("Askers");
        boolean fragmentLoaded = solo.searchText("tony");
        assertTrue("AskersList fragment loaded", fragmentLoaded);
    }
}
