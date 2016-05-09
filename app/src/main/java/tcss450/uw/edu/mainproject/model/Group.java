package tcss450.uw.edu.mainproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mehgan on 5/8/2016.
 */
public class Group implements Serializable {
    /**USERNAME is the username, EMAIL is the email, PASSWORD is the password,
     * USER_ID is the user id*/
    private static final String GROUP_ID = "groupid", GROUP_NAME = "groupname",
            USER_ID = "userid";
    /**The group name*/
    private String mGroupName;
    /***/
    private String mUserID;
    /**
     * Constructor for user
     * @param groupName the name of the group
     * @param userID the user who started the group
     *
     * */
    public Group(String groupID, String groupName, String userID) {
        mGroupName = groupName;
        mUserID = userID;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns user list if success.
     * @param groupJSON json
     * @return reason or null if successful.
     */
    public static String parseGroupJSON(String groupJSON, List<Group> groupList) {
        String reason = null;
        if (groupJSON != null) {
            try {
                JSONArray arr = new JSONArray(groupJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Group group = new Group(obj.getString(GROUP_ID), obj.getString(GROUP_NAME)
                            , obj.getString(USER_ID));
                    groupList.add(group);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public String getGroupName() {
        return mGroupName;
    }
    public void setGroupName(String gn) {
        mGroupName = gn;
    }

}
