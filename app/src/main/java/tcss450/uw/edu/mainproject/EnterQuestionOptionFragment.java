package tcss450.uw.edu.mainproject;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.data.UserDB;
import tcss450.uw.edu.mainproject.model.Question;
import tcss450.uw.edu.mainproject.model.QuestionDetail;
import tcss450.uw.edu.mainproject.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnterQuestionOptionFragment extends Fragment {
    private final static String DOWNLOAD_QUESTION
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=select+%2A+from+Question%3B";
    public List<QuestionDetail> mQuestionDetail;
    private View mView;
    private UserDB mUserDB;
    private String mEmail;
    private String mImage;
   private EditText mEditTextOption;
    private int mOptionsCounter;
    private String mTextOption;
   private EditText mEditTextComment;
    private String mTextComment;
    private ImageView mImageView;
   private int mQuestionID;
    private List<Question> mQuestions;

    public EnterQuestionOptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_enter_question_option, container, false);
        mQuestionDetail = new ArrayList<>();
        mOptionsCounter = 0;
        mUserDB = new UserDB(getActivity());
        mEmail = mUserDB.getUsers().get(0).getEmail();
        mEditTextOption = (EditText) mView.findViewById(R.id.text_question);
      //  mTextOption  = mEditTextOption.getText().toString();
        mEditTextComment = (EditText) mView.findViewById(R.id.text_comment);
     //   mTextComment = mEditTextComment.getText().toString();
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
        final Button imDone =  (Button) mView.findViewById(R.id.done_with_options);
        imDone.setVisibility(View.INVISIBLE);
        final Button addMoreOptions =  (Button) mView.findViewById(R.id.add_more_options);
        addMoreOptions.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mTextOption = mEditTextOption.getText().toString();
                mTextComment = mEditTextComment.getText().toString();
                if (TextUtils.isEmpty(mTextOption) && mImageView == null) {
                    Toast.makeText(getActivity(), "Please Select an Image or Enter a Text option", Toast.LENGTH_LONG)
                            .show();
                } else {
                    mOptionsCounter++;
                    QuestionDetail questionDetail = new QuestionDetail(mQuestionID + "", mTextOption, mTextComment, mImage, 0 +"");
                    mQuestionDetail.add(questionDetail);
                    Log.i("question detail size", mQuestionDetail.size() + "");
                    Log.i("mOptionsCounter", mOptionsCounter + "");
                    mEditTextComment.setText(null);
                    mEditTextOption.setText(null);
                    if (mImageView != null)
                    mImageView.setImageDrawable(null);
                    imDone.setVisibility(View.VISIBLE);
                    addMoreOptions.setVisibility(View.INVISIBLE); //allows for only 2 options to be selected

                }
            }
        });
       // Button imDone =  (Button) mView.findViewById(R.id.done_with_options);
       // Log.i("mOptionsCounter", mOptionsCounter + "");

        imDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextOption = mEditTextOption.getText().toString();
                mTextComment = mEditTextComment.getText().toString();
                if (TextUtils.isEmpty(mTextOption) && mImageView == null) {
                    Toast.makeText(getActivity(), "Please Select an Image or Enter a Text option", Toast.LENGTH_LONG)
                            .show();
                } else {
                    if (TextUtils.isEmpty(mTextOption)) {
                        mTextOption = null;
                    }
                    if (TextUtils.isEmpty(mTextComment)) {
                        mTextComment = null;
                    }
                    QuestionDetail questionDetail = new QuestionDetail(mQuestionID + "", mTextOption, mTextComment, mImage, 0 + "");
                    mQuestionDetail.add(questionDetail);
                    Log.i("question detail size", mQuestionDetail.size() + "");
                    Log.i("mOptionsCounter", mOptionsCounter + "");
                    mEditTextComment.setText(null);
                    mEditTextOption.setText(null);
                    if (mImageView != null)
                        mImageView.setImageDrawable(null);
                }
                ((myApplication) getActivity().getApplication()).setDetailList(mQuestionDetail);
                FollowListFragment followListFragment = new FollowListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.blast_question_container, followListFragment)
                        .commit();
            }


        });

        // Inflate the layout for this fragment
        return mView;
    }
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageView = (ImageView) mView.findViewById(R.id.imageView);
        Bitmap bp = (Bitmap) data.getExtras().get("data");
        mImage = BitMapToString(bp);
        mImageView.setImageBitmap(bp);
    }
    public String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
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
            ((myApplication) getActivity().getApplication()).setQuestionID(mQuestionID);
            Log.i("QuestionID", mQuestionID + "");
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
            }

        }
    }

}
