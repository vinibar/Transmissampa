package me.biuti.transmissampa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by vinibar on 5/24/15.
 */
public class Broadcast implements Parcelable{

    private static ParseObject mParseBroadcast;
    private User mUser;
    private Event mEvent;

    public Broadcast(Event event, ParseUser user){

        mEvent = event;
        mUser = new User(user);
        mParseBroadcast = new ParseObject("Broadcast");

        ParseRelation<ParseUser> relationCreatedBy = mParseBroadcast.getRelation("createdBy");
        relationCreatedBy.add(mUser.getParseUser());
        ParseRelation<ParseObject> relationEvent = mParseBroadcast.getRelation("event");
        relationEvent.add(mEvent.getParseEvent());

    }

    public ParseObject getParseBroadcast(){
        return mParseBroadcast;
    }


    protected Broadcast(Parcel in) {
        mUser = (User) in.readValue(User.class.getClassLoader());
        mEvent = (Event) in.readValue(Event.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mUser);
        dest.writeValue(mEvent);
        //dest.writeValue(mParseEvent);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Broadcast> CREATOR = new Parcelable.Creator<Broadcast>() {
        @Override
        public Broadcast createFromParcel(Parcel in) {
            return new Broadcast(in);
        }

        @Override
        public Broadcast[] newArray(int size) {
            return new Broadcast[0];
        }

    };
}
