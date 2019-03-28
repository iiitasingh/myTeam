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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class list_test extends Activity {

    ImageView yourImage,teamIcon,addEvent,birthdayImg,EventsImg,transactionImg;
    TextView yourName,teamName,teamName1,birthdayTxt,addEventTxt,EventsTxt,transactionTxt;
    SwipeRefreshLayout swipeRefresh;
    String nknm;
    String tm;
    String TempHolder;
    String[] TeamMailHolder;
    byte[] usrImg;
    Dialog logoutProfile;
    Animation atg;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtest);

        session = new Session(this);
        if(!session.loggedin()){
            Logout();
        }

        atg = AnimationUtils.loadAnimation(this, R.anim.atg);

        logoutProfile = new Dialog(this);
        addEvent = (ImageView)findViewById(R.id.addEvent);
        yourImage = (ImageView)findViewById(R.id.userImg);
        teamIcon = (ImageView)findViewById(R.id.teamIcon);
        yourName = (TextView)findViewById(R.id.nickName);
        teamName = (TextView)findViewById(R.id.teamNm);
        teamName1 = (TextView) findViewById(R.id.teamNm1);
        birthdayImg = (ImageView)findViewById(R.id.birthdayImg);
        birthdayTxt = (TextView)findViewById(R.id.birthdayTxt);
        EventsImg = (ImageView)findViewById(R.id.EventsImg);
        EventsTxt = (TextView) findViewById(R.id.EventsTxt);
        addEventTxt = (TextView) findViewById(R.id.addEventTxt);
        transactionImg = (ImageView)findViewById(R.id.transactionImg);
        transactionTxt = (TextView) findViewById(R.id.transactionTxt);

        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);

        TempHolder = getIntent().getStringExtra("UserEmail");

        yourImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutPopup(list_test.this,TempHolder);
            }
        });
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this,AddEvent.class);
                MemIntent.putExtra("Email",TempHolder);
                startActivity(MemIntent);
            }
        });
        addEventTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this,AddEvent.class);
                MemIntent.putExtra("Email",TempHolder);
                startActivity(MemIntent);
            }
        });
        EventsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, Events.class);
                MemIntent.putExtra("Email",TempHolder);
                startActivity(MemIntent);
            }
        });
        EventsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, Events.class);
                MemIntent.putExtra("Email",TempHolder);
                startActivity(MemIntent);
            }
        });

        transactionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, Transaction.class);
                MemIntent.putExtra("Email",TempHolder);
                startActivity(MemIntent);
            }
        });
        transactionTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MemIntent = new Intent(list_test.this, Transaction.class);
                MemIntent.putExtra("Email",TempHolder);
                startActivity(MemIntent);
            }
        });


        birthdayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bdcalender = new Intent(list_test.this,BirthdayCalender.class);
                startActivity(bdcalender);
            }
        });
        birthdayImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bdcalender = new Intent(list_test.this,BirthdayCalender.class);
                startActivity(bdcalender);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUserData();
            }
        });
        Cursor cursor = MainActivity.db.getuserdetails(TempHolder);

        if(cursor.getCount() == 0) {
            Toast.makeText(list_test.this,"Database Error",Toast.LENGTH_SHORT).show();
        }
        else {
            cursor.moveToFirst();
            nknm = cursor.getString(cursor.getColumnIndex("nick_name"));
            tm = cursor.getString(cursor.getColumnIndex("team"));
            usrImg = cursor.getBlob(cursor.getColumnIndex("image"));
        }
        yourName.setText("Welcome "+nknm);
        teamName.setText(tm);
        teamName1.setText(tm);
        Bitmap bitmap = BitmapFactory.decodeByteArray(usrImg, 0, usrImg.length);
        yourImage.setImageBitmap(bitmap);


        TeamMailHolder = new String[]{TempHolder, tm};
        teamName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(list_test.this,Home.class);
                home.putExtra("TEAM_NAME", TeamMailHolder);
                startActivity(home);
            }
        });

        teamIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(list_test.this,Home.class);
                home.putExtra("TEAM_NAME", TeamMailHolder);
                startActivity(home);
            }
        });
    }

    private void updateUserData(){
        // get all data from sqlite
        Cursor cursor = MainActivity.db.getuserdetails(TempHolder);

        if(cursor.getCount() == 0) {
            Toast.makeText(list_test.this,"Database Error",Toast.LENGTH_SHORT).show();
        }
        else {
            cursor.moveToFirst();
            nknm = cursor.getString(cursor.getColumnIndex("nick_name"));
            tm = cursor.getString(cursor.getColumnIndex("team"));
            usrImg = cursor.getBlob(cursor.getColumnIndex("image"));
        }
        yourName.setText("Welcome "+nknm);
        teamName.setText(tm);
        teamName1.setText(tm);
        Bitmap bitmap = BitmapFactory.decodeByteArray(usrImg, 0, usrImg.length);
        yourImage.setImageBitmap(bitmap);

        swipeRefresh.setRefreshing(false);
    }

    Button logout,profile;
    private void logoutPopup(Activity popActivity, final String membMail){

        logoutProfile.setContentView(R.layout.header_layout);

        int width1 = (int) (popActivity.getResources().getDisplayMetrics().widthPixels * .98);
        int height1 = (int) (popActivity.getResources().getDisplayMetrics().heightPixels * 0.10);
        logoutProfile.getWindow().setLayout(width1, height1);
        logoutProfile.getWindow().setGravity(Gravity.BOTTOM);
        logoutProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutProfile.show();

        logout = (Button)logoutProfile.findViewById(R.id.btnLogout);
        profile = (Button)logoutProfile.findViewById(R.id.btnProfile);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(list_test.this,UserProfile.class);
                newIntent.putExtra("User_Email",membMail);
                startActivity(newIntent);
                logoutProfile.dismiss();
            }
        });
    }

    private void Logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(list_test.this,MainActivity.class));
    }

}
