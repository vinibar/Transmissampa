package me.biuti.transmissampa.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.biuti.transmissampa.R;

public class AddEventActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.btnAdd) ImageButton mBtnAdd;
    @InjectView(R.id.etTitle) EditText mTitle;
    @InjectView(R.id.etDescription) EditText mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_add_event));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnAdd)
    void addEvent(View view){

          if (validateFields()){

              final ProgressDialog progressDialog = ProgressDialog.show(
                      this,
                      "Please wait...",
                      "Creating event",
                      true);

              ParseObject event = new ParseObject("Event");
              event.put("eventTitle", mTitle.getText().toString());
              event.put("eventDescription", mDescription.getText().toString());
              ParseRelation<ParseUser> createdBy = event.getRelation("createdBy");
              createdBy.add(ParseUser.getCurrentUser());
              event.saveInBackground(new SaveCallback() {
                  @Override
                  public void done(ParseException e) {
                      if (e == null) {

                      } else {
                          Toast.makeText(AddEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                      }
                      progressDialog.dismiss();
                  }
              });

          }

//        Intent intent = new Intent(AddEventActivity.this, BroadcastActivity.class);
//        startActivity(intent);
    }

    private boolean validateFields() {
        boolean isCorrect = true;
        if(mTitle.length()==0){
            mTitle.setError("Title is required");
            isCorrect = false;
        }

        if(mDescription.length()==0){
            mDescription.setError("Description is required");
            isCorrect = false;
        }

        return isCorrect;
    }

}
