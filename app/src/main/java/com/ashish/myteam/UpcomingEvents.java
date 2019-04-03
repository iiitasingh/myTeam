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


public class UpcomingEvents extends Fragment {



    public UpcomingEvents() {
        // Required empty public constructor
    }

    ArrayList<Events_Card> cardList;
    ListView eventlist;
    Event_List_Adapter adapter;
    String teamName;
    User owner_Up;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upcoming_events, container, false);
        eventlist = (ListView) v.findViewById(R.id.eventCardList);
        cardList = new ArrayList<>();

        owner_Up = (User) getArguments().getSerializable("USER_B");
        teamName = owner_Up.getUteam();

        String events = owner_Up.getUevents();
        String[] names = events.split(",",0);
        String sql = "SELECT * FROM table_event WHERE event_date >= date('now') AND event_ID IN (" + DatabaseHelper.makePlaceholders(names.length) + ")  ORDER BY event_date";
        Cursor eventDetails = MainActivity.db.getEvents(sql,names);
        cardList.clear();
        if(eventDetails.getCount() == 0) {
            adapter = new Event_List_Adapter(getActivity(), R.layout.event_list_template, cardList);
            Toast.makeText(getActivity(), "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }
        else {
            while(eventDetails.moveToNext()){
                String evtname = eventDetails.getString(1);
                String evtDesc = eventDetails.getString(3);
                String evtDate = eventDetails.getString(4);
                //byte[] img = eventDetails.getBlob(1);
                cardList.add(new Events_Card(evtname,teamName,evtDesc,evtDate));
            }
            adapter = new Event_List_Adapter(getActivity(), R.layout.event_list_template, cardList);
        }
        eventlist.setAdapter(adapter);

        return v;
    }

}
