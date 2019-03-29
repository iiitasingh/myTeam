package com.ashish.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddEvent extends AppCompatActivity {

    EditText eventName, eventDesc, eventDate;
    CheckBox selectallFrnd;
    ListView eventMembers;
    ImageButton eventAddImgBtn;
    ArrayList<String> team, eventMemb;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ArrayList<Integer> mUserItems;
    String eventMembersString = "";
    String eventOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        eventName = (EditText)findViewById(R.id.eventName);
        eventDesc = (EditText)findViewById(R.id.eventDesc);
        eventDate = (EditText)findViewById(R.id.eventDate);
        selectallFrnd = (CheckBox) findViewById(R.id.selectFrnd);
        eventMembers = (ListView) findViewById(R.id.eventMembers);
        eventAddImgBtn = (ImageButton) findViewById(R.id.eventAddImgBtn);
        eventMemb = new ArrayList<String>();
        mUserItems = new ArrayList<>();

        eventMembers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        eventOwner = getIntent().getStringExtra("Email");

        team = new ArrayList<String>();
        Cursor frnds_list = MainActivity.db.getdata("SELECT name FROM table_user");
        team.clear();
        if (frnds_list.getCount() == 0) {
            Toast.makeText(AddEvent.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (frnds_list.moveToNext()) {
                team.add(frnds_list.getString(0));
            }
        }
        eventMembers.setAdapter(new choose_member_adapter(AddEvent.this,team));

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dobPicker = new DatePickerDialog(
                        AddEvent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dobPicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dobPicker.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date;
                if(month <10) {
                    date = year + "-0" + month + "-" + day;
                }
                else {
                    date = year + "-" + month + "-" + day;
                }
                eventDate.setText(date);
            }
        };

        eventMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectallFrnd.isChecked())
                {
                    selectallFrnd.setChecked(false);
                }
                if(!mUserItems.contains(position)){
                    mUserItems.add(position);
                }else{
                    mUserItems.remove((Integer.valueOf(position)));
                }
            }
        });

        eventAddImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventMembersString ="";
                for (int i = 0; i < mUserItems.size(); i++) {
                    eventMembersString = eventMembersString + team.get(mUserItems.get(i));
                    if (i != mUserItems.size() - 1) {
                        eventMembersString = eventMembersString + ",";
                    }
                }

                String name = eventName.getText().toString().trim();
                String desc = eventDesc.getText().toString().trim();
                String date = eventDate.getText().toString().trim();

                if(name.length() >= 3 && desc.length() >=5 && date.length() >4)
                {
                    long val = MainActivity.db.addEvent(name,eventOwner,desc,date,eventMembersString);
                    if(val >0)
                    {
                        Toast.makeText(AddEvent.this, "Event Created", Toast.LENGTH_LONG).show();
                        Intent events = new Intent(AddEvent.this,list_test.class);
                        events.putExtra("UserEmail",eventOwner);
                        startActivity(events);
                        finish();
                    }
                }
                else if (name.length() < 3)
                {
                    Toast.makeText(AddEvent.this, "Name length is less than 3", Toast.LENGTH_LONG).show();
                }
                else if (desc.length() <5)
                {
                    Toast.makeText(AddEvent.this, "Description Venue atleast", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AddEvent.this, "Date should not be null", Toast.LENGTH_LONG).show();
                }
            }
        });

        selectallFrnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectallFrnd.isChecked())
                {
                    for ( int i=0; i < eventMembers.getChildCount(); i++) {
                        eventMembers.setItemChecked(i, true);
                    }
                    mUserItems.clear();
                    for (int i = 0; i < team.size(); i++) {
                        mUserItems.add(i);
                    }
                    //Toast.makeText(AddEvent.this, eventMembersString, Toast.LENGTH_LONG).show();
                }
                else {
                    for ( int i=0; i < eventMembers.getChildCount(); i++) {
                        eventMembers.setItemChecked(i, false);
                    }
                    mUserItems.clear();
                    //Toast.makeText(AddEvent.this, eventMembersString, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
