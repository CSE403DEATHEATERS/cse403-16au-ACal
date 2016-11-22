package com.acalendar.acal.Events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.RecyclerViewHoler>{
    private ArrayList<String> user, message;
    private String userName;

    public MessageAdapter(ArrayList<String> user, ArrayList<String> userMessage){
        this.user = user;
        message = userMessage;
        userName = LoginedAccount.getUserName();
    }

    public void add (String mes) {
        user.add(LoginedAccount.getUserName().toString());
        message.add(mes);
    }

    @Override
    public RecyclerViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_item, parent, false);
        RecyclerViewHoler recyclerViewHoler = new RecyclerViewHoler(view);
        return recyclerViewHoler;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHoler holder, int position) {
        holder.userView.setText(user.get(position));
        holder.messageView.setText(message.get(position));
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public static class RecyclerViewHoler extends RecyclerView.ViewHolder {

        TextView userView, messageView;
        public RecyclerViewHoler(View itemView) {
            super(itemView);
            userView = (TextView) itemView.findViewById(R.id.posts_user);
            messageView = (TextView) itemView.findViewById(R.id.posts_message);

        }
    }
}
