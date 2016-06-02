/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.blast_question;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.Helper;
import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.data.UserDB;
import tcss450.uw.edu.mainproject.followers_askers_groups.FollowListFragment;
import tcss450.uw.edu.mainproject.model.Question;
import tcss450.uw.edu.mainproject.model.QuestionDetail;
import tcss450.uw.edu.mainproject.myApplication;


/**
 * Enter Question Option Fragment is where the user enters the details to the question
 * that they want to ask.
 */
public class EnterQuestionOptionFragment extends Fragment {
    /**Static string that downlads the question*/
    private final static String DOWNLOAD_QUESTION
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=select+%2A+from+Question%3B";
    /**List of the question details*/
    public List<QuestionDetail> mQuestionDetail;
    /**the view*/
    private View mView;
    /**user database that holds the email*/
    private UserDB mUserDB;
    /**the email of the user*/
    private String mEmail;
    /**The string version of the image*/
    private String mImage;
    /**The edittext that allows user to enter text option*/
    private EditText mEditTextOption;
    /**Counter to keep track of how many options have been answered*/
    private int mOptionsCounter;
    /**The text option the user input*/
    private String mTextOption;
    /**Edit text field for user to enter a comment*/
    private EditText mEditTextComment;
    /**The comment entered by the user*/
    private String mTextComment;
    /**Imageview to display image taken by the user*/
    private ImageView mImageView;
    /**The question id*/
    private int mQuestionID;
    /**List of questions*/
    private List<Question> mQuestions;
    /**Helper method for style*/
    private Helper mHelper;

    /**
     * Empty constructor
     * */
    public EnterQuestionOptionFragment() {
        // Required empty public constructor
    }

    /**
     * On create view
     * @param savedInstanceState the saved instance state
     * @param inflater the inflater
     * @param container the container
     * @return view
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_enter_question_option, container, false);
        mHelper = new Helper(getActivity().getAssets());
        mHelper.setFontStyle((TextView) mView.findViewById(R.id.textView));
        mHelper.setFontStyle((TextView) mView.findViewById(R.id.take_picture));
        mHelper.setFontStyle((TextView) mView.findViewById(R.id.textView2));
        mHelper.setFontStyle((TextView) mView.findViewById(R.id.text_comment));
        mHelper.setFontStyle((TextView) mView.findViewById(R.id.add_more_options));
        mHelper.setFontStyle((TextView) mView.findViewById(R.id.done_with_options));

        mQuestionDetail = new ArrayList<>();
        mOptionsCounter = 0;
        mUserDB = new UserDB(getActivity());
        mEmail = mUserDB.getUsers().get(0).getEmail();
        mEditTextOption = (EditText) mView.findViewById(R.id.text_question);
        mHelper.setFontStyle(mEditTextOption);
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
                    mEditTextComment.setText("");
                    mEditTextOption.setText("");
                    if (mImageView != null)
                    mImageView.setImageDrawable(null);
                    mImageView = null;
                    imDone.setVisibility(View.VISIBLE);
                    addMoreOptions.setVisibility(View.INVISIBLE);
                    mEditTextOption.clearFocus();
                    mEditTextComment.clearFocus();
                       //allows for only 2 options to be selected

                }
            }
        });

        imDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextOption = mEditTextOption.getText().toString();
                mTextComment = mEditTextComment.getText().toString();
                if (TextUtils.isEmpty(mTextOption) && mImageView == null || (mTextOption.equals("") && mImageView == null)) {
                    Toast.makeText(getActivity(), "Please Select an Image or Enter a Text option", Toast.LENGTH_LONG)
                            .show();
                } else {
                    if (TextUtils.isEmpty(mTextOption)) {
                        mTextOption = "";
                    }
                    if (TextUtils.isEmpty(mTextComment)) {
                        mTextComment = "";
                    }
                    QuestionDetail questionDetail = new QuestionDetail(mQuestionID + "", mTextOption, mTextComment, mImage, 0 + "");
                    mQuestionDetail.add(questionDetail);
                    ((myApplication) getActivity().getApplication()).setDetailList(mQuestionDetail);
                    FollowListFragment followListFragment = new FollowListFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.blast_question_container, followListFragment)
                            .commit();
                    Button button =(Button) getActivity().findViewById(R.id.send_all_button);
                    button.setVisibility(View.VISIBLE);


                }

            }


        });

        // Inflate the layout for this fragment
        return mView;
    }

        /**
         * OnActivityResult is used to get the picture that was taken with the camera
         * @param requestCode  the request code
         * @param resultCode the result code
         * @param data the data
         *
         * */
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageView = (ImageView) mView.findViewById(R.id.imageView);
            try {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                mImage = BitMapToString(bp);
                mImageView.setImageBitmap(StringToBitMap(mImage));
            } catch(Exception e) {
                e.getMessage();
                mImageView = null;
            }
         //   Log.i("Image", mImage);
    }

    /**
     * BitMapToString changes the bitmap image to a string view to be able
     * to be stored in the database
     * @param bitmap the bitmap image
     * @return the string version of the bitmap
     *
     * */
    public String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        Log.i("bitmap string", temp);
        return temp;

    }

    /**StringToBitMap changes a string version of the image
     * into a bitmap image
     * @param encodedString the string version of an image
     * @return the bitmap version of the image
     * */
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
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
     //       Log.i("QuestionID", mQuestionID + "");
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
            }

        }
    }

}
