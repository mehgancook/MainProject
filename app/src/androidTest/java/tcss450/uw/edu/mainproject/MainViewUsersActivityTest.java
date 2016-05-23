package tcss450.uw.edu.mainproject;

import android.test.ActivityInstrumentationTestCase2;

import tcss450.uw.edu.mainproject.authenticate.LoginUserActivity;
import tcss450.uw.edu.mainproject.followers_askers_groups.MainViewUsersActivity;

import com.robotium.solo.Solo;
/**
 * Created by Mehgan on 5/22/2016.
 */
public class MainViewUsersActivityTest extends
        ActivityInstrumentationTestCase2<MainViewUsersActivity> {

    private Solo solo;

    public MainViewUsersActivityTest() {
        super(MainViewUsersActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();

    }

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
    public void testUserDetail() {
        solo.clickOnText("Followers");
        solo.clickInRecyclerView(0);
        boolean foundUserDetail = solo.searchText("anromeo@uw.edu");
        assertTrue("User Detail fragment loaded", foundUserDetail);
        solo.goBack();
        boolean foundFollowersList = solo.searchText("mehgan");
        assertTrue("Back to List works!", foundFollowersList);
    }

    public void testFollowersList() {
        solo.clickOnText("Followers");
        boolean fragmentLoaded = solo.searchText("mehgan");
        assertTrue("FollowersList fragment loaded", fragmentLoaded);
    }

    public void testAskersList() {
        solo.clickOnText("Askers");
        boolean fragmentLoaded = solo.searchText("tony");
        assertTrue("AskersList fragment loaded", fragmentLoaded);
    }
}
