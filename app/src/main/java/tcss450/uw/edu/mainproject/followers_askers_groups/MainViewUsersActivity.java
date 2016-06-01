/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.followers_askers_groups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import tcss450.uw.edu.mainproject.Helper;
import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.account.ProfileActivity;
import tcss450.uw.edu.mainproject.authenticate.MainLoginActivity;
import tcss450.uw.edu.mainproject.blast_question.BlastQuestionActivity;
import tcss450.uw.edu.mainproject.model.User;
import tcss450.uw.edu.mainproject.voting_reviewing_questions.VotingActivity;


/**
 * This class is the main fragment container that will allow the app user to view their followers
 * and their askers
 *
 * */
public class MainViewUsersActivity extends AppCompatActivity implements FollowListFragment.OnListFragmentInteractionListener,
        AskerListFragment.OnListFragmentInteractionListener {
    /**asker list fragment*/
    private AskerListFragment mAskerListFragment;
    /**follower list fragment*/
    private FollowListFragment mFollowListFragment;
    /**the followers button*/
    private TextView mFollowButton;
    /**The askers button*/
    private TextView mAskerButton;
    /**helper class for styles*/
    private Helper mHelper;
    /** Add Asker Button */
    private TextView mAddAskerButton;
    /** View Group Button */
    private TextView mViewGroupButton;

    /**
     * onCreate
     * @param savedInstanceState the saved instance
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_users);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.logo_adjusted));
            setTitle("");
        }
        mHelper = new Helper(getAssets());

        mFollowButton = (TextView) findViewById(R.id.followers_button);
        mAskerButton = (TextView) findViewById(R.id.askers_button);
        mAddAskerButton = (TextView) findViewById(R.id.add_asker);
        mViewGroupButton = (TextView) findViewById(R.id.view_group);

        mHelper.setFontStyle(mFollowButton);
        mHelper.setFontStyle(mAskerButton);
        mHelper.setFontStyle(mAddAskerButton);
        mHelper.setFontStyle(mViewGroupButton);

        mHelper.setFontStyle(mFollowButton);
        mHelper.setFontStyle(mAskerButton);
        mHelper.setFontStyle((TextView) findViewById(R.id.add_asker));

        Toolbar tools = (Toolbar) findViewById(R.id.toolbar);
        tools.setCollapsible(false);

        toFollowers(mFollowButton);
    }

    /**
     * On create options menu
     * @param menu the menu
     * @return a bolean
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**
     * on options item selected
     * @param item menu item
     * @return boolean
     * */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //will log the user out
        if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                    .commit();
            //will return to the main login activity
            Intent i = new Intent(this, MainLoginActivity.class);
            startActivity(i);
            finish();
            return true;

        }
        if (id == R.id.action_email) {

        }
        return super.onOptionsItemSelected(item);
    }
    /**The list fragment ineraction will open the users detail fragment if
     * a user in either list is clicked on
     * @param user is the user being clicked on */
    @Override
    public void onListFragmentInteraction(User user) {

        UserDetailsFragment courseDetailFragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(UserDetailsFragment.USER_ITEM_SELECTED, user);
        courseDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, courseDetailFragment)
                .addToBackStack(null)
                .commit();
    }
    /**
     * toFollowers will open the fragment that contains the current user followers list
     * @param v the view
     *
     * */
    public void toFollowers(View v) {
        int selectedColor = getResources().getColor(R.color.colorPrimary);
        int white = getResources().getColor(R.color.white);
        Drawable iconBox;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            iconBox = getResources().getDrawable(R.drawable.icon_border, getTheme());
            mAskerButton.setBackground(iconBox);

        } else {
            mAskerButton.setBackgroundColor(white);
        }
        mFollowButton.setBackgroundColor(selectedColor);
        mAddAskerButton.setVisibility(View.GONE);
        mViewGroupButton.setVisibility(View.VISIBLE);

        //if the asker list has been clicked on and is not null, then remove the asker list
        //and replace with the followers list
        if (mAskerListFragment != null) {
            mFollowListFragment = new FollowListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mFollowListFragment)
                    .remove(mAskerListFragment)
                    .commit();
        } else {
            mFollowListFragment = new FollowListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mFollowListFragment)
                    .commit();
        }
    }
    /**
     * toAskers will show the user their askers
     * @param v the view
     * */
    public void toAskers(View v) {
        ((TextView) findViewById(R.id.add_asker)).setVisibility(View.VISIBLE);
        int selectedColor = getResources().getColor(R.color.colorPrimary);
        int white = getResources().getColor(R.color.white);
        Drawable iconBox;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            iconBox = getResources().getDrawable(R.drawable.icon_border, getTheme());
            mFollowButton.setBackground(iconBox);
        } else {
            mFollowButton.setBackgroundColor(white);
        }
        mAskerButton.setBackgroundColor(selectedColor);
        mAddAskerButton.setVisibility(View.VISIBLE);
        mViewGroupButton.setVisibility(View.GONE);

        mAskerListFragment = new AskerListFragment();
        /*if the followers list has been clicked on, then remove it from the screen
         * and replace with the askers fragment */
        if (mFollowListFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mAskerListFragment)
                    .remove(mFollowListFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mAskerListFragment)
                    .commit();
        }
    }

    // Go to Add Askers
    public void goToAddAskersActivity(View v) { startActivity(new Intent(this, AddAskersActivity.class)); }

    // Go to Groups
    public void goToGroups(View v) { startActivity(new Intent(this, ViewGroups.class)); }

    // Start Navigation Methods

    // Go to Blast Question
    public void goToBlastQuestion(View v) { startActivity(new Intent(this, BlastQuestionActivity.class));}
    // Go to Home
    public void goToHome(View v) { startActivity(new Intent(this, VotingActivity.class)); }
    // Go to Followers
    public void goToFollowers(View v) { startActivity(new Intent(this, MainViewUsersActivity.class)); }
    // Go To Settings TODO : Change to Settings.class
    public void goToSettings(View v) { startActivity(new Intent(this, ProfileActivity.class)); }

    // End Navigation Methods

}
