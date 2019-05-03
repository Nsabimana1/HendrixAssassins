package com.example.hendrixassassins.uipages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.EmailServer;
import com.example.hendrixassassins.game.Game;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

public class NotificationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "email";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final AgentFileHelper agentFileHelper = new AgentFileHelper();;
    private OnFragmentInteractionListener mListener;
    private AgentList agentList;
    private Game game;
    private View fragView;
    private Button showListView;

    private ArrayList<Email> inboxEmails = new ArrayList<>();
    private ListView notificationListView;
    private  NotificationListViewAdapter adapter;

<<<<<<< HEAD
    public NotificationsFragment() { }
=======

    public NotificationsFragment() {

    }

>>>>>>> 50d8d025450985a32c6ad1b8303a8b234cce79e5

    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String handlerEmail = getArguments().getString(ARG_PARAM1);
            game = new Game(handlerEmail);
            // TODO replace testFile.csv with game.getAgentFileName
            agentList = agentFileHelper.readFromFile(game.getAgentFileName(), this.getContext());
        }
        if(showListView != null) {
                updateMessages();

//            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        displayToast("Refreshing notifications...");

        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_notifications, container, false);
        showListView = fragView.findViewById(R.id.refresh_notification_button);
        updateMessages();
        createListViewAdapter();
        refreshListener();
        return fragView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void refreshListener(){
        showListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToast("Refreshing notifications...");
                updateMessages();
//                adapter.notifyDataSetChanged();

//                createListViewAdapter();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        agentFileHelper.writeToFile(game.getAgentFileName(), agentList, this.getContext());
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void createListViewAdapter(){
        notificationListView = fragView.findViewById(R.id.notifications_ListView);
        adapter = new NotificationListViewAdapter<>(this.getContext(),
                R.layout.notifcation_item_view, inboxEmails);
        notificationListView.setAdapter(adapter);
        setUpItemClickListener(notificationListView);
    }

    private void setUpItemClickListener(final ListView listView){
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Email emailBody = inboxEmails.get(position);
            toNotificationContentActivity(emailBody);
        }
        });
    }

    private void toNotificationContentActivity(Email emailToView) {
        Intent forwardIntent = new Intent(getActivity(), NotificationTemplateViewActivity.class);
        forwardIntent.putExtra("clickedUserEmail", emailToView);
        startActivity(forwardIntent);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void updateMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EmailServer.get().refreshInboxMessages();
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<Email> emails = EmailServer.get().getInboxList();
                inboxEmails.clear();
                inboxEmails.addAll(emails);
<<<<<<< HEAD
=======


                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });


>>>>>>> 50d8d025450985a32c6ad1b8303a8b234cce79e5
                if(adapter != null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
<<<<<<< HEAD
=======

>>>>>>> 50d8d025450985a32c6ad1b8303a8b234cce79e5
            }
        }).start();
    }

    
    public void displayToast(String message){
        Context context = getContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
