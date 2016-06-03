/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.voting_reviewing_questions;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.model.QuestionWithDetail;
import tcss450.uw.edu.mainproject.myApplication;
import tcss450.uw.edu.mainproject.Helper;

/**
 * vote results fragment displays the vote results
 */
public class VoteResultsFragment extends Fragment {
    /**Holder for option 1 text*/
    public TextView mOption1Text;
    /**Holder for option 2 text*/
    public TextView mOption2Text;
    /**Holder for option 1 image*/
    public ImageView mOption1Image;
    /**Holder for option 2 image*/
    public ImageView mOption2Image;
    /**Holder for option 1 vote results*/
    public TextView mResults1;
    /**Holder for option 2 vote results*/
    public TextView mResults2;
    /**List of questions with details*/
    private List<QuestionWithDetail> mQuestionWithDetail;

    /**
     * Required empty constructor
     * */
    public VoteResultsFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vote_results, container, false);
        mQuestionWithDetail = ((myApplication) getActivity().getApplication()).getCurrentQuestion();

        Helper helper = new Helper(getActivity().getAssets());

        mOption1Text = (TextView) view.findViewById(R.id.option1Text);
        mOption2Text = (TextView) view.findViewById(R.id.option2Text);
        mResults1 = (TextView) view.findViewById(R.id.option1ResultPercent);
        mResults2 = (TextView) view.findViewById(R.id.option2ResultPercent);
        mOption1Image = (ImageView) view.findViewById(R.id.option1Image);
        mOption2Image = (ImageView) view.findViewById(R.id.option2Image);

        helper.setFontStyle((TextView) view.findViewById(R.id.option1));
        helper.setFontStyle((TextView) view.findViewById(R.id.option2));
        helper.setFontStyle(mOption1Text);
        helper.setFontStyle(mOption2Text);
        helper.setFontStyle(mResults1);
        helper.setFontStyle(mResults2);


        if (mQuestionWithDetail != null) {
            int half = mQuestionWithDetail.size() / 2;
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
     * String to bitmap changes a string version of the image into a bitmap
     * @param encodedString the string version of image
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
