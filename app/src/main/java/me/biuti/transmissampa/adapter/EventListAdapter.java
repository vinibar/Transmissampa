package me.biuti.transmissampa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import me.biuti.transmissampa.R;
import me.biuti.transmissampa.model.Event;

/**
 * Created by vinibar on 4/26/15.
 */
public class EventListAdapter extends BaseAdapter {

    private Context mContext;
    private Event[] mEvents;

    public EventListAdapter(Context context, Event[] events){
        mContext = context;
        mEvents = events;
    }

    @Override
    public int getCount() {
        return mEvents.length;
    }

    @Override
    public Object getItem(int position) {
        return mEvents[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.event_list_item, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Event event = mEvents[position];
        //if (event != null) {
            holder.tvTitle.setText(event.getTitle());
            holder.tvDescription.setText(event.getDescription());
        //}

        return convertView;
    }

    private static class ViewHolder{
        TextView tvTitle;
        TextView tvDescription;
    }


}
