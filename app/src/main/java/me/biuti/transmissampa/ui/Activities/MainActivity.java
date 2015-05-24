package me.biuti.transmissampa.ui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;
import com.rey.material.widget.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.biuti.transmissampa.R;
import me.biuti.transmissampa.adapter.EventFragmentAdapter;
import me.biuti.transmissampa.model.User;


public class MainActivity extends Activity{

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.btnAdd) FloatingActionButton mAdd;
    @InjectView(R.id.pager) ViewPager mPager;
    private User mUser;

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
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{
            mUser = new User(currentUser);
            mPager.setAdapter(new EventFragmentAdapter(this, getFragmentManager()));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btnAdd)
    void addEvent(View view){
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }


}
