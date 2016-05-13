package tcss450.uw.edu.mainproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mehgan on 5/8/2016.
 */
public class QuestionDetail implements Serializable {
    /**USERNAME is the username, EMAIL is the email, PASSWORD is the password,
     * USER_ID is the user id*/
    private static final String QUESTION_ID = "groupid", QUESTION_TEXT = "questiontext",
            QUESTION_COMMENT = "questioncomment";
    /**The group name*/
    private String mQuestionID;
    /***/
    private String mQuestionText;

    private String mQuestionComment;
    /**
     * Constructor for user
     * @param questionID the name of the group
     * @param questionText the user who started the group
     *
     * */
    public QuestionDetail(String questionID, String questionText, String questionComment) {
        mQuestionID = questionID;
        mQuestionText = questionText;
        mQuestionComment = questionComment;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns user list if success.
     * @param questionDetailJSON json
     * @return reason or null if successful.
     */
    public static String parseQuestionDetailJSON(String questionDetailJSON, List<QuestionDetail> questionDetailList) {
        String reason = null;
        if (questionDetailJSON != null) {
            try {
                JSONArray arr = new JSONArray(questionDetailJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    QuestionDetail questionDetail = new QuestionDetail(obj.getString(QUESTION_ID), obj.getString(QUESTION_TEXT)
                            , obj.getString(QUESTION_COMMENT));
                    questionDetailList.add(questionDetail);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public int getQuestionId () {
        return Integer.parseInt(mQuestionID);
    }
    public String getQuestionText() {
        return mQuestionText;
    }
    public String getQuestionComment() {
        return mQuestionComment;
    }

}
