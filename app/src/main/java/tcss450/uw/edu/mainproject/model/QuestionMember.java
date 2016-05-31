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
import java.util.ArrayList;
import java.util.List;

/**
 * User class that holds information about a user
 * @author Mehgan Cook and Tony Zullo
 */
public class QuestionMember {
    /**USERNAME is the username, EMAIL is the email, PASSWORD is the password,
     * USER_ID is the user id*/
    private static final String QUESTION_ID = "questionid", QUESTION_MEMBER_ID = "questionmemberid",
            QUESTION_ANSWERED = "questionanswered", OPTION_PICKED = "optionpicked";
    /**username*/
    private String mQuestionId;
    /**email address*/
    private String mQuestionMemberId;
    /**Password*/
    private String mQuestionAnswered;

    private String mOptionPicked;


    /**
     * Constructor for user
     * @param questionId the email address
     * @param questionMemberId the password
     * @param questionAnswered the userid
     * @param optionPicked the username
     *
     * */
    public QuestionMember(String questionId, String questionMemberId, String questionAnswered, String optionPicked) {
        mQuestionId = questionId;
        mQuestionMemberId = questionMemberId;
        mQuestionAnswered = questionAnswered;
        mOptionPicked = optionPicked;

    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns user list if success.
     * @param questionMemberJSON json
     * @return reason or null if successful.
     */
    public static String parseQuestionMemberJSON(String questionMemberJSON, List<QuestionMember> questionMemberList) {
        String reason = null;
        if (questionMemberJSON != null) {
            try {
                JSONArray arr = new JSONArray(questionMemberJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    QuestionMember qm = new QuestionMember(obj.getString(QuestionMember.QUESTION_ID), obj.getString(QuestionMember.QUESTION_MEMBER_ID)
                            , obj.getString(QuestionMember.QUESTION_ANSWERED), obj.getString(QuestionMember.OPTION_PICKED));
                    questionMemberList.add(qm);


                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
    public int getOptionPicked() {
        return Integer.parseInt(mOptionPicked);
    }




}
