package me.biuti.transmissampa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by vinibar on 4/22/15.
 */
public class Event implements Parcelable {

    private String mTitle;
    private String mDescription;
    private User mUser;
    private static ParseObject mParseEvent;
    private static Event[] mEvents;

    public Event(String title, String description, ParseUser user){
        mTitle = title;
        mDescription = description;
        mUser = new User(user);
        //mParseEvent = new ParseObject("Event");
        setParses();
    }

    public Event(ParseObject parseEvent){
        mParseEvent = parseEvent;
        mTitle = mParseEvent.getString("eventTitle");
        mDescription = mParseEvent.getString("eventDescription");
        ParseRelation<ParseUser> createdBy = mParseEvent.getRelation("createdBy");
        try {
            mUser = new User(createdBy.getQuery().getFirst());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public static Event[] getmEvents() {
        return mEvents;
    }

    public static void setmEvents(Event[] mEvents) {
        Event.mEvents = mEvents;
    }

    public void setParses(){
        if(mParseEvent == null){
            mParseEvent = new ParseObject("Event");
        }
        mParseEvent.put("eventTitle", mTitle);
        mParseEvent.put("eventDescription", mDescription);
        ParseRelation<ParseUser> createdBy = mParseEvent.getRelation("createdBy");
        createdBy.add(mUser.getParseUser());
    }

    public ParseObject getParseEvent(){

        return mParseEvent;
    }

    protected Event(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mUser = (User) in.readValue(User.class.getClassLoader());
        //mParseEvent = (ParseObject) in.readValue(ParseObject.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeValue(mUser);
        //dest.writeValue(mParseEvent);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}