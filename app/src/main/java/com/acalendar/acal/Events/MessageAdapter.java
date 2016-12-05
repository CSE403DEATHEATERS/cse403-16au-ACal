package com.acalendar.acal.Events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acalendar.acal.R;

import java.util.ArrayList;
import java.util.Date;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.RecyclerViewHoler>{
    private ArrayList<String> user, message;
    private ArrayList<Date> time;

    public MessageAdapter(ArrayList<String> user, ArrayList<String> userMessages, ArrayList<Date> time){
        this.user = user;
        this.message = userMessages;
        this.time = time;
    }

    public void add (String user, String mes, Date time) {
        this.user.add(user);
        this.message.add(mes);
        this.time.add(time);
    }

    public void clear() {
        user.clear();
        message.clear();
        time.clear();
    }

    @Override
    public RecyclerViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_item, parent, false);
        RecyclerViewHoler recyclerViewHoler = new RecyclerViewHoler(view);
        return recyclerViewHoler;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHoler holder, int position) {
        holder.userView.setText(user.get(position) + ":");
        holder.messageView.setText(message.get(position));
        holder.timeView.setText(time.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public static class RecyclerViewHoler extends RecyclerView.ViewHolder {

        TextView userView, messageView, timeView;
        public RecyclerViewHoler(View itemView) {
            super(itemView);
            timeView = (TextView) itemView.findViewById(R.id.posts_time);
            userView = (TextView) itemView.findViewById(R.id.posts_user);
            messageView = (TextView) itemView.findViewById(R.id.posts_message);

        }
    }
}
