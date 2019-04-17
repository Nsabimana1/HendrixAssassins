package com.example.hendrixassassins;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;


public class CustomListViewAdapter<E> extends ArrayAdapter<E> {

    private Context mContext;
    private int mResource;

    public CustomListViewAdapter(Context context, int resource, List<E> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource =resource;
    }

    static class ViewHolder {
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

        /*
        Set up TextViews here from convertView
        and assign them to the ViewHolder
         */


        convertView.setTag(viewHolder);

        return convertView;
    }
}
