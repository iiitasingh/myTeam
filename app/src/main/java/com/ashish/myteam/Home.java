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
    String[] intentHolder;
    Dialog popupDial,popupDial1;
    User homeUser;
    //Popup Attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        popupDial = new Dialog(this);
        popupDial1 = new Dialog(this);
        tmname = (TextView)findViewById(R.id.team_name);
        TeamList = new ArrayList<>();
        your_teams = (ListView)findViewById(R.id.yourteamlist);
        usericon = (FloatingActionButton)findViewById(R.id.homefab);

        //intentHolder = getIntent().getStringArrayExtra("TEAM_NAME");
        homeUser = (User) getIntent().getSerializableExtra("USER");

        usericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent profile = new Intent(Home.this, bottomappbar.class);
//                profile.putExtra("User_Email",intentHolder[0]);
//                startActivity(profile);
                //popUp.setAlpha(0.5F);
                showPopup1(Home.this,homeUser.getUemail());
            }
        });


        tmname.setText("Team "+homeUser.getUteam());

        Cursor cursor = MainActivity.db.getteamMembers(homeUser.getUteam());
        TeamList.clear();
        if(cursor.getCount() == 0) {
            your_teams.setAdapter(new your_team_list_adapter(this, R.layout.your_team_list_view, TeamList));
            Toast.makeText(Home.this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }
        else {
            while(cursor.moveToNext()){
                String mail = cursor.getString(0);
                String name = cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                TeamList.add(new teams(img,name,mail));
            }
            your_teams.setAdapter(new your_team_list_adapter(this, R.layout.your_team_list_view, TeamList));
        }

        your_teams.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Membmail = TeamList.get(position).getEmail();
                showPopup2(Home.this,Membmail);

            }
        });

    }
    //#####################  PopUp1 Attributes  ##################

    ImageView Usimg;
    TextView MemName, MemEmail, MemTeam, MemDob, MemFood, MemMob, MemBgp, MemNknam;
    String nam, niknam, tnm, Umail, dob, food, mob, bgp;
    byte[] userimg;
    Button edit;
    private void showPopup1(Activity popActivity, final String mail){

        popupDial.setContentView(R.layout.activity_bottomappbar);

        int width1 = (int) (popActivity.getResources().getDisplayMetrics().widthPixels * .85);
        int height1 = (int) (popActivity.getResources().getDisplayMetrics().heightPixels * 0.82);
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
        edit = (Button) popupDial.findViewById(R.id.Edit);

        Cursor data1 = MainActivity.db.getuserAlldetails(mail);
        if (data1.getCount() == 0) {
            Toast.makeText(popActivity, "Database Error", Toast.LENGTH_SHORT).show();
        } else {
            data1.moveToFirst();
            Umail = data1.getString(data1.getColumnIndex("email"));
            nam = data1.getString(data1.getColumnIndex("name"));
            niknam = data1.getString(data1.getColumnIndex("nick_name"));
            tnm = data1.getString(data1.getColumnIndex("team"));
            userimg = data1.getBlob(data1.getColumnIndex("image"));
            dob = data1.getString(data1.getColumnIndex("dob"));
            food = data1.getString(data1.getColumnIndex("food"));
            mob = data1.getString(data1.getColumnIndex("mobile"));
            bgp = data1.getString(data1.getColumnIndex("bl_grp"));
        }

        MemEmail.setText("Email: " + Umail);
        MemName.setText(nam);
        MemNknam.setText(niknam);
        MemTeam.setText("Team: " + tnm);
        MemDob.setText("DOB: " + dob);
        MemFood.setText("Food: " + food);
        MemBgp.setText("BGrp: "+ bgp);
        MemMob.setText("Mob: "+ mob);

        Bitmap bitmap = BitmapFactory.decodeByteArray(userimg, 0, userimg.length);
        Usimg.setImageBitmap(bitmap);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getApplicationContext(),UserProfile.class);
                newIntent.putExtra("User_Email",mail);
                startActivity(newIntent);
                popupDial.dismiss();
            }
        });
    }

    //################   popup2 Attributes  #############################
    ImageView Usimgpop2;
    TextView MemNamepop2, MemEmailpop2, MemTeampop2, MemDobpop2, MemFoodpop2, MemMobpop2, MemBgppop2, MemNknampop2;
    String nampop2, niknampop2, tnmpop2, Umailpop2, dobpop2, foodpop2, mobpop2,bgppop2;
    byte[] userimgpop2;

    private void showPopup2(Activity popActivity, final String membMail){

        popupDial1.setContentView(R.layout.home_header);

        int width1 = (int) (popActivity.getResources().getDisplayMetrics().widthPixels * .85);
        int height1 = (int) (popActivity.getResources().getDisplayMetrics().heightPixels * 0.82);
        popupDial1.getWindow().setLayout(width1, height1);
        popupDial1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupDial1.show();

        Usimgpop2 = (ImageView) popupDial1.findViewById(R.id.Uimg);
        MemNknampop2 = (TextView)popupDial1.findViewById(R.id.MemNknam);
        MemNamepop2 = (TextView)popupDial1.findViewById(R.id.MemName);
        MemEmailpop2 = (TextView)popupDial1.findViewById(R.id.MemEmail);
        MemTeampop2 = (TextView)popupDial1.findViewById(R.id.MemTeam);
        MemDobpop2 = (TextView)popupDial1.findViewById(R.id.MemDob);
        MemFoodpop2 = (TextView)popupDial1.findViewById(R.id.MemFood);
        MemMobpop2 = (TextView)popupDial1.findViewById(R.id.MemMob);
        MemBgppop2 = (TextView)popupDial1.findViewById(R.id.MemBgp);

        Cursor data1 = MainActivity.db.getuserAlldetails(membMail);
        if (data1.getCount() == 0) {
            Toast.makeText(popActivity, "Database Error", Toast.LENGTH_SHORT).show();
        } else {
            data1.moveToFirst();
            Umailpop2 = data1.getString(data1.getColumnIndex("email"));
            nampop2 = data1.getString(data1.getColumnIndex("name"));
            niknampop2 = data1.getString(data1.getColumnIndex("nick_name"));
            tnmpop2 = data1.getString(data1.getColumnIndex("team"));
            userimgpop2 = data1.getBlob(data1.getColumnIndex("image"));
            dobpop2 = data1.getString(data1.getColumnIndex("dob"));
            foodpop2 = data1.getString(data1.getColumnIndex("food"));
            mobpop2 = data1.getString(data1.getColumnIndex("mobile"));
            bgppop2 = data1.getString(data1.getColumnIndex("bl_grp"));
        }

        MemEmailpop2.setText("Email: " + Umailpop2);
        MemNamepop2.setText(nampop2);
        MemNknampop2.setText(niknampop2);
        MemTeampop2.setText("Team: " + tnmpop2);
        //MemDobpop2.setText(": " + dobpop2);
        MemFoodpop2.setText("Food: " + foodpop2);
        MemBgppop2.setText("BGrp: "+bgppop2);
        MemMobpop2.setText("Mob: "+mobpop2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = sdf.parse(dobpop2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MemDobpop2.setText("DOB: " +DateFormat.format("dd MMMM", data));

        Bitmap bitmap = BitmapFactory.decodeByteArray(userimgpop2, 0, userimgpop2.length);
        Usimgpop2.setImageBitmap(bitmap);
    }

}
