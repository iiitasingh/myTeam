package com.ashish.myteam;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousEvents extends Fragment {


    public PreviousEvents() {
        // Required empty public constructor
    }

    ArrayList<Events_Card> prvCardList;
    ListView previouseventlist;
    Event_List_Adapter adapter;
    String prvTeamName;
    User owner_P;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View previousView = inflater.inflate(R.layout.fragment_previous_events, container, false);
        previouseventlist = (ListView) previousView.findViewById(R.id.previousEventCardList);
        prvCardList = new ArrayList<>();

        owner_P = (User) getArguments().getSerializable("USER_B");
        prvTeamName = owner_P.getUteam();
        String events = owner_P.getUevents();
        String[] names = events.split(",",0);
        String sql = "SELECT * FROM table_event WHERE event_date < date('now') AND event_ID IN (" + DatabaseHelper.makePlaceholders(names.length) + ")  ORDER BY event_date DESC";

        Cursor eventDetails = MainActivity.db.getEvents(sql,names);
        prvCardList.clear();
        if (eventDetails.getCount() == 0) {
            adapter = new Event_List_Adapter(getActivity(), R.layout.event_list_template, prvCardList);
            Toast.makeText(getActivity(), "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (eventDetails.moveToNext()) {
                String evtname = eventDetails.getString(1);
                String evtDesc = eventDetails.getString(3);
                String evtDate = eventDetails.getString(4);
                //byte[] img = eventDetails.getBlob(1);
                prvCardList.add(new Events_Card(evtname, prvTeamName, evtDesc, evtDate));
            }
            adapter = new Event_List_Adapter(getActivity(), R.layout.event_list_template, prvCardList);
            previouseventlist.setAdapter(adapter);
        }

        return previousView;
    }

}
