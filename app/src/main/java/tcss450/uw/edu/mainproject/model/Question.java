package tcss450.uw.edu.mainproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mehgan on 5/8/2016.
 */
public class Question implements Serializable {
    /**USERNAME is the username, EMAIL is the email, PASSWORD is the password,
     * USER_ID is the user id*/
    private static final String QUESTION_ID = "questionid", QUESTION_NAME = "questionname",
            USER_ID = "useremail";
    /**The group name*/
    private String mQuestionsName;
    /***/
    private String mUserID;

    private String mQuestionID;
    /**
     * Constructor for user
     * @param questionName the name of the group
     * @param userID the user who started the group
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

    public String getQuestionName() {
        return mQuestionsName;
    }
    public int getQuestionId() {
        return Integer.parseInt(mQuestionID);
    }
    public void setQuestionName(String qn) {
        mQuestionsName = qn;
    }

    public String getUserEmail() {
        return mUserID;
    }

}

