package me.biuti.transmissampa.ui.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.biuti.transmissampa.R;
import me.biuti.transmissampa.model.User;

public class LogInActivity extends ActionBarActivity {

    public static final String TAG = LogInActivity.class.getSimpleName();

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.etUsername) EditText mUserName;
    @InjectView(R.id.etPassword) EditText mPassword;
    @InjectView(R.id.btnLogIn) Button mButton;

    public User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_log_in));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btnLogIn)
    public void logIn(View view){

        final ProgressDialog progressDialog = ProgressDialog.show(
                this,
                getString(R.string.progress_login_title),
                getString(R.string.progress_login_text),
                true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ParseUser.logInInBackground(mUserName.getText().toString(), mPassword.getText().toString(),
                    new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                mUser = new User(user);
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
            }
        }).start();

    }

}
