package tcss450.uw.edu.mainproject.blast_question;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.account.ProfileActivity;
import tcss450.uw.edu.mainproject.authenticate.MainLoginActivity;
import tcss450.uw.edu.mainproject.followers_askers_groups.FollowListFragment;
import tcss450.uw.edu.mainproject.followers_askers_groups.MainViewUsersActivity;
import tcss450.uw.edu.mainproject.model.QuestionDetail;
import tcss450.uw.edu.mainproject.model.User;
import tcss450.uw.edu.mainproject.myApplication;
import tcss450.uw.edu.mainproject.voting_reviewing_questions.VotingActivity;

public class BlastQuestionActivity extends AppCompatActivity implements FollowListFragment.OnListFragmentInteractionListener {
 private List<User> mSendToUsers;
    private Button mSendButton;
    private Button mSendToAllButton;
    private int mQuestionID;
    private List<User> mSendToAllFollowers;
    public List<QuestionDetail> mQuestionDetails;
    private String mQuestionText;
    private String mQuestionComment;
    private String mQuestionImage;
    private int mFollowerID;
    private BlastQuestionActivity mOwner;
    /**static variable of the first part of the URL for adding a user to the databse*/
    private final static String BLAST_QUESTION
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blast_question);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.logo_adjusted));
            setTitle("");
        }

        mOwner = this;
        mSendButton = (Button) findViewById(R.id.send_button);
        mSendButton.setVisibility(View.GONE);
        mSendToAllButton = (Button) findViewById(R.id.send_all_button);
        mSendToAllButton.setVisibility(View.GONE);
        EnterQuestionFragment enterQuestionFragment = new EnterQuestionFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.blast_question_container, enterQuestionFragment)
                .commit();
    }
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

    @Override
    public void onListFragmentInteraction(User user) {
//        Log.i("question options", ((myApplication) this.getApplication()).getDetailList().size() +"");
        mSendButton.setVisibility(View.VISIBLE);
        if (mSendToUsers == null) {
            mSendToUsers = new ArrayList<>();
            mSendToUsers.add(user);
            Toast.makeText(this, user.getUsername() + " added to list!", Toast.LENGTH_SHORT).show();
        } else {
            boolean flag = false;
            for(int i = 0; i < mSendToUsers.size(); i++) {
                if (mSendToUsers.get(i).getUsername().equals(user.getUsername())) {
                    flag = true;
                    Toast.makeText(this,user.getUsername() + " removed from list!", Toast.LENGTH_SHORT).show();
                    mSendToUsers.remove(i);
                }
            }
            if (!flag) {
                mSendToUsers.add(user);
                Toast.makeText(this, user.getUsername() + " added to list!", Toast.LENGTH_SHORT).show();
            }
        }
        if (mSendToUsers.size() > 0) {
            mSendButton.setVisibility(View.VISIBLE);
        } else {
            mSendButton.setVisibility(View.GONE);
        }
    }

    public void blastQuestionAll(View v) {
        mQuestionID = ((myApplication) getApplication()).getQuestionID();
        if (mSendToAllFollowers == null) {
            mSendToAllFollowers = new ArrayList<>();
        }
        mSendToAllFollowers =  ((myApplication) getApplication()).getFollowers();
        new AlertDialog.Builder(this)
                .setTitle("Send to All Followeres")
                .setMessage("Are you sure you want to Send to all Followers?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mQuestionDetails =  ((myApplication) getApplication()).getDetailList();
                        for (int i = 0; i < mQuestionDetails.size(); i++) {
                            mQuestionText = mQuestionDetails.get(i).getQuestionText();
                            mQuestionComment = mQuestionDetails.get(i).getQuestionComment();
                            mQuestionImage = mQuestionDetails.get(i).getmQuestionImage();
                            String url = insertDetailsURL();
                           // Log.i("url working", url);
                            BlastQuestionTask task = new BlastQuestionTask();
                            task.execute(url);
                        }
                        for (int i = 0; i < mSendToAllFollowers.size(); i++) {
                            mFollowerID = mSendToAllFollowers.get(i).getUserID();
                            String url = insertFollowerURL();
                            BlastQuestionTask task = new BlastQuestionTask();
                            task.execute(url);
                        }
                        Intent i = new Intent(getBaseContext(), VotingActivity.class);
                        startActivity(i);
                         finish();
                        Toast.makeText(getBaseContext(), "Question sent!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Reselect followers!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void blastQuestion(View v) {
        mQuestionID = ((myApplication) getApplication()).getQuestionID();
        String names = mSendToUsers.get(0).getUsername();
        for (int i = 1; i < mSendToUsers.size(); i++) {
            names += ", " +mSendToUsers.get(i).getUsername();
        }
        new AlertDialog.Builder(this)
                .setTitle("Send to Users")
                .setMessage("Are you sure you want to Send to " + names + "?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mQuestionDetails =  ((myApplication) getApplication()).getDetailList();
                        for (int i = 0; i < mQuestionDetails.size(); i++) {
                            mQuestionText = mQuestionDetails.get(i).getQuestionText();
                            mQuestionComment = mQuestionDetails.get(i).getQuestionComment();
                            mQuestionImage = mQuestionDetails.get(i).getmQuestionImage();
                            String url = insertDetailsURL();
                            Log.i("url working", url);
                            BlastQuestionTask task = new BlastQuestionTask();
                            task.execute(url);
                        }
                        for (int i = 0; i < mSendToUsers.size(); i++) {
                            mFollowerID = mSendToUsers.get(i).getUserID();
                            String url = insertFollowerURL();
                            BlastQuestionTask task = new BlastQuestionTask();
                            task.execute(url);
                        }
                        Intent i = new Intent(getBaseContext(), VotingActivity.class);
                        startActivity(i);
                         finish();
                        Toast.makeText(getBaseContext(), "Question sent!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Reselect followers!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private String insertDetailsURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(BLAST_QUESTION);
        String url = "insert into QuestionDetail values (" + mQuestionID + ",'" + mQuestionText + "','"
                + mQuestionComment + "','" + mQuestionImage +"', 0,'');";
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (Exception exception) {
            Log.e("exception", exception.toString());
        }
        sb.append(url);
        return sb.toString();
    }
    private String insertFollowerURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(BLAST_QUESTION);
        String url = "insert into QuestionMember values (" + mQuestionID + "," + mFollowerID+ ", 'false', '');";
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (Exception exception) {
            Log.e("exception", exception.toString());
        }
        sb.append(url);
        return sb.toString();
    }

    /**
     * DownloadUsersTask is an async class that will acccess the database and retreive the current list of users
     * @author Meneka Abraham and Mehgan Cook
     * */
    private class BlastQuestionTask extends AsyncTask<String, Void, String> {

        /**
         * onPreExecute
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * doInBackground
         * @param urls is the string url
         * @return String returns the response from the server
         * */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s;
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to blast question! Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result is the result
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("errors");
                if (status.equals("none")) {

                } else {

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

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
