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

import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Spinner;

import com.example.hendrixassassins.AgentProfileActivity;
import com.example.hendrixassassins.MainActivity;
import com.example.hendrixassassins.UItestcompnents.AutoCompleteAgentAdapter;
import com.example.hendrixassassins.UItestcompnents.CustomListViewAdapter;
import com.example.hendrixassassins.R;
import com.example.hendrixassassins.agent.Agent;
import com.example.hendrixassassins.agent.AgentFileHelper;
import com.example.hendrixassassins.agent.AgentList;
import com.example.hendrixassassins.agent.AgentStatus;
import com.example.hendrixassassins.game.Game;

import java.util.ArrayList;

import static android.R.layout.simple_spinner_item;
import static com.example.hendrixassassins.agent.AgentStatus.*;

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

    public static final int sortDescending = 2, sortAscending = 1,
            sortByArray = R.array.sortBy_array, filterStatusArray = R.array.filterStatus_array;

    private View fragView;
    private ListView listView;
    private Spinner statusFilter, killsFilter, pointsFilter, alphabeticalFilter;

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

    private void setupSpinners() {
        findSpinners();
        setSpinnerDropdowns();
        setSpinnerListeners();
    }

    private void findSpinners(){
        statusFilter = fragView.findViewById(R.id.sortbyStatus);
        killsFilter = fragView.findViewById(R.id.sortByKills);
        pointsFilter = fragView.findViewById(R.id.sortByPoints);
        alphabeticalFilter = fragView.findViewById(R.id.sortByAlphabet);
    }

    private void setSpinnerDropdowns() {
        setAdapter("Kills", killsFilter, sortByArray);
        setAdapter("Points", pointsFilter, sortByArray);
        setAdapter("Alphabetical", alphabeticalFilter, sortByArray);
        setAdapter("Status", statusFilter, filterStatusArray);
    }

    private void setAdapter(String title, Spinner spinner, int optionArray){
        String[] options = getResources().getStringArray(optionArray);
        options[0] = title;
        spinner.setAdapter(new ArrayAdapter<CharSequence>(this.getContext(),
                simple_spinner_item, options));
    }

    private void setSpinnerListeners() {
        setStatusFilterListener();
        setKillsFilterListener();
        setPointsFilterListener();
        setAlphabeticalFilterListener();
    }

    private void setAlphabeticalFilterListener(){
        alphabeticalFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case sortAscending:
                        agentList.sortNamesAlphabetically();
                        break;
                    case sortDescending:
                        agentList.sortNamesAlphabetically();
                        agentList.reverse();
                        break;
                    default:
                }
                createListViewAdapter(agentList);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setStatusFilterListener(){
        statusFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    AgentStatus status = AgentStatus.valueOf(parent.getSelectedItem().toString());
                    AgentList filtered = agentList.filterAgentsByStatus(status);
                    createListViewAdapter(filtered);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setKillsFilterListener(){
        killsFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case sortAscending:
                        agentList.sortByPersonalKills();
                        break;
                    case sortDescending:
                        agentList.sortByPersonalKills();
                        agentList.reverse();
                        break;
                     default:
                }
                createListViewAdapter(agentList);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setPointsFilterListener(){
        pointsFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case sortAscending:
                        agentList.sortByPointsTotal();
                        break;
                    case sortDescending:
                        agentList.sortByPointsTotal();
                        agentList.reverse();
                        break;
                    default:
                }
                createListViewAdapter(agentList);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_home, container, false);
        createListViewAdapter(agentList);
        //setupButtons();

//        AutoCompleteTextView autoCompleteTextView = fragView.findViewById(R.id.autoCompleteTest);
//        AutoCompleteAgentAdapter<Agent> adapter = new AutoCompleteAgentAdapter<>(this.getContext(),
//                R.layout.auto_complete_list_test, agentList.getAllAgents());
//        // This was causing a bug
//        // autoCompleteTextView.setAdapter(adapter);

        setupSpinners();

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


    private void createListViewAdapter(final AgentList displayAgents){
        ListView listView = fragView.findViewById(R.id.agentList);
        Log.e("all agents", String.valueOf(displayAgents.getAllAgents().size()));
        CustomListViewAdapter adapter = new CustomListViewAdapter<>(this.getContext(),
                R.layout.test_list_view, displayAgents.getAllAgents());
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoAgentProfile(displayAgents.getAllAgents().get(position));
            }


        });
    }

    private void gotoAgentProfile(Agent agent) {
        Intent userListView = new Intent(getActivity(), AgentProfileActivity.class);
        userListView.putExtra("handlerEmail", game.getEmail());
        userListView.putExtra("agentEmail", agent.getEmail());
        startActivity(userListView);
        startActivity(userListView);
    }
}
