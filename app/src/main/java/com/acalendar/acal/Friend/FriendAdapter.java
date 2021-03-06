package com.acalendar.acal.Friend;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.acalendar.acal.R;

import java.util.ArrayList;

public class FriendAdapter extends ArrayAdapter<Friend> {

    private final ArrayList<Friend> friendList;

    public FriendAdapter(Context context, int textViewResourceLayout,
                         int textViewResourceId ,ArrayList<Friend> friendList) {
        super(context,textViewResourceLayout, textViewResourceId, friendList);
        this.friendList = friendList;
                //new ArrayList<Friend>();
        //this.FriendList.addAll(FriendList);
    }

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    private class ViewHolder {
        TextView code;
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.friend_list_item, null);

            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.friend_name);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Friend friend = (Friend) cb.getTag();
                    friend.setSelected(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Friend friend = friendList.get(position);
        holder.code.setText(friend.getName() + "(" + friend.getEmail()+ ")");
        holder.name.setChecked(friend.isSelected());
        holder.name.setTag(friend);

        return convertView;


    }


}
