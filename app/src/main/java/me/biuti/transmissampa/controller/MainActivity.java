package me.biuti.transmissampa.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.biuti.transmissampa.R;


public class MainActivity extends ActionBarActivity{

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.btnAdd) ImageButton mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setTitle("Transmissampa");

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_logout:
                        ParseUser.logOut();
                        Intent intent = new Intent(MainActivity.this, StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;
                    default:
                        return true;
                }
            }
        });

        // Check if user is logged
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnAdd)
    void addEvent(View view){
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }
}
