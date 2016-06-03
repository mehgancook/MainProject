/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.followers_askers_groups;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import tcss450.uw.edu.mainproject.Helper;
import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.account.ProfileActivity;
import tcss450.uw.edu.mainproject.authenticate.MainLoginActivity;
import tcss450.uw.edu.mainproject.blast_question.BlastQuestionActivity;
import tcss450.uw.edu.mainproject.model.User;
import tcss450.uw.edu.mainproject.voting_reviewing_questions.VotingActivity;

/**
 * This class is used to allow the user to add askers to their current asker list
 *
 * */
public class AddAskersActivity extends AppCompatActivity implements AskerListFragment.OnListFragmentInteractionListener  {

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
            String title = "";
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.EMAIL_PREFS), Context.MODE_PRIVATE);
            if (sharedPreferences.getBoolean(getString(R.string.YESEMAIL), false)) {
                title = "Turn off notifications?";
            } else {
                title = "Turn on notifications?";
            }
            new AlertDialog.Builder(this)
                    .setTitle("Email Notifications")
                    .setMessage(title)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String toastMessage = "";
                            SharedPreferences sharedPreferences =
                                    getSharedPreferences(getString(R.string.EMAIL_PREFS), Context.MODE_PRIVATE);
                            if (sharedPreferences.getBoolean(getString(R.string.YESEMAIL), true)) {
                                toastMessage = "You will no longer send emails from Slick Pick!";
                                sharedPreferences =
                                        getSharedPreferences(getString(R.string.EMAIL_PREFS), Context.MODE_PRIVATE);
                                sharedPreferences.edit().putBoolean(getString(R.string.YESEMAIL), false).commit();
                            } else {
                                toastMessage = "You will now send emails from Slick Pick!";
                                sharedPreferences =
                                        getSharedPreferences(getString(R.string.EMAIL_PREFS), Context.MODE_PRIVATE);
                                sharedPreferences.edit().putBoolean(getString(R.string.YESEMAIL), true).commit();
                            }
                            Toast.makeText(getBaseContext(),toastMessage , Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * OnListFragmentInteraction will add an akser when a user is selected from the list
     * @param user is the user being clicked on
     * */
    @Override
    public void onListFragmentInteraction(User user) {
        int userid = user.getUserID();
        String url = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=";

        String username = user.getUsername();
        SpecialAsyncTask task = new SpecialAsyncTask();
      //  task.prepToast("Added " + username, getApplicationContext());
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
        SharedPreferences sp =
                getSharedPreferences(getString(R.string.EMAIL_PREFS), Context.MODE_PRIVATE);
        if (sp.getBoolean(getString(R.string.YESEMAIL), true)) {
            String[] TO = {user.getEmail()};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");


            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Follow Request");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello " + user.getUsername() + "! I would love to " +
                    "start answering your questions! Please send me questions and I will answer them quickly!");
            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
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

}
