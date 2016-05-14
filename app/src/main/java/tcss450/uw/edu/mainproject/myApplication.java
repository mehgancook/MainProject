package tcss450.uw.edu.mainproject;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.model.QuestionDetail;

/**
 * Created by Mehgan on 5/11/2016.
 */
public class myApplication extends Application {
    public List<QuestionDetail> mQuestions;

    public List<QuestionDetail> getDetailList() {
        return mQuestions;
    }

    public void setDetailList(List<QuestionDetail> list) {
        mQuestions = list;
    }
}

