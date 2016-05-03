/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tcss450.uw.edu.mainproject.Helper;

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
/**
 * Login User activity will allow a user to log in to our app.
 *
 * @author Mehgan Cook and Tony Zullo
 *
 */
public class LoginUserActivity extends AppCompatActivity {
    /**The URL to download users from the database*/
    private final static String DOWNLOAD_USER
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=select+%2A+from+User%3B";
    /**The list of users retreived from the database*/
    private List<User> mUsers;
    /**The sql lite database to store the current users email address*/
    private UserDB mUserDB;
    /**Email address enetered by the User*/
    private EditText mEmail;
    /**Password entered by the User*/
    private EditText mPassword;
    /**Adds all the design functionality*/
    private Helper mHelper;


    /**
     * OnCreate method to create the actiity
     * @param savedInstanceState the savedinstancestate
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        mEmail = (EditText) findViewById(R.id.user);
        mPassword = (EditText) findViewById(R.id.password);

        mHelper = new Helper(getAssets());


        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");

        TextView titleText = (TextView) findViewById(R.id.login_title);
        if (titleText != null) {
            titleText.setTypeface(oswald);
        }


        EditText user = (EditText) findViewById(R.id.user);
        if (user != null) {
            user.setTypeface(oswald);
        }

        EditText password = (EditText) findViewById(R.id.password);
        if (password != null) {
            password.setTypeface(oswald);
        }

        Button signInButton = (Button) findViewById(R.id.login_user_button);
        if (signInButton != null) {
            signInButton.setTypeface(oswald);
        }
        DownloadUsersTask task = new DownloadUsersTask();
        task.execute(DOWNLOAD_USER);
    }

    /**
     * login User is an on click method for the login button
     * @param v the view
     * */
    public void loginUser(View v) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        //checks to make sure a valid email was entered
        if (!email.contains("@")) {
            Toast.makeText(this, "Enter a valid email address"
                    , Toast.LENGTH_SHORT)
                    .show();
            mEmail.requestFocus();
            return;
        }
        //checks to make sure password field is not blank
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password"
                    , Toast.LENGTH_SHORT)
                    .show();
            mPassword.requestFocus();
            return;
        }

        boolean flag = false;
        int marker = 0;
        // runs through the list of users and flags true if the email
        // entered by the user is in the database
        for (int i = 0; i < mUsers.size(); i++) {
            if (email.equals(mUsers.get(i).getEmail())) {
                flag = true; //email exists in the database
                marker = i;
            }
        }
        if (flag) {
            // if the email is in the system and the user enters the correct passwords
            // then the main app activity will launch
            if (password.equals(mUsers.get(marker).getPassword())) {
                Intent i = new Intent(this, MainAppActivity.class);
                startActivity(i);
                // stores that the usr has logged in so they will not be shown the log in screen again
                // until they log out.
                SharedPreferences sharedPreferences =
                        getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), true).commit();
                if (mUserDB == null) {
                    mUserDB = new UserDB(this);
                }
                // inserts the users email into the sql lite database to be used for accessing
                // the database in other activities.
                mUserDB.deleteUsers();
                mUserDB.insertUser(email);
            } else {
                Toast.makeText(this, "Password is incorrect! Please try again.", Toast.LENGTH_LONG).show();
                mPassword.requestFocus();
            }
        } else {
            Toast.makeText(this, "Email address has not been registered. Please Register!", Toast.LENGTH_LONG).show();
        }
    }

/**
 * DownloadUsersTask is an async class that will acccess the database and retreive the current list of users
 * @author Meneka Abraham and Mehgan Cook
 * */
    private class DownloadUsersTask extends AsyncTask<String, Void, String> {

    /**
     * doIntBackground makes calls to the database
     * @param urls is the url string
     * @return the data from the database
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
    /**
     * onPostExecute is where error messages will appear if the database was not able
     * to be accessed.
     * @param result is the result returned by the database
     * */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            mUsers = new ArrayList<>();
            result = User.parseUserJSON(result, mUsers);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
            }

        }
    }

}
