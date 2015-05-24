package me.biuti.transmissampa.ui.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rey.material.widget.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.biuti.transmissampa.R;
import me.biuti.transmissampa.model.Broadcast;
import me.biuti.transmissampa.model.Event;

public class ViewEventActivity extends ActionBarActivity {

    private static final String TAG = ViewEventActivity.class.getSimpleName();
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.tvTitle) TextView mTitle;
    @InjectView(R.id.tvDescription) TextView mDescription;
    @InjectView(R.id.tvCreatedBy) TextView mCreatedBy;
    @InjectView(R.id.btnBroadcast) FloatingActionButton mBtnBroadcast;
    private Event mEvent;
    private Broadcast mBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_view_event));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mEvent = intent.getParcelableExtra("EVENT_VIEW");

        mTitle.setText(mEvent.getTitle());
        mDescription.setText(mEvent.getDescription());
        mCreatedBy.setText(mEvent.getUser().getName());

    }

    @OnClick(R.id.btnBroadcast)
    void broadcastEvent(View view) {

        final ProgressDialog progressDialog = ProgressDialog.show(
                this,
                "Please wait",
                "Starting broadcast...",
                true);

        mBroadcast = new Broadcast(mEvent, ParseUser.getCurrentUser());
        mBroadcast.getParseBroadcast().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                progressDialog.dismiss();
                if(e==null){
                    Intent intent = new Intent(ViewEventActivity.this, BroadcastActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("BROADCAST", mBroadcast);
                    startActivity(intent);
                }else{
                    Toast.makeText(ViewEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
