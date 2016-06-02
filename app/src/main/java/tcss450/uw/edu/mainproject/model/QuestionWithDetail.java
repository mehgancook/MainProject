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
 * Question with detail displays the question along with the details
 */
public class QuestionWithDetail implements Serializable {
    /**QUESTION_ANSWERED is the question answered, QUESTION_NAME is the question name,
     * QUESTION_ID is the question id, QUESTION_TEXT is the question text, QUESTION_COMMENT is the question comment
     * QUESTION_IMAGE is the question image, VOTE_COUNT is the vote count, QUESTION_DETAIL_ID is the question detail id*/
    private static final String  QUESTION_ANSWERED = "questionanswered",QUESTION_NAME = "questionname", QUESTION_ID = "questionid",
            QUESTION_TEXT = "questiontext", QUESTION_COMMENT = "questioncomment",
            QUESTION_IMAGE = "questionimage", VOTE_COUNT = "votecount", QUESTION_DETAIL_ID = "questiondetailid";
    /**The question name*/
    private String mQuestionsName;
    /**the question id*/
    private String mQuestionID;
    /**the question text*/
    private String mQuestionText;
    /**the question comment*/
    private String mQuestionComment;
    /**the question image*/
    private String mQuestionImage;
    /**the question vote count*/
    private String mVoteCount;
    /**the question answered*/
    private String mQuestionAnswered;
    /**the question detail id*/
    private String mQuestionDetailID;
    /**
     * Constructor for question with detail
     *@param questionAnswered the question answered
     * @param questionName the question name
     * @param questionID the question id
     * @param questionText the question text
     * @param questionComment the question comment
     * @param questionImage the question image
     * @param voteCount the vote count
     * @param questionDetailID the question detail id
     * */
    public QuestionWithDetail(String questionAnswered, String questionName, String questionID, String questionText, String questionComment,
                    String questionImage, String voteCount, String questionDetailID) {
        mQuestionAnswered = questionAnswered;
        mQuestionsName = questionName;
        mQuestionID = questionID;
        mQuestionText = questionText;
        mQuestionComment = questionComment;
        mQuestionImage = questionImage;
        mVoteCount = voteCount;
        mQuestionDetailID = questionDetailID;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns question with detail list if success.
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
                            obj.getString(VOTE_COUNT), obj.getString(QUESTION_DETAIL_ID));
                    questionWithDetailList.add(questionWithDetail);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    /**Get the question name
     * @return the question name*/
    public String getQuestionName() {
        return mQuestionsName;
    }
    /**
     * get the question id
     * return the question id
     * */
    public int getQuestionId() {
        return Integer.parseInt(mQuestionID);
    }
    /**get the question text
     * @return the question text
     * */
    public String getQuestionText() {
        return mQuestionText;
    }
    /**
     * get question comment
     * @return question comment
     * */
    public String getQuestionComment() {
        return mQuestionComment;
    }
    /**
     * get question image
     * @return question image
     * */
    public String getQuestionImage() {
        return mQuestionImage;
    }
    /**
     * get question answered
     * @return question answered
     * */
    public String getQuestionAnswered() {return mQuestionAnswered;}

    /**Get vote count
     * @return vote count*/
    public int getVoteCount() {
        return Integer.parseInt(mVoteCount);
    }
    /**
     * Get question detail id
     * @return question detail id
     * */

    public int getQuestionDetailID() {return Integer.parseInt(mQuestionDetailID); }

}

