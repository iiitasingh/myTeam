package com.ashish.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RemainingContri extends AppCompatActivity {

    ListView contriEvents;
    ArrayList<Events_Card> cardList;
    User owner;
    String teamName;
    EventListWallet_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining_contri);

        contriEvents = (ListView)findViewById(R.id.contriEvents);
        cardList = new ArrayList<>();

        owner = (User) getIntent().getSerializableExtra("USER");
        teamName = owner.getUteam();
        String events = owner.getUevents();
        String[] names = events.split(",",0);
        String sql = "SELECT * FROM table_event WHERE contribution == 'true' AND event_ID IN (" + DatabaseHelper.makePlaceholders(names.length) + ")  ORDER BY event_date DESC";
        Cursor eventDetails = MainActivity.db.getEvents(sql,names);
        cardList.clear();
        if(eventDetails.getCount() == 0) {
            adapter = new EventListWallet_Adapter(RemainingContri.this, R.layout.event_list_template, cardList);
            Toast.makeText(RemainingContri.this, "There is no upcoming event!",Toast.LENGTH_LONG).show();
        }
        else {
            while(eventDetails.moveToNext()){
                String evtname = eventDetails.getString(1);
                String evtDesc = eventDetails.getString(3);
                String evtDate = eventDetails.getString(4);
                String evtContri = eventDetails.getString(6);
                Long evtId = eventDetails.getLong(0);
                String evtMems = eventDetails.getString(5);
                Long contri = eventDetails.getLong(8);
                Long spent = eventDetails.getLong(9);
                Long rem = eventDetails.getLong(10);
                Long approx = eventDetails.getLong(7);
                String CrMems = eventDetails.getString(13);
                cardList.add(new Events_Card(evtname,teamName,evtDesc,evtDate,evtContri,evtId,evtMems,contri,spent,rem,approx,CrMems));
            }
            adapter = new EventListWallet_Adapter(RemainingContri.this, R.layout.event_list_template, cardList);
        }
        contriEvents.setAdapter(adapter);

    }
}
