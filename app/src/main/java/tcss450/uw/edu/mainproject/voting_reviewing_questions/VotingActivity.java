package tcss450.uw.edu.mainproject.voting_reviewing_questions;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import tcss450.uw.edu.mainproject.data.UserDB;
import tcss450.uw.edu.mainproject.model.QuestionWithDetail;
import tcss450.uw.edu.mainproject.model.User;
import tcss450.uw.edu.mainproject.myApplication;

public class VotingActivity extends AppCompatActivity implements AnswerQuestionsFragment.OnAnswerListFragmentInteractionListener,
AskedQuestionResultFragment.OnListFragmentInteractionListener {
    private static String USER_URL ="http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=" +
            "select+%2A+from+User+where+email+%3D+%27";
    private AnswerQuestionsFragment mAnswerQuestionsFragment;
    private AskedQuestionResultFragment mAskedQuestionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        DownloadUserIDTask task = new DownloadUserIDTask();
        task.execute(buildURL());
//        AnswerQuestionsFragment answerQuestionsFragment = new AnswerQuestionsFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, answerQuestionsFragment)
//                .commit();

    }

    public void toAnswerQuestions(View v) {
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
        List<QuestionWithDetail> questions = ((myApplication) getApplication()).getQuestionList();
        for (int i = 0; i < questions.size(); i++) {
            if (!questions.get(i).getQuestionName().equals(name)) {
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
