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
    String mail, prvTeamName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View previousView = inflater.inflate(R.layout.fragment_previous_events, container, false);

        mail = getArguments().getString("edttext");
        previouseventlist = (ListView) previousView.findViewById(R.id.previousEventCardList);

        Cursor teamCursor = MainActivity.db.getdata("SELECT team FROM table_user WHERE email = '" + mail + "'");
        prvTeamName = "";
        if (teamCursor.getCount() == 0) {
            Toast.makeText(getActivity(), "There are no team for this mail!", Toast.LENGTH_LONG).show();
        } else {
            teamCursor.moveToFirst();
            prvTeamName = teamCursor.getString(teamCursor.getColumnIndex("team"));

        }
        prvCardList = new ArrayList<>();
        Cursor eventDetails = MainActivity.db.getdata("SELECT * FROM table_event WHERE event_date < date('now') ORDER BY event_date DESC");
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
