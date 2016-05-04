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
public class User implements Serializable{
    /**USERNAME is the username, EMAIL is the email, PASSWORD is the password,
     * USER_ID is the user id*/
    private static final String USERNAME = "username", EMAIL = "email",
                        PASSWORD = "password", USER_ID = "userid";
    /**username*/
    private String mUsername;
    /**email address*/
    private String mEmailAddress;
    /**Password*/
    private String mPassword;

    /**
     * Constructor for user
     * @param emailAddress the email address
     * @param password the password
     * @param userid the userid
     * @param username the username
     *
     * */
    public User(String username, String emailAddress, String password, String userid) {
        mUsername = username;
        mEmailAddress = emailAddress;
        mPassword = password;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns user list if success.
     * @param userJSON json
     * @return reason or null if successful.
     */
    public static String parseUserJSON(String userJSON, List<User> userList) {
        String reason = null;
        if (userJSON != null) {
            try {
                JSONArray arr = new JSONArray(userJSON);

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
    /**
     * Gets the username
     * @return the username
     * */
    public String getUsername() {
        return mUsername;
    }
    /**
     * Gets the email
     * @return the email
     * */
    public String getEmail() {
        return mEmailAddress;
    }
    /**
     * Gets the password
     * @return the password
     * */
    public String getPassword() {
        return mPassword;
    }
    /**
     * Set the eamil
     * @param email the email address
     * */
    public void setEmail(String email) {
        mEmailAddress = email;
    }




}
