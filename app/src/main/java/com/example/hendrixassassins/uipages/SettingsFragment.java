package com.example.hendrixassassins.uipages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.GMailSender;
import com.example.hendrixassassins.game.Game;

import java.util.GregorianCalendar;

import javax.mail.AuthenticationFailedException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View viewGetter;
    private Button exportCSVButton;
    private Game game;
    private AgentList agentList;
    private static final AgentFileHelper agentFileHelper = new AgentFileHelper();
    private static final String ARG_PARAM1 = "email";

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGetter = inflater.inflate(R.layout.fragment_settings, container, false);
        setUpUiComponents();
        exportCSVFileButtonListener();
        return viewGetter;
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
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void setUpUiComponents(){
        exportCSVButton = viewGetter.findViewById(R.id.export_file_button);
    }

    private void exportCSVFileButtonListener(){
        exportCSVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCSVEmail();
                exportCSVButton.setText("CSV sent");
            }
        });
    }


    private void sendCSVEmail(){
        final Email message = getCSVEmail();
        Log.e("QQQ", message.getBody());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //GMailSender sender = new GMailSender("HendrixAssassinsApp", "AssassinsTest1");
                    // TODO uncomment this to send emails again:
                    GMailSender sender = new GMailSender();
                    sender.sendMail(message);
                } catch (AuthenticationFailedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Email getCSVEmail(){
        return new Email(game.getEmail(), "CSV of Agents",
                writeCSVEmail());
    }

    private String writeCSVEmail(){
        return agentFileHelper.getAgentListFileString(agentList);
    }

}
