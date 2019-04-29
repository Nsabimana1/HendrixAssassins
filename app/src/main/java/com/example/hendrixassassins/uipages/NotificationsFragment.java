package com.example.hendrixassassins.uipages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.hendrixassassins.AgentProfileActivity;
import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.email.Email;
import com.example.hendrixassassins.email.EmailServer;
import com.example.hendrixassassins.email.Notification;
import com.example.hendrixassassins.email.NotificationList;
import com.example.hendrixassassins.game.Game;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

//    private ListView notificationListView;
    private NotificationList notificationList;
    private ArrayList<Notification> allNotifications = new ArrayList<>();

    private ArrayList<Email> inboxEmails = new ArrayList<>();


    public NotificationsFragment() {
        // Required empty public constructor


        notificationList = new NotificationList();
        notificationList.addNotification(new Notification(new Agent("aperson@hendrix.edu", "Patrick"), "Iwant to dodd"));
        notificationList.addNotification(new Notification(new Agent("aperson@hendrix.edu", "kakanana"), "Iwant to dodd"));
        notificationList.addNotification(new Notification(new Agent("aperson@hendrix.edu", "kamnana"), "Iwant to dodd"));
        allNotifications = notificationList.getAllNotifications();
        Log.e("Im in here", "I am called11");
        updateMessages();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_notifications, container, false);
        createListViewAdapter();
        return fragView;
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
        agentFileHelper.writeToFile(game.getAgentFileName(), agentList, this.getContext());
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


    private void createListViewAdapter(){
        ListView notificationListView = fragView.findViewById(R.id.notifications_ListView);
//        NotificationListViewAdapter adapter = new NotificationListViewAdapter<>(this.getContext(),
//                R.layout.test_list_view, allNotifications);

        NotificationListViewAdapter adapter = new NotificationListViewAdapter<>(this.getContext(),
                R.layout.test_list_view, inboxEmails);
        notificationListView.setAdapter(adapter);
        setUpItemClickListener(notificationListView);
    }

    private void setUpItemClickListener(ListView listView){
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I am clicked", "I was clicked");
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

    public void updateMessages(){
        Log.e("Im in here", "I am called11");
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
                inboxEmails = EmailServer.get().getInboxList();
            }
        }).start();
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////        notificationListView = (ListView) view.findViewById(R.id.notifications_ListView);
//    }

}
