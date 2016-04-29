package tcss450.uw.edu.mainproject.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tcss450.uw.edu.mainproject.MainAppActivity;
import tcss450.uw.edu.mainproject.R;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
                        Intent intent = new Intent(SplashActivity.this, MainAppActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }


    //   FacebookSdk.sdkInitialize(getApplicationContext());
    //   AppEventsLogger.activateApp(this);

    //   TextView joinLink = (TextView) findViewById(R.id.join);
    //   TextView loginLink = (TextView) findViewById(R.id.login);

    //   LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
    //   loginButton.setReadPermissions("email");

    //    Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
    //   joinLink.setTypeface(oswald);
    //   loginLink.setTypeface(oswald);


}