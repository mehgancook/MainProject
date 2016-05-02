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

public class MainAppActivity extends AppCompatActivity implements FollowListFragment.OnListFragmentInteractionListener,
        AskerListFragment.OnListFragmentInteractionListener {
    private AskerListFragment mAskerListFragment;
    private FollowListFragment mFollowListFragment;
    private TextView mFollowButton;
    private TextView mAskerButton;
    private Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                    .commit();

            Intent i = new Intent(this, MainLoginActivity.class);
            startActivity(i);
            finish();
            return true;

        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(User item) {

        UserDetailsFragment courseDetailFragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(UserDetailsFragment.USER_ITEM_SELECTED, item);
        courseDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, courseDetailFragment)
                .addToBackStack(null)
                .commit();
    }

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

//        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
        if (mAskerListFragment != null) {
            mFollowListFragment = new FollowListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mFollowListFragment)
                    .remove(mAskerListFragment)
                    .commit();
            //      }
        } else {
            mFollowListFragment = new FollowListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mFollowListFragment)
                    .commit();
        }
    }
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

//        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
        mAskerListFragment = new AskerListFragment();
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
        //      }
    }


}
