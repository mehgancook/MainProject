/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.followers_askers_groups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.account.ProfileActivity;
import tcss450.uw.edu.mainproject.blast_question.BlastQuestionActivity;
import tcss450.uw.edu.mainproject.model.Group;
import tcss450.uw.edu.mainproject.voting_reviewing_questions.VotingActivity;

/**
 * View groups allows a user to view the current groups
 *
 * */
public class ViewGroups extends AppCompatActivity implements
    GroupListFragment.OnListFragmentInteractionListener {

    /**
     * Group list fragment that will be placed on the activity.
     * */
    private GroupListFragment mGroupListFragment;
    /**
     * onCreate
     * @param savedInstanceState the saved instance
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.logo_adjusted));
            setTitle("");
        }

        mGroupListFragment = new GroupListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mGroupListFragment)
                .commit();

    }

    /**
     * onListFragmentInteraction allows the user to see the details about a group
     * @param group the group to view details
     * */
    @Override
    public void onListFragmentInteraction(Group group) {
        GroupListFragment groupDetailFragment = new GroupListFragment();
        Bundle args = new Bundle();
        args.putSerializable(GroupListFragment.GROUP_ITEM_SELECTED, group);
        groupDetailFragment.setArguments(args);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, groupDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    // Start Navigation Methods

    /**
     * add group adds a group
     * @param v the view
     * */
    public void addGroup(View v) {
        String groupName = ((EditText) findViewById(R.id.newGroupName)).getText().toString();

        if (groupName.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please, enter a name.", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        SpecialAsyncTask task = new SpecialAsyncTask();
        task.prepToast("Added " + groupName, getApplicationContext());

        StringBuilder sb = new StringBuilder("http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=");
        try {
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
            int myUserid = sharedPreferences.getInt(getString(R.string.USERID), -1);

            String insertString = "INSERT INTO `Group` VALUES ('', '" + groupName + "', '" + myUserid + "');";
            System.out.print(insertString);
            sb.append(URLEncoder.encode(insertString, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        task.execute(sb.toString());
        Intent intent = new Intent();
        intent.setClass(this, this.getClass());
        this.startActivity(intent);
        this.finish();

    }

    /** Go to Blast Question activity
     * @param v the view*/
    public void goToBlastQuestion(View v) { startActivity(new Intent(this, BlastQuestionActivity.class));}
    /** Go to Home activity
     * @param v the view */
    public void goToHome(View v) { startActivity(new Intent(this, VotingActivity.class)); }
    /** Go to Followers activity
     * @param v the view*/
    public void goToFollowers(View v) { startActivity(new Intent(this, MainViewUsersActivity.class)); }
    /** Go To Settings activity
     * @param v the view*/
    public void goToSettings(View v) { startActivity(new Intent(this, ProfileActivity.class)); }

    // End Navigation Methods

}
