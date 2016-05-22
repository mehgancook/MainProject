package tcss450.uw.edu.mainproject;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.model.QuestionDetail;
import tcss450.uw.edu.mainproject.model.QuestionWithDetail;

/**
 * Created by Mehgan on 5/11/2016.
 */
public class myApplication extends Application {
    public List<QuestionDetail> mQuestions;
    public int mQuestionID;
    public int mUserID;
    public List<QuestionWithDetail> mQuestionsToVote;
    public List<QuestionWithDetail> mCurrentQuestion;

    public List<QuestionWithDetail> getQuestionList() {return mQuestionsToVote;}

    public void setQuestionLst(List<QuestionWithDetail> list) {mQuestionsToVote = list;}


    public void setCurrentQuestion (List<QuestionWithDetail> list) {mCurrentQuestion = list;}
    public List<QuestionWithDetail> getCurrentQuestion() {return mCurrentQuestion;}

    public List<QuestionDetail> getDetailList() {
        return mQuestions;
    }

    public void setDetailList(List<QuestionDetail> list) {
        mQuestions = list;
    }

    public void setQuestionID(int id) {
        mQuestionID = id;
    }
    public int getQuestionID() {
        return mQuestionID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public int getUserID() {
        return mUserID;
    }
}

