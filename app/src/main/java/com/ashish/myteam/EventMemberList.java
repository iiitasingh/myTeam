package com.ashish.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EventMemberList extends AppCompatActivity {

    String[] mems;
    ListView memList;
    String[] memsArray;
    String[] memsArray1;
    Contri_Status_adapter listAdapter;
    ArrayList<Contri_Status_details> membersList;
    ArrayList<String> memsArray2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_member_list);
        mems = getIntent().getStringArrayExtra("members");


        memsArray = mems[0].split(",", 0);
        memsArray1 = mems[1].split(",", 0);
        memsArray2 = new ArrayList<String>(Arrays.asList(memsArray1));
        for(int k=0;k<memsArray2.size() ;k++ )
        {
            if(memsArray2.get(k).equals(""))
            {
                memsArray2.remove(k);
            }
        }


        if (memsArray.length > 0) {
            memList = (ListView) findViewById(R.id.memberListOfEvent);
            membersList = new ArrayList<>();
            String sql = "SELECT ID, name FROM table_user WHERE ID IN (" + DatabaseHelper.makePlaceholders(memsArray.length) + ")";
            Cursor eventMemName = MainActivity.db.getEvents(sql, memsArray);
            membersList.clear();
            if (eventMemName.getCount() == 0) {
                listAdapter = new Contri_Status_adapter(EventMemberList.this, R.layout.contri_status_template, membersList);
                Toast.makeText(EventMemberList.this, "No member for this event!", Toast.LENGTH_LONG).show();
            } else {
                while (eventMemName.moveToNext()) {
                    Long id = eventMemName.getLong(0);
                    String name = eventMemName.getString(1);
                    membersList.add(new Contri_Status_details(id, name, false));
                }

                for (int i = 0; i < memsArray2.size(); i++) {
                    for (int j = 0; j < memsArray.length; j++) {
                        if (membersList.get(j).getID() == Long.parseLong(memsArray2.get(i))){
                            membersList.get(j).setStatus(true);
                        }
                    }
                }

                listAdapter = new Contri_Status_adapter(this, R.layout.contri_status_template, membersList);
            }

            memList.setAdapter(listAdapter);

        }
    }
}
