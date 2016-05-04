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
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import tcss450.uw.edu.mainproject.Helper;
import tcss450.uw.edu.mainproject.MainViewUsersActivity;
import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.data.UserDB;


/**
 * RegisterUserAcitivity is the form that the user fills out in order to register
 * @author Mehgan Cook and Tony Zullo
 * */
public class RegisterUserActivity extends AppCompatActivity {
    /**static variable of the first part of the URL for adding a user to the databse*/
    private final static String ADD_USER
            = "http://cssgate.insttech.washington.edu/~_450atm4/zombieturtles.php?totallyNotSecure=";
    /**the SharedPrferences used to store if a user is logged in*/
    protected SharedPreferences mSharedPreferences;
    /**the User database to store the email of a user who registers*/
    private UserDB mUserDB;
    /**this class used in the private async class*/
    private RegisterUserActivity mMe;
    /**Helper method for style*/
    private Helper mHelper;
    /**the username entered by the User*/
    private EditText mUsername;
    /**the email entered by the User*/
    private EditText mEmail;
    /**the password entered by the user*/
    private EditText mPassword;

    /**
     *onCreate creates the activity
     *@param savedInstanceState the savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMe = this;
        setContentView(R.layout.activity_add_user);

        mHelper = new Helper(getAssets());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.logo_adjusted));
            setTitle("");
        }

        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");

        TextView titleText = (TextView) findViewById(R.id.sign_title);
        titleText.setTypeface(oswald);

        TextView userid = (TextView) findViewById(R.id.userid);
        userid.setTypeface(oswald);

        EditText user = (EditText) findViewById(R.id.user);
        user.setTypeface(oswald);

        EditText password = (EditText) findViewById(R.id.password);
        password.setTypeface(oswald);

        Button signInButton = (Button) findViewById(R.id.login_user_button);
        signInButton.setTypeface(oswald);
        mUsername = (EditText) findViewById(R.id.userid);
        mEmail = (EditText) findViewById(R.id.user);
        mPassword = (EditText) findViewById(R.id.password);
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
    }

    /**
     * addUser is the button that will register a user
     * @param v the View
     * */
    public void addUser(View v) {
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        //checks to see if they input a username
        if (TextUtils.isEmpty(username))  {
            Toast.makeText(this, "Enter username"
                    , Toast.LENGTH_SHORT)
                    .show();
            mUsername.requestFocus();
            return;
        }
        //checks to make sure the email is valid
        if (!email.contains("@")) {
            Toast.makeText(this, "Enter a valid email address"
                    , Toast.LENGTH_SHORT)
                    .show();
            mEmail.requestFocus();
            return;
        }
        // checks to make sure the password field is not empty
        if (TextUtils.isEmpty(password))  {
            Toast.makeText(this, "Enter password"
                    , Toast.LENGTH_SHORT)
                    .show();
            mPassword.requestFocus();
            return;
        }
        //users have to create a password at least 6 characters long
        if (password.length() < 6) {
            Toast.makeText(this, "Enter password of at least 6 characters"
                    , Toast.LENGTH_SHORT)
                    .show();
            mPassword.requestFocus();
            return;
        }

        String url = buildCourseURL();
        AddUserTask task = new AddUserTask();
        task.execute(url);

        mSharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), true).commit();
        if (mUserDB == null) {
            mUserDB = new UserDB(this);
        }
        mUserDB.deleteUsers();
        mUserDB.insertUser(email);
    }

    /**
     * buildCourseURL encodes the information that they user gave and returns a URL to add the
     * user to the database
     * @return the url string
     * */
    private String buildCourseURL() {
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
            Log.e("exception", exception.toString());
        }
        Log.i(username, url);
        sb.append(url);
        return sb.toString();
    }

    /**
     * DownloadUsersTask is an async class that will acccess the database and retreive the current list of users
     * @author Meneka Abraham and Mehgan Cook
     * */
private class AddUserTask extends AsyncTask<String, Void, String> {

    /**
     * onPreExecute
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * doInBackground
     * @param urls is the string url
     * @return String returns the response from the server
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
     * @param result is the result
     */
    @Override
    protected void onPostExecute(String result) {
       try {
            JSONObject jsonObject = new JSONObject(result);
            String status = (String) jsonObject.get("errors");
            if (status.equals("none")) {
                Intent i = new Intent(mMe, MainViewUsersActivity.class);
                startActivity(i);
                mSharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), true).commit();
                if (mUserDB == null) {
                    mUserDB = new UserDB(mMe);
                }
                mUserDB.deleteUsers();
                mUserDB.insertUser(mEmail.getText().toString());
                Toast.makeText(getApplicationContext(), mUsername.getText().toString() + " successfully added!"
                        , Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(), "Email is already registered! Please log in"

                        , Toast.LENGTH_LONG)
                        .show();
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

}
