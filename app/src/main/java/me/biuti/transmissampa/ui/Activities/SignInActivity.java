package me.biuti.transmissampa.ui.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rey.material.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.biuti.transmissampa.R;
import me.biuti.transmissampa.model.User;


public class SignInActivity extends ActionBarActivity {

    public static final String TAG = SignInActivity.class.getSimpleName();

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.etName) EditText mName;
    @InjectView(R.id.etEmail) EditText mEmail;
    @InjectView(R.id.etUsername) EditText mUsername;
    @InjectView(R.id.etPassword) EditText mPassword;
    @InjectView(R.id.etConfirmPassword) EditText mConfirmPassword;
    @InjectView(R.id.btnSignIn) Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_sign_in));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btnSignIn)
    public void signInAction(View view){

        if (validateFields()){

            final ProgressDialog progressDialog = ProgressDialog.show(
                    this,
                    getString(R.string.progress_sing_in_title),
                    getString(R.string.progress_sing_in_text),
                    true);

            User user = new User(mName, mEmail, mUsername, mPassword);
            ParseUser parseUser = user.getParseUser();
            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Log.d(TAG, "Sign in successful (parse.com)");
                    } else {
                        Log.e(TAG, "Sign in error (parse.com)");
                        Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }

    private boolean validateFields() {
        boolean isCorrect = true;
        if(mName.length()==0){
            mName.setError("Name is required");
            isCorrect = false;
        }
        if(mEmail.length()==0){
            mEmail.setError("E-mail is required");
            isCorrect = false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail.getText()).matches()){
            mEmail.setError("The e-mail is in incorrect format");
            isCorrect = false;
        }
        if(mUsername.length()==0){
            mUsername.setError("Username is required");
            isCorrect = false;
        }
        if(mPassword.length()==0){
            mPassword.setError("Password is required");
            isCorrect = false;
        }else if(!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())){
            mPassword.setError("Password fields must match");
            mConfirmPassword.setError("Password fields must match");
            isCorrect = false;
        }
        return isCorrect;
    }


}
