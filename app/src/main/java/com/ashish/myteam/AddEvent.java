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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddEvent extends AppCompatActivity {

    EditText eventName, eventDesc, eventDate, approxContri;
    CheckBox selectallFrnd, contriButton;
    ListView eventMembers;
    ImageButton eventAddImgBtn;
    ArrayList<String> eventMemb;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ArrayList<Integer> mUserItems;
    ArrayList<teamMemberObject> team;
    String eventMembersString;
    String eventOwner;
    User owner;
    ArrayList<String> userIds;
    String[] UserIdsStrArr;
    int ownerPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        eventName = (EditText)findViewById(R.id.eventName);
        eventDesc = (EditText)findViewById(R.id.eventDesc);
        eventDate = (EditText)findViewById(R.id.eventDate);
        selectallFrnd = (CheckBox) findViewById(R.id.selectFrnd);
        contriButton = (CheckBox) findViewById(R.id.collection_Box);
        eventMembers = (ListView) findViewById(R.id.eventMembers);
        eventAddImgBtn = (ImageButton) findViewById(R.id.eventAddImgBtn);
        approxContri = (EditText)findViewById(R.id.approxContri);
        eventMemb = new ArrayList<String>();
        mUserItems = new ArrayList<>();
        userIds = new ArrayList<>();

        eventMembers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        owner = (User) getIntent().getSerializableExtra("USER");
        eventOwner = owner.getUemail();


        team = new ArrayList<>();
        Cursor frnds_list = MainActivity.db.getdata("SELECT ID,name FROM table_user WHERE team = '"+owner.getUteam()+"'");
        team.clear();
        if (frnds_list.getCount() == 0) {
            Toast.makeText(AddEvent.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (frnds_list.moveToNext()) {
                Long id = frnds_list.getLong(0);
                String name = frnds_list.getString(1);
                team.add(new teamMemberObject(id,name));
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

        contriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contriButton.isChecked())
                {
                    approxContri.setFocusableInTouchMode(true);
                    approxContri.requestFocus();
                    approxContri.setFocusable(true);
                    approxContri.setClickable(true);
                }else{
                    approxContri.setFocusableInTouchMode(false);
                    approxContri.requestFocus();
                    approxContri.setText("");
                    approxContri.setFocusable(false);
                    approxContri.setClickable(false);
                }
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date;
                String dayS = String.valueOf(day);
                String monthS = String.valueOf(month);
                if(month < 10 )
                {
                    monthS = "0" + monthS;
                }
                if(day < 10 )
                {
                    dayS = "0" + dayS;
                }

                date = year + "-" + monthS + "-" + dayS;
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

        ownerPosition = getOwnerPosition(team);
        eventAddImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventMembersString ="";
                if(!mUserItems.contains(ownerPosition))
                {
                    mUserItems.add(ownerPosition);
                }

                for (int i = 0; i < mUserItems.size(); i++) {
                    eventMembersString = eventMembersString + String.valueOf(team.get(mUserItems.get(i)).getId());
                    userIds.add(String.valueOf(team.get(mUserItems.get(i)).getId()));
                    if (i != mUserItems.size() - 1) {
                        eventMembersString = eventMembersString + ",";
                    }
                }

                UserIdsStrArr = new String[userIds.size()];
                Object[] objArr = userIds.toArray();
                int j = 0;
                for (Object obj : objArr) {
                    UserIdsStrArr[j++] = (String)obj;
                }

                String name = eventName.getText().toString().trim();
                String desc = eventDesc.getText().toString().trim();
                String date = eventDate.getText().toString().trim();
                String contri = Boolean.toString(contriButton.isChecked());
                String contriAmount = approxContri.getText().toString().trim();
                Long eventID;

                if(name.length() >= 3 && desc.length() >=5 && date.length() >4)
                {
                    if(contriButton.isChecked()) {
                        if(contriAmount.length() > 0) {
                            Long amount = Long.parseLong(contriAmount);
                            eventID = MainActivity.db.newEvent(name, eventOwner, desc, date, eventMembersString, contri, amount);

                            if (eventID > 0) {
                                Long Val = MainActivity.db.addEvent(UserIdsStrArr, String.valueOf(eventID));
                                if (Val > 0) {
                                    Toast.makeText(AddEvent.this, "Event Created", Toast.LENGTH_LONG).show();
                                    Intent events = new Intent(AddEvent.this, list_test.class);
                                    events.putExtra("UserEmail", eventOwner);
                                    startActivity(events);
                                    finish();
                                } else {
                                    Toast.makeText(AddEvent.this, "Event Creation failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(AddEvent.this, "Put Some approximate Contri Amount", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        contriAmount = "0";
                        Long amount = Long.parseLong(contriAmount);
                        eventID = MainActivity.db.newEvent(name, eventOwner, desc, date, eventMembersString, contri,amount);
                        if (eventID > 0) {
                            Long Val = MainActivity.db.addEvent(UserIdsStrArr, String.valueOf(eventID));
                            //Toast.makeText(AddEvent.this, "Event Created"+ Arrays.toString(UserIdsStrArr) , Toast.LENGTH_LONG).show();
                            if (Val > 0) {
                                Toast.makeText(AddEvent.this, "Event Created", Toast.LENGTH_LONG).show();
                                Intent events = new Intent(AddEvent.this, list_test.class);
                                events.putExtra("UserEmail", eventOwner);
                                startActivity(events);
                                finish();
                            } else {
                                Toast.makeText(AddEvent.this, "Event Creation failed", Toast.LENGTH_LONG).show();
                            }
                        }
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

    public int getOwnerPosition(ArrayList<teamMemberObject> teamlist){
        for (int i = 0; i < teamlist.size();i++)
        {
            if(teamlist != null && teamlist.get(i).getId().equals(owner.getId()))
            {
                return i;
            }
        }
        return -1;
    }
}
