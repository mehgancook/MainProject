package tcss450.uw.edu.mainproject.followers_askers_groups;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tcss450.uw.edu.mainproject.R;

/**
 * Created by anthonyzullo on 5/30/16.
 */
public class SpecialAsyncTask  extends AsyncTask<String, Void, String> {
    /** String to toast with */
    private String mToastString;
    /** Context of this */
    private Context mContext;

    private boolean mGetUserId = false;

    public void prepToast(String toast, Context con) {
        mToastString = toast;
        mContext = con;
    }

    public void setGetUserId(boolean gettingUserId) {
        mGetUserId = gettingUserId;
    }
    /**
     * tasks to be done in the background
     * @param urls the string of url
     * @return the response from the server
     * */
    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        HttpURLConnection urlConnection = null;
        for (String url : urls) {
            try {
                URL urlObject = new URL(url);
                urlConnection = (HttpURLConnection) urlObject.openConnection();

                InputStream content = urlConnection.getInputStream();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s;
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }

            } catch (Exception e) {
                response = "Unable to insert asker, Reason: "
                        + e.getMessage();
            }
            finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
        }
        return response;
    }

    /**
     * On Post execute of the task
     * @param result the result from the databsae
     * */
    @Override
    protected void onPostExecute(String result) {
        if (mGetUserId) {
            JSONArray arr;
            JSONObject obj;
            int userid = -1;
            try {
                arr = new JSONArray(result);
                obj = arr.getJSONObject(0);
                userid = obj.getInt("userid");
            } catch (JSONException e) {

            }
            if (userid != -1) {
                SharedPreferences sharedPreferences =
                        mContext.getSharedPreferences(mContext.getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt(mContext.getString(R.string.USERID), userid).commit();
            }
        } else {
            Toast.makeText(mContext, mToastString, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
