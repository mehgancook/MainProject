package tcss450.uw.edu.mainproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.view.View;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public void toLoginPage(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void toFacebook(View view) {

       // Intent intentFacebook = new Intent(this, .class);
       // startActivity(intentImage);
    }

    public void toJoin(View view) {
        Intent intentJoin = new Intent(this, Join.class);
        startActivity(intentJoin);

    }
}
