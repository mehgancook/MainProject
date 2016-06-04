/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.account;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.Helper;
import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.model.QuestionMember;
import tcss450.uw.edu.mainproject.model.QuestionWithDetail;
import tcss450.uw.edu.mainproject.myApplication;

/**
 * AnswerQuestionDetailsFragment shows the question details that the user has answered,
 * as well as shows the way the current votes are going for that question.
 */
public class AnsweredQuestionDetailsFragment extends Fragment {

    /**Holds the text for option 1 the user had entered*/
    public TextView mOption1Text;
    /**Holds the text for option 2 the user had entered*/
    public TextView mOption2Text;
    /**Holds the image for option 1 the user had entered*/
    public ImageView mOption1Image;
    /**Holds the image for option 2 the user had entered*/
    public ImageView mOption2Image;
    /**Holds the voting results for option 1*/
    public TextView mResults1;
    /**Holds the voting results for option 2*/
    public TextView mResults2;
    /**Holds the question details for the question selected*/
    private List<QuestionWithDetail> mQuestionWithDetail;
    /**Displays the users voting results*/
    private TextView mOptionSelectedText;
    /**Holds the users voting results*/
    private int mOptionSelected;
    /** Helper for font */
    private Helper mHelper;

    /**
     * Constructor for AnsweredQuestionDetailsFragment
     * */
    public AnsweredQuestionDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * on create view
     * @param savedInstanceState the saved instance
     * @param container of viewGroup
     * @param inflater inflates the fragment
     * @return the view
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answered_question_details, container, false);
        mQuestionWithDetail = ((myApplication) getActivity().getApplication()).getCurrentQuestion();
        mOptionSelectedText = (TextView) view.findViewById(R.id.optionPicked);
        String url = buildURL();
        DownloadOptionSelectedTask task = new DownloadOptionSelectedTask();
        task.execute(url);

        mHelper = new Helper(getActivity().getAssets());
        mOption1Text = (TextView) view.findViewById(R.id.option1Text);
        mOption2Text = (TextView) view.findViewById(R.id.option2Text);
        mResults1 = (TextView) view.findViewById(R.id.option1ResultPercent);
        mResults2 = (TextView) view.findViewById(R.id.option2ResultPercent);
        mOption1Image = (ImageView) view.findViewById(R.id.option1Image);
        mOption2Image = (ImageView) view.findViewById(R.id.option2Image);

        mHelper.setFontStyle((TextView) view.findViewById(R.id.option1));
        mHelper.setFontStyle((TextView) view.findViewById(R.id.option2));
        mHelper.setFontStyle(mOption1Text);
        mHelper.setFontStyle(mOption2Text);
        mHelper.setFontStyle(mResults1);
        mHelper.setFontStyle(mResults2);
        //if there are details available for the question selected
        if (mQuestionWithDetail != null) {
            int half = mQuestionWithDetail.size() / 2; //gets the second option that is in the details list
            mOption1Text.setText(mQuestionWithDetail.get(0).getQuestionText());
            mOption2Text.setText(mQuestionWithDetail.get(half).getQuestionText());
            String image1 = mQuestionWithDetail.get(0).getQuestionImage();
            String image2 = mQuestionWithDetail.get(half).getQuestionImage();
            image1 = image1.replaceAll(" ","+");
            image2 = image2.replaceAll(" ", "+");
            mOption1Image.setImageBitmap(StringToBitMap(image1));
            mOption2Image.setImageBitmap(StringToBitMap(image2));
            String result1;
            String result2;
            int voteResults1 = mQuestionWithDetail.get(0).getVoteCount();
            int voteResults2 = mQuestionWithDetail.get(half).getVoteCount();
            if (voteResults1 + voteResults2 != 0) {
                double votePercentResult1 = (double) voteResults1 / (voteResults1 + voteResults2);
                double votePercentResult2 = (double) voteResults2 / (voteResults1 + voteResults2);
                DecimalFormat myFormatter = new DecimalFormat("##.##%");
                result1 = myFormatter.format(votePercentResult1);
                result2 = myFormatter.format(votePercentResult2);
                mResults1.setText(result1);
                mResults2.setText(result2);
            } else {
                result1 = "No votes yet!";
                result2 = "No votes yet!";
                mResults1.setText(result1);
                mResults2.setText(result2);
            }
        }
        return view;
    }
    /**
     * Builds the String in order to get the selected option from question selected
     * @return the string to be used by the async task.
     *
     * */
    public String buildURL() {
        StringBuilder sb = new StringBuilder();
        int userid = ((myApplication) getActivity().getApplication()).getUserID();
        int questionid = mQuestionWithDetail.get(0).getQuestionId();
        sb.append("http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=");
        try {
            sb.append(URLEncoder.encode("select * from QuestionMember where questionid = " + questionid +
                    " and questionmemberid = " + userid + ";", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try{
            // Log.i("StringToBitMap", encodedString);
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    /**
     * DownloadOptionSelecctedTask downloads the option that the user selcted for the question
     * that they have answered.
     *
     * */
    private class DownloadOptionSelectedTask extends AsyncTask<String, Void, String> {
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
                Toast.makeText(getActivity().getBaseContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<QuestionMember> me = new ArrayList<>();
            result = QuestionMember.parseQuestionMemberJSON(result, me);
            mOptionSelected = me.get(0).getOptionPicked();
            mOptionSelectedText.setText("You Selected Option " + mOptionSelected + "!");
            mHelper.setFontStyle(mOptionSelectedText);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getBaseContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
        }


    }
}
