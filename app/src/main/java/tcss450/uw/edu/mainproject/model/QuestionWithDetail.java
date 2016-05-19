package tcss450.uw.edu.mainproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mehgan on 5/8/2016.
 */
public class QuestionWithDetail implements Serializable {
    /**USERNAME is the username, EMAIL is the email, PASSWORD is the password,
     * USER_ID is the user id*/
    private static final String  QUESTION_ANSWERED = "questionanswered",QUESTION_NAME = "questionname", QUESTION_ID = "questionid",
            QUESTION_TEXT = "questiontext", QUESTION_COMMENT = "questioncomment",
            QUESTION_IMAGE = "questionimage", VOTE_COUNT = "votecount";
    /**The group name*/
    private String mQuestionsName;
    /***/
    private String mQuestionID;
    private String mQuestionText;
    private String mQuestionComment;
    private String mQuestionImage;
    private String mVoteCount;
    private String mQuestionAnswered;
    /**
     * Constructor for user
     *
     * */
    public QuestionWithDetail(String questionAnswered, String questionName, String questionID, String questionText, String questionComment,
                    String questionImage, String voteCount) {
        mQuestionAnswered = questionAnswered;
        mQuestionsName = questionName;
        mQuestionID = questionID;
        mQuestionText = questionText;
        mQuestionComment = questionComment;
        mQuestionImage = questionImage;
        mVoteCount = voteCount;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns question list if success.
     * @param questionJSON json
     * @return reason or null if successful.
     */
    public static String parseQuestionWithDetailJSON(String questionJSON, List<QuestionWithDetail> questionWithDetailList) {
        String reason = null;
        if (questionJSON != null) {
            try {
                JSONArray arr = new JSONArray(questionJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    QuestionWithDetail questionWithDetail= new QuestionWithDetail(obj.getString(QUESTION_ANSWERED),obj.getString(QUESTION_NAME), obj.getString(QUESTION_ID),
                            obj.getString(QUESTION_TEXT), obj.getString(QUESTION_COMMENT), obj.getString(QUESTION_IMAGE),
                            obj.getString(VOTE_COUNT));
                    questionWithDetailList.add(questionWithDetail);
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

    public String getQuestionText() {
        return mQuestionText;
    }
    public String getQuestionComment() {
        return mQuestionComment;
    }

    public String getQuestionImage() {
        return mQuestionImage;
    }

    public String getQuestionAnswered() {return mQuestionAnswered;}

    public int getVoteCount() {
        return Integer.parseInt(mVoteCount);
    }

}

