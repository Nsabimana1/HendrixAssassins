package com.example.hendrixassassins.uipages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.GMailSender;
import com.example.hendrixassassins.email.GmailLogin;
import com.example.hendrixassassins.game.Game;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final AgentFileHelper agentFileHelper = new AgentFileHelper();;
    private OnFragmentInteractionListener mListener;
    private AgentList agentList;
    private Game game;
    private ImageButton sendButton;
    private CheckBox alive, dead, frozen, purged, withDrown;
    private TextInputEditText emailSubjectView, emailBodyView;
    private View componentsViewsGetter;
//    private ArrayList<Email> selectedEmailAddresses = new ArrayList<>();
    private ArrayList<Agent> allAgents = new ArrayList<>();

    public EmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmailFragment newInstance(String param1, String param2) {
        EmailFragment fragment = new EmailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String handlerEmail = getArguments().getString(ARG_PARAM1);
//            game = new Game(handlerEmail);
//            // TODO replace testFile.csv with game.getAgentFileName
//            agentList = agentFileHelper.readFromFile("testFile.csv", this.getContext());

            game = new Game(GmailLogin.email);
            // TODO replace testFile.csv with game.getAgentFileName
            agentList = agentFileHelper.readFromFile(game.getAgentFileName(), this.getContext());
        }
        loadAgents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        componentsViewsGetter = inflater.inflate(R.layout.fragment_email, container, false);
        setUpComponents();
        sendButtonListener();
        return componentsViewsGetter;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
//        agentFileHelper.writeToFile(game.getAgentFileName(), agentList, this.getContext());
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setUpComponents(){
        alive = componentsViewsGetter.findViewById(R.id.alive_box);
        dead = componentsViewsGetter.findViewById(R.id.dead_box);
        frozen = componentsViewsGetter.findViewById(R.id.frozen_box);
        purged = componentsViewsGetter.findViewById(R.id.purged_box);
        withDrown = componentsViewsGetter.findViewById(R.id.withdraw_box);
        sendButton = componentsViewsGetter.findViewById(R.id.reply_button);
        emailSubjectView = componentsViewsGetter.findViewById(R.id.email_subject_view);
        emailBodyView = componentsViewsGetter.findViewById(R.id.email_body_View);
    }


    private void loadAgents(){
        agentList = agentFileHelper.readFromFile(game.getAgentFileName(), this.getContext());
        allAgents = agentList.getAllAgents();
    }

    private ArrayList<String> getEmailAddressesByStatus(AgentStatus agentStatus, ArrayList<Agent> emailList){
        ArrayList<String> requiredEmailAddresses = new ArrayList<>();
        for(Agent agent: emailList){
            if(agent.getStatus().equals(agentStatus)){
                requiredEmailAddresses.add(agent.getEmail());
            }
        }
        return requiredEmailAddresses;
    }

    private ArrayList getSelectedEmails(){
        ArrayList<String> selectedEmailAddresses = new ArrayList<>();
        if(alive.isChecked()){
            selectedEmailAddresses.addAll(getEmailAddressesByStatus(AgentStatus.ALIVE, allAgents));
        }
        if(dead.isChecked()){
            selectedEmailAddresses.addAll(getEmailAddressesByStatus(AgentStatus.DEAD, allAgents));
        }
        if(frozen.isChecked()){
            selectedEmailAddresses.addAll(getEmailAddressesByStatus(AgentStatus.FROZEN, allAgents));
        }
        if(purged.isChecked()){
            selectedEmailAddresses.addAll(getEmailAddressesByStatus(AgentStatus.PURGED, allAgents));
        }
        if(withDrown.isChecked()){
            selectedEmailAddresses.addAll(getEmailAddressesByStatus(AgentStatus.WITHDRAWN, allAgents));
        }
        return selectedEmailAddresses;
    }

    private void clearTemplate(){
        emailBodyView.clearComposingText();
        emailSubjectView.clearComposingText();
    }

    private void sendEmail(Email email){
        try {
            GMailSender sender = new GMailSender();
            sender.sendMail(email);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayToast("Email Sent :)");
                }});
            clearTemplate();
        } catch (Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayToast("Failed to send ):");
                }});
        }
    }
    private void sendButtonListener(){
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadAgents();
                        if (getSelectedEmails().isEmpty()){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayToast("Please select recipients");
                                }
                            });
                        }else {
                            Email email = new Email(getSelectedEmails(),
                                    emailSubjectView.getText().toString(), emailBodyView.getText().toString());
                            sendEmail(email);
                        }
                    }
                }).start();
            }
        });
    }

    public void displayToast(String message){
        Context context = getContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
