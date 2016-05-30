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
 * A simple {@link Fragment} subclass.
 */
public class VoteResultsFragment extends Fragment {
    public static String QUESTION_SELECTED = "questionSelected";
    public TextView mOption1Text;
    public TextView mOption2Text;
    public ImageView mOption1Image;
    public ImageView mOption2Image;
    public TextView mResults1;
    public TextView mResults2;
    private List<QuestionWithDetail> mQuestionWithDetail;

    public VoteResultsFragment() {
        // Required empty public constructor
    }


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


        //  List<QuestionWithDetail> setViews = new ArrayList<>();
//        for (int i = 0; i < mQuestionWithDetail.size(); i++) {
//            if (i == 0) {
//                mOption1Text.setText(mQuestionWithDetail.get(i).getQuestionText());
//                String image1 = mQuestionWithDetail.get(i).getQuestionImage();
//                image1 = image1.replaceAll(" ","+");
//                mOption1Image.setImageBitmap(StringToBitMap(image1));
//            }
//
//        }
        if (mQuestionWithDetail != null) {
            //  mOption1ID = mQuestionWithDetail.get(0).getQuestionDetailID();
            //  mOption2ID = mQuestionWithDetail.get(1).getQuestionDetailID();
            int half = mQuestionWithDetail.size() / 2;
            mOption1Text.setText(mQuestionWithDetail.get(0).getQuestionText());
            mOption2Text.setText(mQuestionWithDetail.get(half).getQuestionText());
            String image1 = mQuestionWithDetail.get(0).getQuestionImage();
            String image2 = mQuestionWithDetail.get(half).getQuestionImage();
            image1 = image1.replaceAll(" ","+");
            image2 = image2.replaceAll(" ", "+");
            String s =  "       ";
            s = s.replaceAll("\\s","+");
            //Log.i("s", s);
            mOption1Image.setImageBitmap(StringToBitMap(image1));
            mOption2Image.setImageBitmap(StringToBitMap(image2));
            Log.i("image1", image1);
            Log.i("image2", image2);
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

//            if (!image1.equals(null)) {
//                mOption1Image.setImageBitmap(StringToBitMap(image1));
//
//            }
//            if (!image2.equals(null)) {
//                mOption2Image.setImageBitmap(StringToBitMap(image2));
//            }


        }
        return view;
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
    public void updateView(QuestionWithDetail questionWithDet) {
        mQuestionWithDetail = ((myApplication) getActivity().getApplication()).getCurrentQuestion();

               // getCurrentQuestion();
//        mOption1Text = (TextView) view.findViewById(R.id.option1Text);
//        mOption2Text = (TextView) view.findViewById(R.id.option2Text);
//        mResults1 = (TextView) view.findViewById(R.id.option1Result);
//        mResults2 = (TextView) view.findViewById(R.id.option2Result);
        if (mQuestionWithDetail != null) {
            //  mOption1ID = mQuestionWithDetail.get(0).getQuestionDetailID();
            //  mOption2ID = mQuestionWithDetail.get(1).getQuestionDetailID();
            int half = mQuestionWithDetail.size() / 2;
            mOption1Text.setText(mQuestionWithDetail.get(0).getQuestionText());
            mOption2Text.setText(mQuestionWithDetail.get(half).getQuestionText());
            String image1 = mQuestionWithDetail.get(0).getQuestionImage();
            String image2 = mQuestionWithDetail.get(half).getQuestionImage();
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
//            Log.i("result1", result1);
//            Log.i("result2", result2);
//            mResults1.setText(result1);
//            mResults2.setText(result2);

//            if (!image1.equals(null)) {
//                mOption1Image.setImageBitmap(StringToBitMap(image1));
//
//            }
//            if (!image2.equals(null)) {
//                mOption2Image.setImageBitmap(StringToBitMap(image2));
//            }


        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateView((QuestionWithDetail) args.getSerializable(QUESTION_SELECTED));
        }
    }

}
