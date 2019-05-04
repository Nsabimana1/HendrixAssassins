package com.example.hendrixassassins.uipages;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.hendrixassassins.R;
import com.example.hendrixassassins.email.Email;

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
        TextView emailSenderInitials;
        TextView emailSender;
        TextView emailSubject;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Email notification = (Email) getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        TextView emailSenderInitials = convertView.findViewById(R.id.emailSender_initials);
        TextView emailSender = convertView.findViewById(R.id.emailSender);
        TextView emailSubject = convertView.findViewById(R.id.emailSubject);
        viewHolder.emailSenderInitials = emailSenderInitials;
        viewHolder.emailSender = emailSender;
        viewHolder.emailSubject = emailSubject;
        convertView.setTag(viewHolder);

        emailSenderInitials.setText(notification.getSender().substring(0, 1));
        emailSender.setText(notification.getSender());
        emailSubject.setText(notification.getSubject());
        return convertView;
    }
}
