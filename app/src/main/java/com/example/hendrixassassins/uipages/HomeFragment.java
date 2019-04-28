package com.example.hendrixassassins.uipages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.hendrixassassins.AgentProfileActivity;
import com.example.hendrixassassins.MainActivity;
import com.example.hendrixassassins.UItestcompnents.CustomListViewAdapter;
import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.game.Game;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "email";

    private View fragView;
    private ListView listView;
    private Button statusFilter, killsFilter, pointsFilter, alphabeticalFilter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final AgentFileHelper agentFileHelper = new AgentFileHelper();;
    private OnFragmentInteractionListener mListener;
    private AgentList agentList;
    private Game game;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            agentList = agentFileHelper.readFromFile("testFile.csv", this.getContext());
        }
    }

    private void findButtons(){
        statusFilter = fragView.findViewById(R.id.statusButton);
        killsFilter = fragView.findViewById(R.id.KillsButton);
        pointsFilter = fragView.findViewById(R.id.PointsButton);
        alphabeticalFilter = fragView.findViewById(R.id.AlphabetButton);
    }

    private void setupButtons() {
        findButtons();
        setButtonListeners();
    }

    private void setButtonListeners() {
        setStatusFilterListener();
        setKillsFilterListener();
        setPointsFilterListener();
        setAlphabeticalFilterListener();
    }

    private void setAlphabeticalFilterListener(){
        alphabeticalFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agentList.sortNamesAlphabetically();
                agentList.reverse();
                createListViewAdapter();
            }
        });
    }

    private void setStatusFilterListener(){
        statusFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setKillsFilterListener(){
        statusFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agentList.sortByPersonalKills();

                createListViewAdapter();
            }
        });
    }

    private void setPointsFilterListener(){
        statusFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agentList.sortByPointsTotal();
                createListViewAdapter();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_home, container, false);
        createListViewAdapter();
        setupButtons();
        // Inflate the layout for this fragment
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

//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
        ListView listView = fragView.findViewById(R.id.agentList);
        Log.e("all agents", String.valueOf(agentList.getAllAgents().size()));
        CustomListViewAdapter adapter = new CustomListViewAdapter<>(this.getContext(),
                R.layout.test_list_view, agentList.getAllAgents());
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoAgentProfile();
            }


        });
    }

    private void gotoAgentProfile() {
        Intent userListView = new Intent(getActivity(), AgentProfileActivity.class);
        startActivity(userListView);
    }
}
