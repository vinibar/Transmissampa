package me.biuti.transmissampa.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import com.parse.ParseUser;

/**
 * Created by vinibar on 4/16/15.
 */
public class User implements Parcelable {

    private String mName;
    private String mEmail;
    private String mUsername;
    private String mPassword;
    private ParseUser mParseUser;

    public User(EditText name, EditText email, EditText username, EditText password){
        mName = name.getText().toString();
        mEmail = email.getText().toString();
        mUsername = username.getText().toString();
        mPassword = password.getText().toString();

        setParses();
    }

    public User(ParseUser user){
        mName = user.getString("name");
        mEmail = user.getEmail();
        mUsername = user.getUsername();
        mParseUser = user;
    }

    private void setParses() {
        mParseUser = new ParseUser();
        mParseUser.put("name", mName);
        mParseUser.setUsername(mUsername);
        mParseUser.setEmail(mEmail);
        mParseUser.setPassword(mPassword);
    }

    public User(String name, String email, String username, String password){
        mName = name;
        mEmail = email;
        mUsername = username;
        mPassword = password;

        mParseUser = new ParseUser();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public ParseUser getParseUser(){
        return mParseUser;
    }


    protected User(Parcel in) {
        mName = in.readString();
        mEmail = in.readString();
        mUsername = in.readString();
        mPassword = in.readString();
        //mParseUser = (ParseUser) in.readValue(ParseUser.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeString(mUsername);
        dest.writeString(mPassword);
        //dest.writeValue(mParseUser);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}