package com.example.hendrixassassins.uipages;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.Notification;

import java.util.List;

public class NotificationListViewAdapter<E> extends ArrayAdapter<E> {
    private Context mContext;
    private int mResource;

    public NotificationListViewAdapter(Context context, int resource, List<E> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource =resource;
    }

    static class ViewHolder {
//        Button senderInitials;
//        TextView emailSender;
//        TextView emailSubject;
//        TextView emailSentDate;

        TextView name;
        TextView status;
        TextView score;
        /*
        Define any objects inside custom view
        this includes any TextViews or ImageViews
         */
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("istViewAdapterCreated", "list view adapter Created");

//        Notification notification = (Notification) getItem(position);

        Email notification = (Email) getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        TextView person = convertView.findViewById(R.id.nameTest);
        TextView status = convertView.findViewById(R.id.statusTest);
//        TextView score = convertView.findViewById(R.id.pointsTest);
        viewHolder.name = person;
        viewHolder.status = status;
//        viewHolder.score = score;


//        Button senderInitials = convertView.findViewById(R.id.sender_Initials);
//        TextView emailSender = convertView.findViewById(R.id.sender_email);
//        TextView emailSubject = convertView.findViewById(R.id.email_subject);
//        TextView emailSentDate = convertView.findViewById(R.id.sent_date);

//        viewHolder.senderInitials = senderInitials;
//        viewHolder.emailSender = emailSender;
//        viewHolder.emailSubject = emailSubject;
//        viewHolder.emailSentDate = emailSentDate;
        convertView.setTag(viewHolder);

//        senderInitials.setText(String.valueOf(notification.getSender().charAt(0)));
//        emailSender.setText(notification.getSender().toString());
//        emailSubject.setText(notification.getSubject().toString());
//        emailSentDate.setText(notification.getDate().toString());

        person.setText(notification.getSender().toString());
        status.setText(notification.getSubject().toString());

//        Log.e("I just reached here", "I just reached herrrrrrr");
//        person.setText(notification.getNotifier().getName().toString());
//        status.setText(notification.getNotificationContent().toString());


//        score.setText(String.valueOf(agent.getPointsTotal()));
        /*
        Set up TextViews here from convertView
        and assign them to the ViewHolder
         */

        return convertView;
    }
}
