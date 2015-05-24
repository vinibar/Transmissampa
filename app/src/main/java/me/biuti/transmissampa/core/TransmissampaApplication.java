package me.biuti.transmissampa.core;

import android.app.Application;

import com.parse.Parse;

import me.biuti.transmissampa.util.ParseHashKeys;

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
