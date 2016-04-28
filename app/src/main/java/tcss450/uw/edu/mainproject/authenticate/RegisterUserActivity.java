package tcss450.uw.edu.mainproject.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import tcss450.uw.edu.mainproject.MainAppActivity;
import tcss450.uw.edu.mainproject.R;

public class RegisterUserActivity extends AppCompatActivity {
    protected SharedPreferences mSharedPreferences;
    private String mResult;
    private final static String ADD_USER
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=";

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mUsername = (EditText) findViewById(R.id.add_username);
        mEmail = (EditText) findViewById(R.id.add_email);
        mPassword = (EditText) findViewById(R.id.add_password);
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);

    }

    public void addUser(View v) {
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(username))  {
            Toast.makeText(this, "Enter username"
                    , Toast.LENGTH_SHORT)
                    .show();
            mUsername.requestFocus();
            return;
        }
        if (!email.contains("@")) {
            Toast.makeText(this, "Enter a valid email address"
                    , Toast.LENGTH_SHORT)
                    .show();
            mEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password))  {
            Toast.makeText(this, "Enter password"
                    , Toast.LENGTH_SHORT)
                    .show();
            mPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Enter password of at least 6 characters"
                    , Toast.LENGTH_SHORT)
                    .show();
            mPassword.requestFocus();
            return;
        }

        String url = buildCourseURL(v);
        AddUserTask task = new AddUserTask();
        task.execute(new String[]{url.toString()});

      //  if (mResult.equals("success")) {
            Intent i = new Intent(this, MainAppActivity.class);
            startActivity(i);
        mSharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), true).commit();
      //  }
    }

    private String buildCourseURL(View v) {
        StringBuilder sb = new StringBuilder();
        sb.append(ADD_USER);
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        Log.i(username,username + email + password);
        String url = "insert into User values ('" + username + "','" + email + "','" + password + "','');";
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (Exception exception) {
            //showing an exception yet its adding the data?
          //  Toast.makeText(v.getContext(), "Something wrong with the url" + exception.getMessage(), Toast.LENGTH_LONG)
           //         .show();
        }
        Log.i(username, url);
        sb.append(url);
        return sb.toString();
    }


private class AddUserTask extends AsyncTask<String, Void, String> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

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
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }

            } catch (Exception e) {
                response = "Unable to add " + mUsername + ", Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
        }
        return response;
    }


    /**
     * It checks to see if there was a problem with the URL(Network) which is when an
     * exception is caught. It tries to call the parse Method and checks to see if it was successful.
     * If not, it displays the exception.
     *
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        // Something wrong with the network or the URL.
        Log.i("result", result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            mResult = (String) jsonObject.get("result");
            String status = (String) jsonObject.get("errors");
            Log.i("status", status);
            if (status.equals("none")) {
                Toast.makeText(getApplicationContext(), mUsername + " successfully added!"
                        , Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to add: "
                                + jsonObject.get("error")
                        , Toast.LENGTH_LONG)
                        .show();
            }
        } catch (JSONException e) {
           // Toast.makeText(getApplicationContext(), "Something wrong with the data" +
             //       e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

}
