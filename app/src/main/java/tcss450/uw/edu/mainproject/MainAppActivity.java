package tcss450.uw.edu.mainproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import tcss450.uw.edu.mainproject.authenticate.MainLoginActivity;
import tcss450.uw.edu.mainproject.model.User;

public class MainAppActivity extends AppCompatActivity implements FollowListFragment.OnListFragmentInteractionListener,
        AskerListFragment.OnListFragmentInteractionListener {
    private AskerListFragment mAskerListFragment;
    private FollowListFragment mFollowListFragment;
    private Button mFollowButton;
    private Button mAskerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        mFollowButton = (Button) findViewById(R.id.followers_button);
        mAskerButton = (Button) findViewById(R.id.askers_button);
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
