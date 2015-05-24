package me.biuti.transmissampa.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import me.biuti.transmissampa.ui.Fragments.EventsFragment;

/**
 * Created by vinibar on 4/24/15.
 */
public class EventFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private EventsFragment mEventsFragment;

    public EventFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if(mEventsFragment == null){
                    mEventsFragment = new EventsFragment();
                }
                return mEventsFragment;
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public String getPageTitle(int position) {
        switch (position){
            case 0:
                return mEventsFragment.TITLE;
            default: return null;
        }
    }
}
