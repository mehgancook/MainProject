package tcss450.uw.edu.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tcss450.uw.edu.mainproject.authenticate.MainLoginActivity;

public class SplashActivity extends AppCompatActivity {

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
                    Intent intent = new Intent(SplashActivity.this,MainLoginActivity.class);
                    startActivity(intent);
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