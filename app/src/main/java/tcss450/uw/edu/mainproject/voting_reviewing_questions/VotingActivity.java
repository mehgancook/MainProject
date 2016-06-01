package tcss450.uw.edu.mainproject.voting_reviewing_questions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.account.ProfileActivity;
import tcss450.uw.edu.mainproject.authenticate.MainLoginActivity;
import tcss450.uw.edu.mainproject.blast_question.BlastQuestionActivity;
import tcss450.uw.edu.mainproject.data.UserDB;
import tcss450.uw.edu.mainproject.followers_askers_groups.MainViewUsersActivity;
import tcss450.uw.edu.mainproject.model.QuestionWithDetail;
import tcss450.uw.edu.mainproject.model.User;
import tcss450.uw.edu.mainproject.myApplication;
import tcss450.uw.edu.mainproject.Helper;

public class VotingActivity extends AppCompatActivity implements AnswerQuestionsFragment.OnAnswerListFragmentInteractionListener,
AskedQuestionResultFragment.OnListFragmentInteractionListener {
    private static String USER_URL ="http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=" +
            "select+%2A+from+User+where+email+%3D+%27";
    private AnswerQuestionsFragment mAnswerQuestionsFragment;
    private AskedQuestionResultFragment mAskedQuestionFragment;
    private TextView mResultsButton;
    private TextView mAnswerButton;
    private Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        DownloadUserIDTask task = new DownloadUserIDTask();
        task.execute(buildURL());
        mHelper = new Helper(getAssets());
//        AnswerQuestionsFragment answerQuestionsFragment = new AnswerQuestionsFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, answerQuestionsFragment)
//                .commit();
        mAnswerButton = (TextView) findViewById(R.id.answer_button);
        mResultsButton = (TextView) findViewById(R.id.results_button);
        mHelper.setFontStyle(mAnswerButton);
        mHelper.setFontStyle(mResultsButton);
        toVotingResults(mResultsButton);

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
            String toastMessage = "";
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.EMAIL_PREFS), Context.MODE_PRIVATE);
            if (sharedPreferences.getBoolean(getString(R.string.YESEMAIL), false)) {
                title = "Turn off notifications?";
                toastMessage = "You will not longer send emails from Slick Pick!";
            } else {
                title = "Turn on notifications?";
                toastMessage = "You will now send emails from Slick Pick!";
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

    public void toAnswerQuestions(View v) {
        int selectedColor = getResources().getColor(R.color.colorPrimary);
        int white = getResources().getColor(R.color.white);
        Drawable iconBox;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            iconBox = getResources().getDrawable(R.drawable.icon_border, getTheme());
            mResultsButton.setBackground(iconBox);
        } else {
            mResultsButton.setBackgroundColor(white);
        }
        mAnswerButton.setBackgroundColor(selectedColor);


        mAnswerQuestionsFragment = new AnswerQuestionsFragment();
        if (mAskedQuestionFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mAnswerQuestionsFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mAnswerQuestionsFragment)
                    .remove(mAskedQuestionFragment)
                    .commit();
        }

    }

    public void toVotingResults(View v) {
        int selectedColor = getResources().getColor(R.color.colorPrimary);
        int white = getResources().getColor(R.color.white);
        Drawable iconBox;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            iconBox = getResources().getDrawable(R.drawable.icon_border, getTheme());
            mAnswerButton.setBackground(iconBox);
        } else {
            mAnswerButton.setBackgroundColor(white);
        }
        mResultsButton.setBackgroundColor(selectedColor);


        mAskedQuestionFragment = new AskedQuestionResultFragment();
        if (mAnswerQuestionsFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mAskedQuestionFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mAskedQuestionFragment)
                    .remove(mAnswerQuestionsFragment)
                    .commit();
        }
    }

    @Override
    public void onAnswerListFragmentInteraction(QuestionWithDetail questionWithDetail) {
        Vote voteFragment = new Vote();
        Bundle args = new Bundle();
        String name = questionWithDetail.getQuestionName();
        List<QuestionWithDetail> questions = ((myApplication) getApplication()).getQuestionList();
        for (int i = 0; i < questions.size(); i++) {
            if (!questions.get(i).getQuestionName().equals(name)) {
                questions.remove(i);
                i--;
            }
        }
        ((myApplication) getApplication()).setCurrentQuestion(questions);

        args.putSerializable(voteFragment.QUESTION_SELECTED, questionWithDetail);
        voteFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, voteFragment)
                .addToBackStack(null)
                .commit();
    }

    public void onListFragmentInteraction(QuestionWithDetail questionWithDetail) {
        VoteResultsFragment voteResults = new VoteResultsFragment();
        Bundle args = new Bundle();

        String name = questionWithDetail.getQuestionName();
        int id = questionWithDetail.getQuestionId();
        List<QuestionWithDetail> questions = ((myApplication) getApplication()).getQuestionList();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestionId() != id) {
           // if (!questions.get(i).getQuestionName().equals(name)) {
                questions.remove(i);
                i--;
            }
        }
        ((myApplication) getApplication()).setCurrentQuestion(questions);

        args.putSerializable(voteResults.QUESTION_SELECTED, questionWithDetail);
        voteResults.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, voteResults)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Build the url string based on the currently logged in user email address
     * @return the url
     * */
    public String buildURL() {
        StringBuilder sb = new StringBuilder(USER_URL);
        UserDB userDB = new UserDB(this);
        String email = userDB.getUsers().get(0).getEmail();
        try {
            sb.append(URLEncoder.encode(email, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("%27%3B");
        return sb.toString();
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

    private class DownloadUserIDTask extends AsyncTask<String, Void, String> {
        /**
         * call the server in the background
         * @param urls the url
         * @return the response from the servier
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
                    response = "Unable to download the list of followers, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
        /**
         * on post execute
         * @param result the result from the server
         * */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<User> me = new ArrayList<>();
            result = User.parseUserJSON(result, me);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            ((myApplication) getApplication()).setUserID(me.get(0).getUserID());
            Log.i("myid", ((myApplication) getApplication()).getUserID() + "");
        }


    }
}
