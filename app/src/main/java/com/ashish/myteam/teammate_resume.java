package com.ashish.myteam;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class teammate_resume extends AppCompatActivity {

    User resumeMail;
    String teamMateMail;
    ImageView resumeWallpaper;
    TextView resumeName, resumeDesignation, textVwaboutBtn, resumeHobbies, resumeAbout, resumeEmail;
    CircleImageView circleResumeImageView;
    Dialog aboutPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teammate_resume);

        teamMateMail = getIntent().getStringExtra("USER_MAIL");
        resumeMail = updateUserData(teamMateMail);

        resumeWallpaper = (ImageView) findViewById(R.id.teamMateresumeWallpaper);
        resumeName = (TextView) findViewById(R.id.teamMateresumeName);
        resumeDesignation = (TextView) findViewById(R.id.teamMateresumeDesignation);
        textVwaboutBtn = (TextView) findViewById(R.id.teamMateResumeAbout);
        resumeHobbies = (TextView) findViewById(R.id.teamMateresumeHobbies);
        resumeAbout = (TextView) findViewById(R.id.teamMateabout);
        resumeEmail = (TextView) findViewById(R.id.teamMateresumeEmail);
        circleResumeImageView = (CircleImageView) findViewById(R.id.teamMateResumeImage);
        aboutPopup = new Dialog(this);

        setResumeData(resumeMail);

        textVwaboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutPopup(teammate_resume.this, resumeMail);
            }
        });
    }

    private User updateUserData(String email) {
        User profileUser = new User();
        Cursor Upcursor = MainActivity.db.getdata("SELECT * FROM table_user WHERE email = '" + email + "'");
        if (Upcursor.getCount() == 0) {
            Toast.makeText(teammate_resume.this, "No data with this mail", Toast.LENGTH_LONG).show();
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

            profileUser = new User(id, uemail, uaadhaar, uname, unick_name, uteam, udob, ufood, umobile, ubgrp, upan, uevents, uimage, utrans, uabout, udesig, uhobby);
        }
        return profileUser;
    }
    public void setResumeData(User ower) {

        byte[] usrerImg = ower.getUimage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(usrerImg, 0, usrerImg.length);
        circleResumeImageView.setImageBitmap(bitmap);
        resumeName.setText(ower.getUname());
        resumeDesignation.setText(ower.getUdesignation());
        resumeHobbies.setText(ower.getUhobbies());
        resumeAbout.setText(ower.getUabout());
        resumeEmail.setText(ower.getUemail());
    }

    ImageView Usimgpop2;
    TextView MemNamepop2, MemEmailpop2, MemTeampop2, MemDobpop2, MemFoodpop2, MemMobpop2, MemBgppop2, MemNknampop2,MemDesig;
    byte[] userimgpop2;

    private void aboutPopup(Activity popActivity, final User userAbout) {

        aboutPopup.setContentView(R.layout.home_header);

        int width1 = (int) (popActivity.getResources().getDisplayMetrics().widthPixels * .82);
        int height1 = (int) (popActivity.getResources().getDisplayMetrics().heightPixels * 0.75);
        aboutPopup.getWindow().setLayout(width1, height1);
        aboutPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        aboutPopup.show();

        Usimgpop2 = (ImageView) aboutPopup.findViewById(R.id.Uimg);
        MemNknampop2 = (TextView) aboutPopup.findViewById(R.id.MemNknam);
        MemNamepop2 = (TextView) aboutPopup.findViewById(R.id.MemName);
        MemEmailpop2 = (TextView) aboutPopup.findViewById(R.id.MemEmail);
        MemTeampop2 = (TextView) aboutPopup.findViewById(R.id.MemTeam);
        MemDobpop2 = (TextView) aboutPopup.findViewById(R.id.MemDob);
        MemFoodpop2 = (TextView) aboutPopup.findViewById(R.id.MemFood);
        MemMobpop2 = (TextView) aboutPopup.findViewById(R.id.MemMob);
        MemBgppop2 = (TextView) aboutPopup.findViewById(R.id.MemBgp);
        MemDesig = (TextView) aboutPopup.findViewById(R.id.MemDesig);

        MemEmailpop2.setText("Email: " + userAbout.getUemail());
        MemNamepop2.setText(userAbout.getUname());
        MemNknampop2.setText(userAbout.getUnick_name());
        MemDesig.setText(userAbout.getUdesignation());
        MemTeampop2.setText("Team: " + userAbout.getUteam());
        MemFoodpop2.setText("Food: " + userAbout.getUfood());
        MemBgppop2.setText("BGrp: " + userAbout.getUbgrp());
        MemMobpop2.setText("Mob: " + userAbout.getUmobile());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = sdf.parse(userAbout.getUdob());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MemDobpop2.setText("DOB: " + DateFormat.format("dd MMMM", data));
        userimgpop2 = userAbout.getUimage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(userimgpop2, 0, userimgpop2.length);
        Usimgpop2.setImageBitmap(bitmap);
    }
}
