package com.ashish.myteam;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


public class bottomappbar extends AppCompatActivity {

    /*
    ImageView Usimg;
    TextView MemName, MemEmail, MemTeam, MemDob, MemFood, MemMob, MemBgp, MemNknam;
    String nam, niknam, tnm, Umail, dob, food;
    byte[] userimg;
    String UserIdHolder;
    Button edit;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomappbar);
/*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .82));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

        //UserIdHolder = getIntent().getStringExtra("User_Email");


        Usimg = (ImageView)findViewById(R.id.PopUpUserImg);
        MemNknam = (TextView) findViewById(R.id.MemNknam1);
        MemName = (TextView) findViewById(R.id.MemName1);
        MemEmail = (TextView) findViewById(R.id.MemEmail1);
        MemTeam = (TextView) findViewById(R.id.MemTeam1);
        MemDob = (TextView) findViewById(R.id.MemDob1);
        MemFood = (TextView) findViewById(R.id.MemFood1);
        MemMob = (TextView) findViewById(R.id.MemMob1);
        MemBgp = (TextView) findViewById(R.id.MemBgp1);
        edit = (Button) findViewById(R.id.Edit);


        Cursor data = MainActivity.db.getuserAlldetails(UserIdHolder);
        if (data.getCount() == 0) {
            Toast.makeText(bottomappbar.this, "Database Error", Toast.LENGTH_SHORT).show();
        } else {
            data.moveToFirst();
            Umail = data.getString(data.getColumnIndex("email"));
            nam = data.getString(data.getColumnIndex("name"));
            niknam = data.getString(data.getColumnIndex("nick_name"));
            tnm = data.getString(data.getColumnIndex("team"));
            userimg = data.getBlob(data.getColumnIndex("image"));
            dob = data.getString(data.getColumnIndex("dob"));
            food = data.getString(data.getColumnIndex("food"));
        }

        MemEmail.setText(": " + Umail);
        MemName.setText(nam);
        MemNknam.setText(niknam);
        MemTeam.setText(": " + tnm);
        MemDob.setText(": " + dob);
        MemFood.setText(": " + food);
        MemBgp.setText(": B+");
        MemMob.setText(": 999999999");

        Bitmap bitmap = BitmapFactory.decodeByteArray(userimg, 0, userimg.length);
        Usimg.setImageBitmap(bitmap);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(bottomappbar.this, "Edit Page will Open", Toast.LENGTH_SHORT).show();
                Intent newIntent = new Intent(bottomappbar.this,UserProfile.class);
                newIntent.putExtra("User_Email",UserIdHolder);
                startActivity(newIntent);
                finish();
            }
        });
        */
    }

}
