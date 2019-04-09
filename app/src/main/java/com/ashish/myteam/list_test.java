package com.ashish.myteam;

import android.app.Activity;
import android.app.Dialog;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class list_test extends Activity {

    ImageView yourImage, teamIcon, addEvent, birthdayImg, EventsImg, transactionImg, addTransacImg;
    TextView yourName, teamName, teamName1, birthdayTxt, addEventTxt, EventsTxt, transactionTxt, addTransacTxt;
    SwipeRefreshLayout swipeRefresh;
    String TempHolder;
    byte[] usrImg;
    Dialog logoutProfile;
    Animation atg;
    public static User profileUser;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtest);

        session = new Session(this);
        if (!session.loggedin()) {
            Logout();
        }
        TempHolder = getIntent().getStringExtra("UserEmail");
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);

        logoutProfile = new Dialog(this);
        addEvent = (ImageView) findViewById(R.id.addEvent);
        yourImage = (ImageView) findViewById(R.id.userImg);
        teamIcon = (ImageView) findViewById(R.id.teamIcon);
        yourName = (TextView) findViewById(R.id.nickName);
        teamName = (TextView) findViewById(R.id.teamNm);
        teamName1 = (TextView) findViewById(R.id.teamNm1);
        birthdayImg = (ImageView) findViewById(R.id.birthdayImg);
        birthdayTxt = (TextView) findViewById(R.id.birthdayTxt);
        EventsImg = (ImageView) findViewById(R.id.EventsImg);
        EventsTxt = (TextView) findViewById(R.id.EventsTxt);
        addEventTxt = (TextView) findViewById(R.id.addEventTxt);
        transactionImg = (ImageView) findViewById(R.id.transactionImg);
        transactionTxt = (TextView) findViewById(R.id.transactionTxt);
        addTransacImg = (ImageView) findViewById(R.id.addTransacImg);
        addTransacTxt = (TextView) findViewById(R.id.addTransacTxt);



        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        Cursor Ucursor = MainActivity.db.getdata("SELECT * FROM table_user WHERE email = '" + TempHolder + "'");
        if (Ucursor.getCount() == 0) {
            Toast.makeText(list_test.this, "No data with this mail", Toast.LENGTH_LONG).show();
        } else {
            Ucursor.moveToFirst();
            Long id = Ucursor.getLong(0);
            String uemail = Ucursor.getString(1);
            String uaadhaar = Ucursor.getString(2);
            String uname = Ucursor.getString(3);
            String unick_name = Ucursor.getString(4);
            String uteam = Ucursor.getString(5);
            String udob = Ucursor.getString(7);
            String ufood = Ucursor.getString(8);
            String umobile = Ucursor.getString(9);
            String ubgrp = Ucursor.getString(10);
            String upan = Ucursor.getString(11);
            String uevents = Ucursor.getString(12);
            byte[] uimage = Ucursor.getBlob(6);
            String utrans = Ucursor.getString(13);
            String uabout = Ucursor.getString(14);
            String udesig = Ucursor.getString(15);
            String uhobby = Ucursor.getString(16);

            profileUser = new User(id, uemail, uaadhaar, uname, unick_name, uteam, udob, ufood, umobile, ubgrp, upan, uevents, uimage, utrans, uabout,udesig,uhobby);
        }

        yourName.setText("Welcome " + profileUser.getUnick_name());
        teamName.setText(profileUser.getUteam());
        teamName1.setText(profileUser.getUteam());
        usrImg = profileUser.getUimage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(usrImg, 0, usrImg.length);
        yourImage.setImageBitmap(bitmap);

        yourImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutPopup(list_test.this, profileUser);
            }
        });
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, AddEvent.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                MemIntent.putExtras(args);
                startActivity(MemIntent);
            }
        });
        addEventTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, AddEvent.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                MemIntent.putExtras(args);
                startActivity(MemIntent);
            }
        });
        EventsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, Events.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                MemIntent.putExtras(args);
                startActivity(MemIntent);
            }
        });
        EventsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, Events.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                MemIntent.putExtras(args);
                startActivity(MemIntent);
            }
        });

        transactionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, Transaction.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                MemIntent.putExtras(args);
                startActivity(MemIntent);
            }
        });
        transactionTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, Transaction.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                MemIntent.putExtras(args);
                startActivity(MemIntent);
            }
        });


        birthdayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bdcalender = new Intent(list_test.this, BirthdayCalender.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                bdcalender.putExtras(args);
                startActivity(bdcalender);
            }
        });
        birthdayImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bdcalender = new Intent(list_test.this, BirthdayCalender.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                bdcalender.putExtras(args);
                startActivity(bdcalender);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUserData();
            }
        });

        //TeamMailHolder = new String[]{TempHolder, tm};
        teamName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(list_test.this, Home.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                home.putExtras(args);
                startActivity(home);
            }
        });

        teamIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(list_test.this, Home.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                home.putExtras(args);
                startActivity(home);
            }
        });

        addTransacImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent home = new Intent(list_test.this, AddTransaction.class);
                    Bundle args = new Bundle();
                    args.putSerializable("USER", profileUser);
                    home.putExtras(args);
                    startActivity(home);
            }
        });

        addTransacTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(list_test.this, AddTransaction.class);
                Bundle args = new Bundle();
                args.putSerializable("USER", profileUser);
                home.putExtras(args);
                startActivity(home);
            }
        });
    }

    private void updateUserData() {
        // get all data from sqlite
        Cursor Upcursor = MainActivity.db.getdata("SELECT * FROM table_user WHERE email = '" + TempHolder + "'");
        if (Upcursor.getCount() == 0) {
            Toast.makeText(list_test.this, "No data with this mail", Toast.LENGTH_LONG).show();
        } else {
            Upcursor.moveToFirst();
            Long id = Upcursor.getLong(0);
            String uemail = Upcursor.getString(1);
            String uaadhaar = Upcursor.getString(2);
            String uname = Upcursor.getString(3);
            String unick_name = Upcursor.getString(4);
            String uteam = Upcursor.getString(5);
            String udob = Upcursor.getString(7);
            String ufood = Upcursor.getString(8);
            String umobile = Upcursor.getString(9);
            String ubgrp = Upcursor.getString(10);
            String upan = Upcursor.getString(11);
            String uevents = Upcursor.getString(12);
            byte[] uimage = Upcursor.getBlob(6);
            String utrans = Upcursor.getString(13);
            String uabout = Upcursor.getString(14);
            String udesig = Upcursor.getString(15);
            String uhobby = Upcursor.getString(16);

            profileUser = new User(id, uemail, uaadhaar, uname, unick_name, uteam, udob, ufood, umobile, ubgrp, upan, uevents, uimage, utrans, uabout,udesig,uhobby);
        }

        yourName.setText("Welcome " + profileUser.getUnick_name());
        teamName.setText(profileUser.getUteam());
        teamName1.setText(profileUser.getUteam());
        usrImg = profileUser.getUimage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(usrImg, 0, usrImg.length);
        yourImage.setImageBitmap(bitmap);

        swipeRefresh.setRefreshing(false);
    }

    Button logout, profile;
    ImageView logoutUserImg;

    private void logoutPopup(Activity popActivity, final User membMail) {

        logoutProfile.setContentView(R.layout.logout_popup);

        int width1 = (int) (popActivity.getResources().getDisplayMetrics().widthPixels * .8);
        int height1 = (int) (popActivity.getResources().getDisplayMetrics().heightPixels * .6);
        logoutProfile.getWindow().setLayout(width1, height1);
        logoutProfile.getWindow().setGravity(Gravity.CENTER);
        logoutProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutProfile.show();

        logout = (Button) logoutProfile.findViewById(R.id.btnLogout);
        profile = (Button) logoutProfile.findViewById(R.id.btnProfile);
        logoutUserImg = (ImageView) logoutProfile.findViewById(R.id.logoutUserImg);

        byte[] usrerImg = membMail.getUimage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(usrerImg, 0, usrerImg.length);
        logoutUserImg.setImageBitmap(bitmap);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(list_test.this, resume_Page.class);
                //newIntent.putExtra("User_Email", membMail.getUemail());
                Bundle args = new Bundle();
                args.putSerializable("USER", membMail);
                newIntent.putExtras(args);
                startActivity(newIntent);
                logoutProfile.dismiss();
            }
        });
    }

    private void Logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(list_test.this, MainActivity.class));
    }

}
