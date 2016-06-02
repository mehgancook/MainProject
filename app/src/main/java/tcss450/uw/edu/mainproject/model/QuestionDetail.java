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
 * Question Detail holds the question details
 */
public class QuestionDetail implements Serializable {
    /**QUESTION_ID is the questionid, QUESTION_TEXT is the question text, QUESTION_COMMENT is the quesiton comment,
     * QUESTION_IMAGE is the question image, VOTE_COUNT is the vote count, QUESTION_DETAIL_ID is the questiondetail id */
    private static final String QUESTION_ID = "questionid", QUESTION_TEXT = "questiontext",
            QUESTION_COMMENT = "questioncomment", QUNESTION_IMAGE = "questionimage", VOTE_COUNT = "votecount";
    /**The question id name*/
    private String mQuestionID;
    /** the question text*/
    private String mQuestionText;
    /**The question commebt*/
    private String mQuestionComment;
    /**The question image*/
    private String mQuestionImage;
    /**The vote count*/
    private String mVoteCount;
    /**
     * Constructor for Question Detail
     * @param questionID the id of the question
     *@param   questiontext the question text
     * @param questionComment the question comment
     * @param questionImage the question image
     * @param voteCount the vote count
     * */
    public QuestionDetail(String questionID, String questiontext,  String questionComment,
                          String questionImage, String voteCount) {
        mQuestionText = questiontext;
        mQuestionID = questionID;
        mQuestionComment = questionComment;
        mQuestionImage = questionImage;
        mVoteCount = voteCount;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns question detail list if success.
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
                    QuestionDetail questionDetail = new QuestionDetail(obj.getString(QUESTION_ID)
                            ,obj.getString(QUESTION_TEXT), obj.getString(QUESTION_COMMENT), obj.getString(QUNESTION_IMAGE),
                            obj.getString(VOTE_COUNT));
                    questionDetailList.add(questionDetail);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    /**
     * get question image returns the image
     * @return the image
     * */
    public String getmQuestionImage() {return mQuestionImage; }
    /**
     * gets the question text
     * @return the question text
     * */
    public String getQuestionText() {
        return mQuestionText;
    }
    /**
     * gets the question comment
     * @return the question comment
     */
    public String getQuestionComment() {
        return mQuestionComment;
    }

}
