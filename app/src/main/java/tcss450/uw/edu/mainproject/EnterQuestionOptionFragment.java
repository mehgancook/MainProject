package tcss450.uw.edu.mainproject;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.data.UserDB;
import tcss450.uw.edu.mainproject.model.Question;
import tcss450.uw.edu.mainproject.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnterQuestionOptionFragment extends Fragment {
    private final static String DOWNLOAD_QUESTION
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=select+%2A+from+Question%3B";

    private View mView;
    private UserDB mUserDB;
    private String mEmail;
   private EditText mEditTextQuestion;
    private String mTextQuestion;
   private EditText mEditTextComment;
    private String mTextComment;
   private int mQuestionID;
    private List<Question> mQuestions;

    public EnterQuestionOptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_enter_question_option, container, false);
        mUserDB = new UserDB(getActivity());
        mEmail = mUserDB.getUsers().get(0).getEmail();
        mEditTextQuestion = (EditText) mView.findViewById(R.id.text_question);
        mTextQuestion  = mEditTextQuestion.getText().toString();
        mEditTextComment = (EditText) mView.findViewById(R.id.text_comment);
        mTextComment = mEditTextComment.getText().toString();
        DownloadQuestionTask task = new DownloadQuestionTask();
        task.execute(DOWNLOAD_QUESTION);
        Button takeImage = (Button) mView.findViewById(R.id.take_picture);
        takeImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        // Inflate the layout for this fragment
        return mView;
    }
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView iv = (ImageView) mView.findViewById(R.id.imageView);
        Bitmap bp = (Bitmap) data.getExtras().get("data");
        iv.setImageBitmap(bp);
    }

    /**
     * DownloadQuestionTask is an async class that will acccess the database and retreive the current list of questions
     * @author Meneka Abraham and Mehgan Cook
     * */
    private class DownloadQuestionTask extends AsyncTask<String, Void, String> {

        /**
         * doIntBackground makes calls to the database
         * @param urls is the url string
         * @return the data from the database
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
                    response = "Unable to download the list of users, Reason: "
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
         * onPostExecute is where error messages will appear if the database was not able
         * to be accessed.
         * @param result is the result returned by the database
         * */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            mQuestions = new ArrayList<>();
            List<Question> temp = new ArrayList<>();
            result = Question.parseQuestionJSON(result, mQuestions);
            for(int i = 0; i < mQuestions.size(); i++) {
                if (mQuestions.get(i).getUserEmail().equals(mEmail)) {
                    temp.add(mQuestions.get(i));
                }
            }
            int index = temp.size() - 1;
            mQuestionID = temp.get(index).getQuestionId();
            Log.i("QuestionID", mQuestionID + "");
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
            }

        }
    }

}
