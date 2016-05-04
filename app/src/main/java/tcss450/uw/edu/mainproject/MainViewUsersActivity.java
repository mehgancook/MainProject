/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import tcss450.uw.edu.mainproject.authenticate.MainLoginActivity;
import tcss450.uw.edu.mainproject.model.User;


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
        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");

        mFollowButton = (TextView) findViewById(R.id.followers_button);
        mFollowButton.setTypeface(oswald);

        mAskerButton = (TextView) findViewById(R.id.askers_button);
        mAskerButton.setTypeface(oswald);

        Toolbar tools = (Toolbar) findViewById(R.id.toolbar);
        tools.setCollapsible(false);
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
        return  super.onOptionsItemSelected(item);
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
     * toFollowers will open the fragment that contains the current usesr followers list
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


}
