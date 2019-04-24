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
        CheckBox addPlayer;
        /*
        Define any objects inside custom view
        this includes any TextViews or ImageViews
         */
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        TextView person = convertView.findViewById(R.id.emailName);
        TextView subject = convertView.findViewById(R.id.emailSubject);
        CheckBox score = convertView.findViewById(R.id.isPlaying);


        viewHolder.email = person;
        viewHolder.subject = subject;
        viewHolder.addPlayer = score;
        convertView.setTag(viewHolder);

        /*
        Set up TextViews here from convertView
         */

        return convertView;
    }
}
