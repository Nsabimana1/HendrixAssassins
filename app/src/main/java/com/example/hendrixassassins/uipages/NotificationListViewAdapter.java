package com.example.hendrixassassins.uipages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;
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
        Notification notification = (Notification) getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        TextView person = convertView.findViewById(R.id.nameTest);
        TextView status = convertView.findViewById(R.id.statusTest);
        TextView score = convertView.findViewById(R.id.pointsTest);


        viewHolder.name = person;
        viewHolder.status = status;
        viewHolder.score = score;
        convertView.setTag(viewHolder);


        person.setText(notification.getNotifier().getName().toString());
        status.setText(notification.getNotificationContent().toString());
//        score.setText(String.valueOf(agent.getPointsTotal()));
        /*
        Set up TextViews here from convertView
        and assign them to the ViewHolder
         */

        return convertView;
    }
}
