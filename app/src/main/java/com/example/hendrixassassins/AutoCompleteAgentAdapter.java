package com.example.hendrixassassins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.example.hendrixassassins.agent.Agent;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAgentAdapter<E> extends ArrayAdapter<E> {
    private List<E> displayItems;
    private int resource;


    public AutoCompleteAgentAdapter(Context context, int resource, List<E> objects) {
        super(context, resource, objects);
        this.resource = resource;
        displayItems = new ArrayList<>(objects);
    }


    @Override
    public Filter getFilter() {
        return displayFilter;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView textViewEmail = convertView.findViewById(R.id.autoCompleteAgentName);

        Agent agent = (Agent) getItem(position);

        if(agent != null){
            textViewEmail.setText(agent.getEmail());
        }

        return convertView;
    }

    private Filter displayFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<E> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(displayItems);
            }else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for(E item: displayItems){
                    Agent agent = (Agent) item;
                    if(agent.getEmail().toLowerCase().contains(filterpattern)){
                        suggestions.add(item);

                    }
                }
            }
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List<E>) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Agent) resultValue).getEmail();
        }
    };
}
