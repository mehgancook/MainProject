/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.followers_askers_groups;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import tcss450.uw.edu.mainproject.Helper;
import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.model.User;

/**
 * This class is the main fragment container that will allow the app user to view their followers
 * and their askers
 *
 * */
public class AddAskersActivity extends AppCompatActivity implements AskerListFragment.OnListFragmentInteractionListener  {

    /**asker list fragment*/
    private AskerListFragment mAskerListFragment;
    /**follower list fragment*/
    private FollowListFragment mFollowListFragment;
    /** helper */
    private Helper mHelper;

    /**
     * onCreate
     * @param savedInstanceState the saved instance
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_askers);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.logo_adjusted));
            setTitle("");
        }

        Toolbar tools = (Toolbar) findViewById(R.id.toolbar);
        tools.setCollapsible(false);

        AskerListFragment list = new AskerListFragment();
        Bundle args = new Bundle();
        args.putSerializable("VIEWALL", true);
        list.setArguments(args);

        mHelper = new Helper(getAssets());
        mHelper.setFontStyle((TextView) findViewById(R.id.title));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, list)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(User user) {
        int userid = user.getUserID();
        String url = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=";

        String username = user.getUsername();
        SpecialAsyncTask task = new SpecialAsyncTask();
        task.prepToast("Added " + username, getApplicationContext());
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        int myUserid = sharedPreferences.getInt(getString(R.string.USERID), -1);

        String insertStatement = "INSERT INTO Askers VALUES (" + myUserid +", " + userid + ");";
        System.out.println(insertStatement);
        try {
            url += URLEncoder.encode(insertStatement, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        task.execute(url);
    }
}
