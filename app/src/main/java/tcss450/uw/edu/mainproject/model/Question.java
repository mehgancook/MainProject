/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Holds the information for a question
 */
public class Question implements Serializable {
    /**QUESTION_ID is the questionid, QUESTION_NAME is the question name,
     * USER_ID is the user email*/
    private static final String QUESTION_ID = "questionid", QUESTION_NAME = "questionname",
            USER_ID = "useremail";
    /**The group name*/
    private String mQuestionsName;
    /**The user id*/
    private String mUserID;
    /**The question id*/
    private String mQuestionID;
    /**
     * Constructor for Question
     * @param questionName the name of the question
     * @param userID the user who started the question
     *
     * */
    public Question(String questionID, String questionName, String userID) {
        mQuestionsName = questionName;
        mUserID = userID;
        mQuestionID = questionID;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns question list if success.
     * @param questionJSON json
     * @return reason or null if successful.
     */
    public static String parseQuestionJSON(String questionJSON, List<Question> questionList) {
        String reason = null;
        if (questionJSON != null) {
            try {
                JSONArray arr = new JSONArray(questionJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Question question= new Question(obj.getString(QUESTION_ID), obj.getString(QUESTION_NAME)
                            , obj.getString(USER_ID));
                    questionList.add(question);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    /**
     * gets the question id
     * @return the question id
     * */
    public int getQuestionId() {
        return Integer.parseInt(mQuestionID);
    }

    /**Get the user email
     * @return the user id*/
    public String getUserEmail() {
        return mUserID;
    }

}

