package me.biuti.transmissampa.ui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseException;

import me.biuti.transmissampa.R;
import me.biuti.transmissampa.model.Broadcast;

public class BroadcastActivity extends Activity {

    private Broadcast mBroadcast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        Intent intent = getIntent();
        mBroadcast = intent.getParcelableExtra("BROADCAST");


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Create broadcast item

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mBroadcast.getParseBroadcast().delete();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Eliminate broadcast item
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mBroadcast.getParseBroadcast().delete();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
