package com.ashish.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class wallet extends AppCompatActivity {

    TextView remainingAmount;
    ListView pendingContri;
    ArrayList<Events_Card> cardList, pendingCardList;
    User owner;
    String teamName;
    EventWalletPend_Adapter adapter;
    long remSum = 0;
    String[] creditMems;
    String ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        remainingAmount = (TextView)findViewById(R.id.remainingAmount);
        pendingContri = (ListView)findViewById(R.id.pendingContri);
        cardList = new ArrayList<>();
        pendingCardList = new ArrayList<>();

        owner = (User) getIntent().getSerializableExtra("USER");
        teamName = owner.getUteam();
        ownerId = String.valueOf(owner.getId());
        String events = owner.getUevents();
        String[] names = events.split(",",0);

        String sql = "SELECT * FROM table_event WHERE contribution = 'true' AND event_ID IN (" + DatabaseHelper.makePlaceholders(names.length) + ")  ORDER BY event_date";
        Cursor remaining = MainActivity.db.getEvents(sql,names);
        if(remaining.getCount() == 0) {
            remSum = 0;
            Toast.makeText(wallet.this, "There is no contribution event!",Toast.LENGTH_LONG).show();
        }
        else {
            String sql1 = "SELECT sum(remaining_contri) FROM table_event WHERE contribution = 'true' AND event_ID IN (" + DatabaseHelper.makePlaceholders(names.length) + ")  ORDER BY event_date";
            Cursor remain = MainActivity.db.getEvents(sql1,names);
            if(remain.getCount() == 0) {
                remSum = 0;
                Toast.makeText(wallet.this, "There is no contribution event!",Toast.LENGTH_LONG).show();
            }
            while(remain.moveToNext()) {
                remSum = remain.getLong(0);
            }
        }

        remainingAmount.setText(""+String.valueOf(remSum)+".00");
        remainingAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contri = new Intent(wallet.this,RemainingContri.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", owner);
                contri.putExtras(args);
                startActivity(contri);
            }
        });


        String sql2 = "SELECT * FROM table_event WHERE contribution = 'true' AND event_ID IN (" + DatabaseHelper.makePlaceholders(names.length) + ")  ORDER BY event_date";
        Cursor eventDetails = MainActivity.db.getEvents(sql2,names);
        cardList.clear();
        pendingCardList.clear();
        if(eventDetails.getCount() == 0) {
            adapter = new EventWalletPend_Adapter(wallet.this, R.layout.pending_contri_template, pendingCardList);
            Toast.makeText(wallet.this, "There is no contribution event!",Toast.LENGTH_LONG).show();
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
                Long approx = eventDetails.getLong(7);
                Long rem = eventDetails.getLong(10);
                String CrMems = eventDetails.getString(13);
                cardList.add(new Events_Card(evtname,teamName,evtDesc,evtDate,evtContri,evtId,evtMems,contri,spent,rem,approx,CrMems));
            }
            for(int i=0; i < cardList.size() ; i++){
                creditMems = cardList.get(i).getCreditMemb().split(",",0);
                if(Arrays.asList(creditMems).contains(ownerId)){
                    //pendingCardList.add(cardList.get(i));
                }else {
                    pendingCardList.add(cardList.get(i));
                }
            }
            adapter = new EventWalletPend_Adapter(wallet.this, R.layout.pending_contri_template, pendingCardList);
        }
        pendingContri.setAdapter(adapter);

    }
}
