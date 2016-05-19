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

import java.util.List;

import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.model.QuestionWithDetail;
import tcss450.uw.edu.mainproject.model.User;
import tcss450.uw.edu.mainproject.myApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class Vote extends Fragment {
    /**user item selected*/
    public static String QUESTION_SELECTED = "questionSelected";
    public static TextView mOption1Text;
    public static TextView mOption2Text;
    public static ImageView mOption1Image;
    public static ImageView mOption2Image;
    public static TextView mOption1Comment;
    public static TextView mOption2Comment;

    public Vote() {
        // Required empty public constructor
    }

    /**
     * update the view with current information

     * */
    public void updateView(QuestionWithDetail questionWithDet) {
        List<QuestionWithDetail> questionWithDetail = ((myApplication) getActivity().getApplication()).getCurrentQuestion();
        if (questionWithDetail != null) {
            mOption1Text.setText(questionWithDetail.get(0).getQuestionText());
            mOption2Text.setText(questionWithDetail.get(1).getQuestionText());
            String image1 = questionWithDetail.get(0).getQuestionImage();
            String image2 = questionWithDetail.get(1).getQuestionImage();
           // Log.i("image1", image1);
            if (!image1.equals(null)) {
                Log.i("image1", image1);
                byte[] decodedString = Base64.decode(image1, Base64.URL_SAFE);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                byte[] imageAsBytes = Base64.decode(image1.getBytes(), Base64.DEFAULT);
               // ImageView image = (ImageView)this.findViewById(R.id.ImageView);
                mOption1Image.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
              //  Bitmap img1 = StringToBitMap(image1);
             //   mOption1Image.setImageBitmap(decodedByte);
            }
            if (!image2.equals(null)) {
                byte[] decodedString = Base64.decode(image1, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
              //  Bitmap img2 = StringToBitMap(image2);
                mOption2Image.setImageBitmap(decodedByte);
            }

            mOption1Comment.setText(questionWithDetail.get(0).getQuestionComment());
            mOption2Comment.setText(questionWithDetail.get(1).getQuestionComment());
        }
    }
    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_vote, container, false);
        List<QuestionWithDetail> questionWithDetail = ((myApplication) getActivity().getApplication()).getCurrentQuestion();
        mOption1Text = (TextView) view.findViewById(R.id.option1Text);
        mOption2Text = (TextView) view.findViewById(R.id.option2Text);
        mOption1Comment = (TextView) view.findViewById(R.id.option1Comment);
        mOption2Comment = (TextView) view.findViewById(R.id.option2Comment);
        mOption1Image = (ImageView) view.findViewById(R.id.option1Image);
        mOption2Image = (ImageView) view.findViewById(R.id.option2Image);
        // Inflate the layout for this fragment
        return view;
    }
    /**
     * onStart
     * */
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
