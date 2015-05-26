package me.biuti.transmissampa.ui.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

public class AddEventActivity extends ActionBarActivity {

    private static final String TAG = AddEventActivity.class.getSimpleName();
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.btnAdd)
    FloatingActionButton mBtnAdd;
    @InjectView(R.id.etTitle)
    EditText mTitle;
    @InjectView(R.id.etDescription)
    EditText mDescription;
    private Event mEvent;
    private Broadcast mBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_add_event));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.btnAdd)
    void addEvent(View view) {


        final ProgressDialog progressDialog = ProgressDialog.show(
                this,
                "Please wait",
                "Creating event...",
                true);


        mEvent = new Event(mTitle.getText().toString(),
                mDescription.getText().toString(),
                ParseUser.getCurrentUser());

        mEvent.getParseEvent().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    progressDialog.setMessage("Starting broadcast...");
                    mBroadcast = new Broadcast(mEvent, ParseUser.getCurrentUser());
                    mBroadcast.getParseBroadcast().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(AddEventActivity.this, BroadcastActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("BROADCAST", mBroadcast);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(AddEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });

    }

}
