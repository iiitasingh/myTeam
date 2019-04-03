package com.ashish.myteam;


import android.app.usage.UsageEvents;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import androidx.appcompat.app.AppCompatActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BirthdayCalender extends AppCompatActivity {

    private static final String TAG = "BirthdayCalender";

    CompactCalendarView compactCalendar;
    TextView current;
    Calendar cal;
    ListView birthdays;
    ArrayList<BirthdayList> birthdayList,evenCalenderList;
    ArrayList<String> names;
    private String[] dob_arr = {};
    User calenderOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday_calender);

        calenderOwner = (User)getIntent().getSerializableExtra("USER");

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        current = (TextView) findViewById(R.id.currentMonthYr);
        birthdays = (ListView) findViewById(R.id.birtdayList);

        cal = Calendar.getInstance();
        current.setText(DateFormat.format("MMMM yyyy", cal));
        compactCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        ///////  Getting Birthdays from db  //////////////
        birthdayList = new ArrayList<BirthdayList>();
        Cursor dob_list = MainActivity.db.getdata("SELECT name,dob FROM table_user WHERE team = '"+calenderOwner.getUteam()+"'");
        birthdayList.clear();
        if (dob_list.getCount() == 0) {
            Toast.makeText(BirthdayCalender.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (dob_list.moveToNext()) {
                String name = dob_list.getString(0);
                String dob = dob_list.getString(1);
                birthdayList.add(new BirthdayList("Happy Birthday "+name, dob));
            }
        }

        ///////  Getting Events from db  //////////////
        evenCalenderList = new ArrayList<BirthdayList>();
        String eventString = calenderOwner.getUevents();
        String[] User_events = eventString.split(",",0);
        String sql = "SELECT event_name,event_date FROM table_event WHERE event_ID IN (" + DatabaseHelper.makePlaceholders(User_events.length) + ")";
        Cursor event_list = MainActivity.db.getEvents(sql,User_events);
        evenCalenderList.clear();
        if (event_list.getCount() == 0) {
            Toast.makeText(BirthdayCalender.this, "You have no Event.", Toast.LENGTH_SHORT).show();
        } else {
            while (event_list.moveToNext()) {
                String name = event_list.getString(0);
                String dob = event_list.getString(1);
                evenCalenderList.add(new BirthdayList(name, dob));
            }
        }

        setEvent(birthdayList);
        setEvent(evenCalenderList);

        //////////////////   Checking Events for today's Date   //////////////////
        Date today = Calendar.getInstance().getTime();
        List<Event> events = compactCalendar.getEvents(today);
        names = new ArrayList<String>();
        if (events.size() > 0) {

            names.clear();
            //Toast.makeText(BirthdayCalender.this, "Happy Birthday " + events.get(0).getData(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i<events.size();i++)
            {
                Event ash = events.get(i);
                names.add(ash.getData().toString());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    BirthdayCalender.this,
                    android.R.layout.simple_list_item_1,
                    names );

            birthdays.setAdapter(arrayAdapter);

        }
        else {
            birthdays.setAdapter(null);
        }

        /////////   define a listener to receive callbacks when certain events happen.   ////////////////

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                names = new ArrayList<String>();
                if (events.size() > 0) {

                    names.clear();
                    //Toast.makeText(BirthdayCalender.this, "Happy Birthday " + events.get(0).getData(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i<events.size();i++)
                    {
                        Event ash = events.get(i);
                        names.add(ash.getData().toString());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            BirthdayCalender.this,
                            android.R.layout.simple_list_item_1,
                            names );

                    birthdays.setAdapter(arrayAdapter);

                }
                else {
                    birthdays.setAdapter(null);
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                current.setText(DateFormat.format("MMMM yyyy", firstDayOfNewMonth));
            }
        });
    }

    public void setEvent(ArrayList<BirthdayList> dobdate) {

        int i = dobdate.size();
        int today = Calendar.getInstance().get(Calendar.YEAR);
        String currDate;

        for (int j = 0; j < i; j++) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = null;
            try {
                data = sdf.parse(dobdate.get(j).getDOB());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            currDate = DateFormat.format("dd/MM", data) + "/" + today + " 11:00:00";

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date data1 = null;
            try {
                data1 = sdf1.parse(currDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long millis = data1.getTime();
            Event ev1 = new Event(Color.GREEN, millis, ""+dobdate.get(j).getName());
            compactCalendar.addEvent(ev1);
        }

    }
}
