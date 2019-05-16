package com.ashish.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventMemberList extends AppCompatActivity {

    String mems;
    ListView memList;
    String[] memsArray;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> membersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_member_list);
        mems = getIntent().getStringExtra("members");

        memsArray = mems.split(",",0);
        if(memsArray.length > 0) {
            memList = (ListView) findViewById(R.id.memberListOfEvent);
            membersList = new ArrayList<>();

            String sql = "SELECT name FROM table_user WHERE ID IN (" + DatabaseHelper.makePlaceholders(memsArray.length) + ")";
            Cursor eventMemName = MainActivity.db.getEvents(sql, memsArray);
            membersList.clear();
            if (eventMemName.getCount() == 0) {
                listAdapter = new ArrayAdapter<String>(EventMemberList.this, android.R.layout.simple_list_item_1, membersList);
                Toast.makeText(EventMemberList.this, "No member for this event!", Toast.LENGTH_LONG).show();
            } else {
                while (eventMemName.moveToNext()) {
                    membersList.add(eventMemName.getString(0));
                }
                listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, membersList);
            }
            memList.setAdapter(listAdapter);
        }
    }
}
