package com.example.hendrixassassins.uipages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hendrixassassins.IncomingEmailListViewAdapter;
import com.example.hendrixassassins.R;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.GmailTestActivity;
import com.example.hendrixassassins.email.MessageReader;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

public class SetUpGameFragment extends Fragment {
    private Button createGameButton, verifyAllAgentsButton, refreshEmails;
    private ListView incomingEmails;
    private ArrayList<Email> unread_filtered_emails;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private View fragView;
    private IncomingEmailListViewAdapter<Email> incomingEmailListViewAdapter;

    public SetUpGameFragment(){
        //For some reason I need this
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        unread_filtered_emails = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.setup_game_fragment, container, false);

        getIDs();
        createGameButtonListener();
        verifyAllAgentsButtonListener();
        refreshEmailsButtonListener();
        setupIncomingEmailsListView();



        return fragView;
    }

    private void refreshEmailsButtonListener() {
        refreshEmails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabSent();
            }
        });
    }

    private void grabSent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String date, sender, subject, display;
                    boolean read;
                    MessageReader reader = new MessageReader("HendrixAssassinsApp", "AssassinsTest1");
                    ArrayList<Email> sent = reader.getUnreadMessages();
                    unread_filtered_emails.addAll(sent);
                    SetUpGameFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            incomingEmailListViewAdapter.notifyDataSetChanged();
                        }
                    });
                    //this crashs the project


                } catch (MessagingException | IOException e) {
                    Log.e("SetUpGameFragment", "Failure getting mail: " + e.toString());
                }
            }
        }).start();
    }

    private void setupIncomingEmailsListView() {
        incomingEmailListViewAdapter = new IncomingEmailListViewAdapter<>(this.getContext(),
                R.layout.incoming_emails_start_game, unread_filtered_emails);

        incomingEmails.setAdapter(incomingEmailListViewAdapter);

    }


    private void verifyAllAgentsButtonListener() {
        verifyAllAgentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createGameButtonListener() {
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getIDs() {
        createGameButton = fragView.findViewById(R.id.createGame);
        verifyAllAgentsButton = fragView.findViewById(R.id.verifyAllAgents);
        incomingEmails = fragView.findViewById(R.id.listofIncomingEmails);
        refreshEmails = fragView.findViewById(R.id.refresh_emails_1);
    }




}
