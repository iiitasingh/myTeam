package com.ashish.myteam;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class teamSelection extends AppCompatActivity {

    FloatingActionButton add_team;
    ListView teamslist;
    String[] teamstest;
    ArrayList<String> teams;
    String clickedValue = "Select Team First";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        add_team = (FloatingActionButton)findViewById(R.id.addTeamfab);
        teamslist = (ListView)findViewById(R.id.teamlist);

        add_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerTeam = new Intent(teamSelection.this,RegisterTeam.class);
                startActivity(registerTeam);
            }
        });


        teams = new ArrayList<>();
        Cursor cursor = MainActivity.db.getdata("SELECT team_name FROM table_team");
        teams.clear();
        if(cursor.getCount() == 0) {
            teamslist.setAdapter(new team_list_adapter(this, teams));
            Toast.makeText(teamSelection.this, "Register your team first",Toast.LENGTH_LONG).show();
        }
        else {
            while(cursor.moveToNext()){
                teams.add(cursor.getString(0));
                teamslist.setAdapter(new team_list_adapter(this, teams));
            }
        }

            teamslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    clickedValue = teams.get(position).toString();

                    Intent intent = new Intent(teamSelection.this, Signup.class);

                    intent.putExtra("ListViewClickedValue", clickedValue);
                    startActivity(intent);

                }
            });

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(teamSelection.this, Signup.class);
        startActivity(setIntent);
        finish();
    }


}
