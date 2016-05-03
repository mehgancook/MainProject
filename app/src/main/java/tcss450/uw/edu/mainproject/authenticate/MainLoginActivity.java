/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.authenticate;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import tcss450.uw.edu.mainproject.Helper;
import tcss450.uw.edu.mainproject.R;

/**
 * MainLoginActiity is where the user can select to log in if they have an account or
 * register if they do not have an account.
 *
 * */
public class MainLoginActivity extends AppCompatActivity {
    /**Helper method of style*/
    private Helper mHelper;

    /**
     * OnCreate method to create the actiity
     * @param savedInstanceState the savedinstancestate
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        mHelper = new Helper(getAssets());

        TextView joinLink = (TextView) findViewById(R.id.join);
        TextView loginLink = (TextView) findViewById(R.id.login);

        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        if (joinLink != null) {
            joinLink.setTypeface(oswald);
        }
        if (loginLink != null) {
            loginLink.setTypeface(oswald);
        }
    }



/**
 * toJoin takes you to the screen where you can register a user
 *@param v the view
 * */
    public void toJoin(View v) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);

    }
/**
 * toLoginPage takes you to the screen where you can login a user.
 * @param v the view.
 * */
    public void toLoginPage(View v) {
        Intent intent = new Intent(this, LoginUserActivity.class);
        startActivity(intent);
    }

}
