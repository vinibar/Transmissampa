package me.biuti.transmissampa;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by vinibar on 4/16/15.
 */
public class TransmissampaApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ParseHashKeys.KEY1, ParseHashKeys.KEY2);
    }
}
