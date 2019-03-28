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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;

public class AddTransaction extends AppCompatActivity implements OnItemSelectedListener {

    RadioGroup transacType;
    EditText amount, transDate;
    Spinner payee;
    RadioButton radioDebit, radioCredit;
    Button transacSubmit;
    ArrayList<String> memberNames, eventNames;
    String[] country = {"India", "USA", "China", "Japan", "Other"};
    String transacMode,spinnerItem;
    private DatePickerDialog.OnDateSetListener mDateSet;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);

        transacType = (RadioGroup) findViewById(R.id.typeGrp);
        amount = (EditText) findViewById(R.id.transactionAmount);
        transDate = (EditText) findViewById(R.id.transactionDate);
        payee = (Spinner) findViewById(R.id.payeeMember);
        radioDebit = (RadioButton) findViewById(R.id.RadioDebit);
        radioCredit = (RadioButton) findViewById(R.id.RadioCredit);
        transacSubmit = (Button) findViewById(R.id.TransacSubmit);
        memberNames = new ArrayList<>();
        eventNames = new ArrayList<>();

        Cursor cursor = MainActivity.db.getdata("SELECT name FROM table_user");
        memberNames.clear();
        if (cursor.getCount() == 0) {
            Toast.makeText(AddTransaction.this, "No Member to show", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                memberNames.add(cursor.getString(0));
            }
        }

        Cursor cursor1 = MainActivity.db.getdata("SELECT event_name FROM table_event");
        eventNames.clear();
        if (cursor1.getCount() == 0) {
            Toast.makeText(AddTransaction.this, "No Event to show", Toast.LENGTH_LONG).show();
        } else {
            while (cursor1.moveToNext()) {
                eventNames.add(cursor1.getString(0));
            }
        }
        transacType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.RadioDebit) {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, eventNames);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payee.setAdapter(dataAdapter);
                    transacMode = "debit";
                } else {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, memberNames);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payee.setAdapter(dataAdapter);
                    transacMode = "credit";
                }
            }

        });

        if (transacType.getCheckedRadioButtonId() == radioCredit.getId())
        {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTransaction.this,
                    R.layout.spinner_text, memberNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            payee.setAdapter(dataAdapter);
            transacMode = "credit";
        }

        payee.setOnItemSelectedListener(this);

        transDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dobPicker = new DatePickerDialog(
                        AddTransaction.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSet,
                        year,month,day);
                dobPicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dobPicker.show();
            }
        });

        mDateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                transDate.setText(date);
            }
        };

//        transacSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String money = amount.getText().toString().trim();
//                String date = transDate.getText().toString().trim();
//                int myNum = 0;
//
////                try {
////                    myNum = Integer.parseInt(amount.getText().toString());
////                } catch(NumberFormatException nfe) {
////                    Toast.makeText(AddTransaction.this, "could not parse"+ nfe, Toast.LENGTH_LONG).show();
////                }
//            }
//        });

        transacSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                userid = 10;
                String money = amount.getText().toString().trim();
                String date = transDate.getText().toString().trim();
                //Toast.makeText(AddTransaction.this, " "+spinnerItem +" "+date+" "+money, Toast.LENGTH_LONG).show();
                if(money.length() > 0 && date.length() > 0)
                {
                    int creditamount = Integer.parseInt(money);
                    long val = MainActivity.db.addTransaction(userid,creditamount,date,transacMode);
                    if(val >0)
                    {
                        Toast.makeText(AddTransaction.this, "Transaction added successfully", Toast.LENGTH_LONG).show();
                        Intent events = new Intent(AddTransaction.this,Transaction.class);
                        startActivity(events);
                        finish();
                    }
                    else {
                        Toast.makeText(AddTransaction.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
                else if(money.length() <=0){
                    Toast.makeText(AddTransaction.this, "Add Some Money", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AddTransaction.this, "Select Transaction Date", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        spinnerItem = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
