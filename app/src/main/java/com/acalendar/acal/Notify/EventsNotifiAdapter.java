package com.acalendar.acal.Notify;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.acalendar.acal.R;

import java.util.ArrayList;

public class EventsNotifiAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> NotifyList;

    public EventsNotifiAdapter(Context context, int textViewResourceLayout,
                               int textViewResourceId , ArrayList<String> List) {
        super(context,textViewResourceLayout, textViewResourceId, List);
        this.NotifyList = List;
    }

    public ArrayList<String> getFriendList() {
        return new ArrayList<String>(NotifyList);
    }

    private class ViewHolder {
        TextView event;
        Button accept;
        Button decline;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder = new ViewHolder();;
        Log.v("ConvertView", String.valueOf(position));

        LayoutInflater vi = (LayoutInflater)getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        convertView = vi.inflate(R.layout.notifi_events_item, null);

        holder.event = (TextView) convertView.findViewById(R.id.noti_events_item);
        holder.accept = (Button) convertView.findViewById(R.id.noti_events_accept);
        holder.decline = (Button) convertView.findViewById(R.id.noti_events_delete);
        convertView.setTag(holder);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 11/21/2016 request related handler
                holder.accept.setText("accepted");
            }
        });

        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 11/21/2016 request related handler
                holder.decline.setText("declined");
            }
        });



        String eventView = NotifyList.get(position);
        holder.event.setText(eventView);

        return convertView;


    }
}