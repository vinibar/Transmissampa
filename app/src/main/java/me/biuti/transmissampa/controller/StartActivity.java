package me.biuti.transmissampa.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.biuti.transmissampa.R;
import me.biuti.transmissampa.controller.SignInActivity;


public class StartActivity extends Activity {

    @InjectView(R.id.tvSignIn) TextView mSignIn;
    @InjectView(R.id.tvLogIn) TextView mLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.tvSignIn)
    public void startSignInActivity(View view){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tvLogIn)
    public void startLogInActivity(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

}
