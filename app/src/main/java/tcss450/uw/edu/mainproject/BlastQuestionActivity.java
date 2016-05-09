package tcss450.uw.edu.mainproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.mainproject.authenticate.MainLoginActivity;
import tcss450.uw.edu.mainproject.data.UserDB;
import tcss450.uw.edu.mainproject.model.User;

public class BlastQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blast_question);

        EnterQuestionFragment enterQuestionFragment = new EnterQuestionFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.blast_question_container, enterQuestionFragment)
                .addToBackStack(null)
                .commit();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**
     * on options item selected
     * @param item menu item
     * @return boolean
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //will log the user out
        if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                    .commit();
            //will return to the main login activity
            Intent i = new Intent(this, MainLoginActivity.class);
            startActivity(i);
            finish();
            return true;

        }
        return  super.onOptionsItemSelected(item);
    }


//    public void toCamera(View v) {
//        Intent  intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,0);
//
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        ImageView iv = (ImageView) findViewById(R.id.imageView);
//        Bitmap bp = (Bitmap) data.getExtras().get("data");
//        iv.setImageBitmap(bp);
//    }


}
