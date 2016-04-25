package tcss450.uw.edu.mainproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddUserActivity extends AppCompatActivity {
    private EditText mUserNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mUserNameEditText = (EditText) findViewById(R.id.add_user);
        mEmailEditText = (EditText) findViewById(R.id.add_email);
        mPasswordEditText = (EditText) findViewById(R.id.add_password);

    }

    public void addCourse(View v) {

    }
}