/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject;

import android.app.Application;

import java.util.List;

import tcss450.uw.edu.mainproject.model.QuestionDetail;
import tcss450.uw.edu.mainproject.model.QuestionWithDetail;
import tcss450.uw.edu.mainproject.model.User;

/**
 * myApplication is used for global variables that can be called in any activity or fragment
 */
public class myApplication extends Application {
    /**List of questions*/
    public List<QuestionDetail> mQuestions;
    /**Question id*/
    public int mQuestionID;
    /**user id*/
    public int mUserID;
    /**Questions to vote on*/
    public List<QuestionWithDetail> mQuestionsToVote;
    /**The current question that is selected*/
    public List<QuestionWithDetail> mCurrentQuestion;
    /**List of followeres*/
    public List<User> mFollowers;
    /**List of askwers*/
    public List<User> mAskers;
    /**
     * Gets the question list
     * @return questions with details
     * */
    public List<QuestionWithDetail> getQuestionList() {return mQuestionsToVote;}

    /**
     * sets the question list
     * @param list a question with details list
     * */
    public void setQuestionLst(List<QuestionWithDetail> list) {mQuestionsToVote = list;}

    /**
     * Gets the followers
     * @return list of followers
     * */
    public List<User> getFollowers() {return mFollowers;}

    /**
     * Sets the list of followers
     * @param followers the list of followers
     * */
    public void setFollowers(List<User> followers) { mFollowers = followers;}

    /**
     * Gets the askers
     * @return list of followers
     * */
    public List<User> getAskers() {return mAskers;}

    /**
     * Sets the list of askers
     * @param askers the list of askers
     * */
    public void setAskers(List<User> askers) { mAskers = askers;}


    /**
     * Sets the current Question
     * @param list the current question selected
     * */
    public void setCurrentQuestion (List<QuestionWithDetail> list) {mCurrentQuestion = list;}
    /**
     * Gets the current question
     * @return the current question
     * */
    public List<QuestionWithDetail> getCurrentQuestion() {return mCurrentQuestion;}

    /**
     * Gets the detail list
     * @return the detail list
     * */
    public List<QuestionDetail> getDetailList() {
        return mQuestions;
    }
    /**
     * Sets the detail list
     * @param list the detail list
     * */
    public void setDetailList(List<QuestionDetail> list) {
        mQuestions = list;
    }

    /**
     * Sets the question id
     * @param id the question id
     * */
    public void setQuestionID(int id) {
        mQuestionID = id;
    }
    /**
     * gets the question id
     * @return the question id
     * */
    public int getQuestionID() {
        return mQuestionID;
    }
    /**
     * Sets the user id
     * @param userID the user id
     * */
    public void setUserID(int userID) {
        mUserID = userID;
    }
    /**
     * Gets the user id
     * @return the user
     * */
    public int getUserID() {
        return mUserID;
    }
}

