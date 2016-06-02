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
 * Question member class that holds information about a question member
 */
public class QuestionMember {
    /**QUESTION_ID is the questionid, QUESTIOn_MEMBER_ID is the question member id, QUESTION_ANSWEREd
     * is the question answered, OPTION_PICKED is the option picked*/
    private static final String QUESTION_ID = "questionid", QUESTION_MEMBER_ID = "questionmemberid",
            QUESTION_ANSWERED = "questionanswered", OPTION_PICKED = "optionpicked";
    /**question id*/
    private String mQuestionId;
    /**question member id*/
    private String mQuestionMemberId;
    /**question answered*/
    private String mQuestionAnswered;
    /**Option picked*/
    private String mOptionPicked;


    /**
     * Constructor for question member
     * @param questionId the question id
     * @param questionMemberId the question member id
     * @param questionAnswered the question answered
     * @param optionPicked the option picked
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
     * Returns question member list if success.
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

    /**gets the option that the user voted for
     * @return the option picked*/
    public int getOptionPicked() {
        return Integer.parseInt(mOptionPicked);
    }




}
