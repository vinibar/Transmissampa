package me.biuti.transmissampa.ui.Fragments;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rey.material.widget.ProgressView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.biuti.transmissampa.R;
import me.biuti.transmissampa.adapter.EventListAdapter;
import me.biuti.transmissampa.model.Event;
import me.biuti.transmissampa.ui.Activities.ViewEventActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends ListFragment {

    public static final String TITLE = "Events";
    public static final String TAG = EventsFragment.class.getSimpleName();
    private Event[] mEvents;
    private EventListTask mEventListTask;

    @InjectView(android.R.id.list) ListView mList;
    @InjectView(android.R.id.empty) TextView mEmptyView;
    @InjectView(R.id.progress_circular) ProgressView mProgressView;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> eventsList, ParseException e) {

                if (e == null) {
                    if(mEventListTask == null) {
                        mEventListTask = new EventListTask(eventsList);
                    }else{
                        mEventListTask.cancel(true);
                        mEventListTask.setEventsList(eventsList);
                    }
                    mEventListTask.execute();
                } else {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void showList() {
        EventListAdapter adapter = new EventListAdapter(getListView().getContext(), mEvents);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ViewEventActivity.class);
                intent.putExtra("EVENT_VIEW", mEvents[position]);
                startActivity(intent);
            }
        });
        mList.setEmptyView(mEmptyView);
        mProgressView.setVisibility(ProgressView.INVISIBLE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mEventListTask != null) {
            mEventListTask.cancel(true);
        }
    }

    public class EventListTask extends AsyncTask {

        private List<ParseObject> mEventsList;
        private boolean mRunning;

        public EventListTask(List<ParseObject> eventsList) {
            mEventsList = eventsList;
        }

        public void setEventsList(List<ParseObject> eventsList){
            mEventsList = eventsList;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            mRunning = true;
            mEvents = new Event[mEventsList.size()];
            for (int i = 0; i < mEventsList.size(); i++) {
                //Get Parse Event
                ParseObject parseEvent = mEventsList.get(i);
                // Put the event into an array
                mEvents[i] = new Event(parseEvent);
                Log.d(  TAG, "Event: " + mEvents[i].getTitle());

            }

            return null;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mRunning = false;
            showList();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mRunning = false;
        }

        public boolean isRunning(){
            return mRunning;
        }
    }
}
