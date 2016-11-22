package com.acalendar.acal.Notification;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.acalendar.acal.Notification.Invitation;
import com.acalendar.acal.R;

import java.util.ArrayList;

public class InvitationAdapter extends ArrayAdapter<Invitation> {

    private final ArrayList<Invitation> invitationList;

    public InvitationAdapter(Context context, int textViewResourceLayout,
                         int textViewResourceId ,ArrayList<Invitation> invitationList) {
        super(context,textViewResourceLayout, textViewResourceId, invitationList);
        this.invitationList = invitationList;
        //new ArrayList<Invitation>();
        //this.InvitationList.addAll(InvitationList);
    }

    public ArrayList<Invitation> getInvitationList() {
        return new ArrayList<Invitation>(invitationList);
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
            convertView = vi.inflate(R.layout.invitation_item, null);

            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.invitation);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox2);
            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Invitation invitation = (Invitation) cb.getTag();
                    invitation.setSelected(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Invitation invitation = invitationList.get(position);
        holder.code.setText(invitation.toString() );
        holder.name.setChecked(invitation.isSelected());
        holder.name.setTag(invitation);

        return convertView;


    }


}
