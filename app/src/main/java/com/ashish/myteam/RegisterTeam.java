package com.ashish.myteam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterTeam extends AppCompatActivity {


    EditText teamN, email, pin;
    Button registerbutton;
    DatabaseHelper db1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);

        db1 = new DatabaseHelper(this);

        teamN = (EditText) findViewById(R.id.registerTeamName);
        email = (EditText) findViewById(R.id.registerTeamEmail);
        pin = (EditText) findViewById(R.id.registerTeamPin);
        registerbutton = (Button) findViewById(R.id.button_register);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamname = teamN.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pn = pin.getText().toString().trim();

                if (teamname.length() != 0 && mail.length() != 0 && pn.length() >= 4) {
                    boolean res1 = db1.checkteamname(teamname);
                    if (res1) {
                        Toast.makeText(RegisterTeam.this, "Team already exist", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean res = db1.checkregisteringEmail(mail);
                        if (res) {
                            Toast.makeText(RegisterTeam.this, "You already registered a Team", Toast.LENGTH_SHORT).show();
                        } else {
                            long val = db1.addTeam(teamname.toUpperCase(), mail, pn);
                            if (val > 0) {
                                Toast.makeText(RegisterTeam.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent moveselectTeam = new Intent(RegisterTeam.this, teamSelection.class);
                                startActivity(moveselectTeam);
                            } else {
                                Toast.makeText(RegisterTeam.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
                else {
                    if (teamname.length() < 1){
                        Toast.makeText(RegisterTeam.this, "Team name should not be null", Toast.LENGTH_SHORT).show();
                    }
                    else if (mail.length() < 1) {
                        Toast.makeText(RegisterTeam.this, "Mail should not be null", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterTeam.this, "Team Pin should have at least 4 Characters", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }
}
