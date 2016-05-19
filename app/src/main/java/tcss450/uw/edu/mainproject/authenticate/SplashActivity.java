/*
 * Slick pick app
  * Mehgan Cook and Tony Zullo
  * Mobile apps TCSS450
 * */
package tcss450.uw.edu.mainproject.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tcss450.uw.edu.mainproject.BlastQuestionActivity;
import tcss450.uw.edu.mainproject.MainViewUsersActivity;
import tcss450.uw.edu.mainproject.R;
import tcss450.uw.edu.mainproject.VotingActivity;

/**
 * SplashActivity displays an image of our logo.
 * @author Mehgan Cook and Tony Zullo
 * */
public class SplashActivity extends AppCompatActivity {
    /**Used to see if the user is logged in*/
    private SharedPreferences mSharedPreferences;

    /**
     * onCreate creates the activity
     * @param savedInstanceState the saved instance state
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //creates a timer that will last for 3 second. this is how long the splash screen
        //will be displayed
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                            , Context.MODE_PRIVATE);
                    if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
                        Intent intent = new Intent(SplashActivity.this, MainLoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, VotingActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    /**
     * onPause
     * */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}