package tcss450.uw.edu.mainproject.authenticate;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import tcss450.uw.edu.mainproject.Helper;
import tcss450.uw.edu.mainproject.R;

public class MainLoginActivity extends AppCompatActivity {

    private Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        mHelper = new Helper(getAssets());

        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);
        //   FacebookSdk.sdkInitialize(getApplicationContext());
        //   AppEventsLogger.activateApp(this);

           TextView joinLink = (TextView) findViewById(R.id.join);
           TextView loginLink = (TextView) findViewById(R.id.login);

        //   LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //   loginButton.setReadPermissions("email");

           Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
           joinLink.setTypeface(oswald);
           loginLink.setTypeface(oswald);



        //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
      //      public void onClick(View view) {
         //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
           //             .setAction("Action", null).show();
        //    }
    //    });
    }




    public void toJoin(View v) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);

    }

    public void toLoginPage(View v) {
        Intent intent = new Intent(this, LoginUserActivity.class);
        startActivity(intent);
    }

    public void toFacebook(View v) {

    }

}
