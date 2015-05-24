package me.biuti.transmissampa.ui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.biuti.transmissampa.R;


public class StartActivity extends Activity {

    @InjectView(R.id.btnSignIn)Button mSignIn;
    @InjectView(R.id.btnLogIn) Button mLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btnSignIn)
    public void startSignInActivity(View view){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnLogIn)
    public void startLogInActivity(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

}
