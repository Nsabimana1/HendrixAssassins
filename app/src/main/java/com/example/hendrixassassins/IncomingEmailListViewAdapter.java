package com.example.hendrixassassins;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.email.Email;

import java.util.List;


public class IncomingEmailListViewAdapter<E> extends ArrayAdapter<E> {

    private Context mContext;
    private int mResource;

    public IncomingEmailListViewAdapter(Context context, int resource, List<E> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource =resource;
    }

    public static class ViewHolder {
        TextView email;
        TextView subject;
        /*
        Define any objects inside custom view
        this includes any TextViews or ImageViews
         */
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        Email email = (Email) getItem(position);
        String emailName = email.getSender();
        String emailDate = email.getDate().toString();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        TextView person = convertView.findViewById(R.id.emailName);
        TextView subject = convertView.findViewById(R.id.emailSubject);


        viewHolder.email = person;
        viewHolder.subject = subject;
        convertView.setTag(viewHolder);

        person.setText(emailName);
        subject.setText(emailDate);

        return convertView;
    }
}
