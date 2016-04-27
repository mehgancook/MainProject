package tcss450.uw.edu.mainproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mehgan on 4/26/2016.
 */
public class User {

    public static final String USERNAME = "username", EMAIL = "email",
                        PASSWORD = "password", USER_ID = "userid";
    private String mUsername;
    private String mEmailAddress;
    private String mPassword;
    private String mUserID;
    private List<User> mFollowers;
    private List<User> mAskers;

    public User(String username, String emailAddress, String password, String userid) {
        mUsername = username;
        mEmailAddress = emailAddress;
        mPassword = password;
        mUserID = userid;
        mFollowers = new ArrayList<>();
        mAskers = new ArrayList<>();
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param courseJSON
     * @return reason or null if successful.
     */
    public static String parseCourseJSON(String courseJSON, List<User> userList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    User user = new User(obj.getString(User.USERNAME), obj.getString(User.EMAIL)
                            , obj.getString(User.PASSWORD), obj.getString(User.USER_ID));
                    userList.add(user);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmailAddress;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setEmail(String email) {
        mEmailAddress = email;
    }

    public void setPassword(String password) {
        mPassword = password;
    }


}
