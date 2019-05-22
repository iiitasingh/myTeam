package com.ashish.myteam;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;


public class Home extends AppCompatActivity {

    FloatingActionButton usericon;
    TextView tmname;
    ArrayList<teams> TeamList;
    ListView your_teams;
    Dialog popupDial;
    User homeUser;
    //Popup Attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        popupDial = new Dialog(this);
        tmname = (TextView)findViewById(R.id.team_name);
        TeamList = new ArrayList<>();
        your_teams = (ListView)findViewById(R.id.yourteamlist);
        usericon = (FloatingActionButton)findViewById(R.id.homefab);

        homeUser = (User) getIntent().getSerializableExtra("USER");

        usericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup1(Home.this,homeUser);
            }
        });


        tmname.setText("Team "+homeUser.getUteam());

        Cursor cursor = MainActivity.db.getteamMembers(homeUser.getUteam());
        TeamList.clear();
        if(cursor.getCount() == 0) {
            your_teams.setAdapter(new your_team_list_adapter(this, R.layout.your_team_list_view, TeamList));
            Toast.makeText(Home.this, "No Member in this Team!",Toast.LENGTH_LONG).show();
        }
        else {
            while(cursor.moveToNext()){
                String mail = cursor.getString(0);
                String name = cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                String desig = cursor.getString(3);
                TeamList.add(new teams(img,name,mail,desig));
            }
            your_teams.setAdapter(new your_team_list_adapter(this, R.layout.your_team_list_view, TeamList));
        }

        your_teams.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Membmail = TeamList.get(position).getEmail();
                if(homeUser.getUemail().equals(Membmail)) {
                    Intent memberIntent = new Intent(Home.this, resume_Page.class);
                    Bundle args = new Bundle();
                    args.putSerializable("USER", homeUser);
                    memberIntent.putExtras(args);
                    startActivity(memberIntent);
                } else {
                    Intent memberIntent = new Intent(Home.this, teammate_resume.class);
                    memberIntent.putExtra("USER_MAIL", Membmail);
                    startActivity(memberIntent);
                }
            }
        });

    }
    //#####################  PopUp1 Attributes  ##################

    ImageView Usimg;
    TextView MemName, MemEmail, MemTeam, MemDob, MemFood, MemMob, MemBgp, MemNknam, MemDesig1;
    Button edit;
    private void showPopup1(Activity popActivity, final User Usrmail){
        popupDial.setContentView(R.layout.activity_bottomappbar);

        int width1 = (int) (popActivity.getResources().getDisplayMetrics().widthPixels * .82);
        int height1 = (int) (popActivity.getResources().getDisplayMetrics().heightPixels * 0.8);
        popupDial.getWindow().setLayout(width1, height1);
        popupDial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupDial.show();

        Usimg = (ImageView) popupDial.findViewById(R.id.PopUpUserImg);
        MemNknam = (TextView) popupDial.findViewById(R.id.MemNknam1);
        MemName = (TextView) popupDial.findViewById(R.id.MemName1);
        MemEmail = (TextView) popupDial.findViewById(R.id.MemEmail1);
        MemTeam = (TextView) popupDial.findViewById(R.id.MemTeam1);
        MemDob = (TextView) popupDial.findViewById(R.id.MemDob1);
        MemFood = (TextView) popupDial.findViewById(R.id.MemFood1);
        MemMob = (TextView) popupDial.findViewById(R.id.MemMob1);
        MemBgp = (TextView) popupDial.findViewById(R.id.MemBgp1);
        MemDesig1 = (TextView) popupDial.findViewById(R.id.MemDesig1);
        edit = (Button) popupDial.findViewById(R.id.Edit);

        MemEmail.setText("Email: " + Usrmail.getUemail());
        MemName.setText(Usrmail.getUname());
        MemNknam.setText(Usrmail.getUnick_name());
        MemDesig1.setText(Usrmail.getUdesignation());
        MemTeam.setText("Team: " + Usrmail.getUteam());
        MemDob.setText("DOB: " + Usrmail.getUdob());
        MemFood.setText("Food: " + Usrmail.getUfood());
        MemBgp.setText("BGrp: "+ Usrmail.getUbgrp());
        MemMob.setText("Mob: "+ Usrmail.getUmobile());

        Bitmap bitmap = BitmapFactory.decodeByteArray(Usrmail.getUimage(), 0, Usrmail.getUimage().length);
        Usimg.setImageBitmap(bitmap);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent memberIntent = new Intent(Home.this, resume_Page.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", homeUser);
                memberIntent.putExtras(args);
                startActivity(memberIntent);
                popupDial.dismiss();
            }
        });
    }

}
