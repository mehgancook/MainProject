package tcss450.uw.edu.mainproject.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tcss450.uw.edu.mainproject.Helper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.MainAppActivity;
import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.data.UserDB;
import tcss450.uw.edu.mainproject.model.User;

public class LoginUserActivity extends AppCompatActivity {
    private final static String DOWNLOAD_USER
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=select+%2A+from+User%3B";
    private List<User> mUsers;
    private UserDB mUserDB;
    private EditText mEmail;
    private EditText mPassword;

    private Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        mEmail = (EditText) findViewById(R.id.user);
        mPassword = (EditText) findViewById(R.id.password);

        mHelper = new Helper(getAssets());


        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");

        TextView titleText = (TextView) findViewById(R.id.login_title);
        titleText.setTypeface(oswald);


        EditText user = (EditText) findViewById(R.id.user);
        user.setTypeface(oswald);

        EditText password = (EditText) findViewById(R.id.password);
        password.setTypeface(oswald);

        Button signInButton = (Button) findViewById(R.id.login_user_button);
        signInButton.setTypeface(oswald);
        DownloadUsersTask task = new DownloadUsersTask();
        task.execute(new String[]{DOWNLOAD_USER});
    }

    public void loginUser(View v) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (!email.contains("@")) {
            Toast.makeText(this, "Enter a valid email address"
                    , Toast.LENGTH_SHORT)
                    .show();
            mEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password"
                    , Toast.LENGTH_SHORT)
                    .show();
            mPassword.requestFocus();
            return;
        }

        boolean flag = false;
        int marker = 0;
        for (int i = 0; i < mUsers.size(); i++) {
            if (email.equals(mUsers.get(i).getEmail())) {
                flag = true;
                marker = i;
            }
        }
        if (flag) {
            if (password.equals(mUsers.get(marker).getPassword())) {
                Intent i = new Intent(this, MainAppActivity.class);
                startActivity(i);
                SharedPreferences sharedPreferences =
                        getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), true).commit();

            } else {
                Toast.makeText(this, "Password is incorrect! Please try again.", Toast.LENGTH_LONG);
                mPassword.requestFocus();
            }
        } else {
            Toast.makeText(this, "Email address has not been registered. Please Register!", Toast.LENGTH_LONG);
        }
    }


    private class DownloadUsersTask extends AsyncTask<String, Void, String> {

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
                    response = "Unable to download the list of users, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            mUsers = new ArrayList<User>();
            result = User.parseUserJSON(result, mUsers);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

        }
    }

}
