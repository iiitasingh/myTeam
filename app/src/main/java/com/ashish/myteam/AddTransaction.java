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
    EditText amount, transDate, transDesc;
    Spinner payee;
    RadioButton radioDebit, radioCredit;
    Button transacSubmit;
    ArrayList<Long> membersIds;
    ArrayList<String> memberNames;
    ArrayList<String> eventNames;
    ArrayList<String> eventMembers;
    String transacMode, payeeName, eventNm, eventMems;
    private DatePickerDialog.OnDateSetListener mDateSet;
    User addUserTrans;
    ArrayList<String> authorized;
    String[] payeeStringArray;
    String[] payeeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);

        addUserTrans = (User) getIntent().getSerializableExtra("USER");

        transacType = (RadioGroup) findViewById(R.id.typeGrp);
        amount = (EditText) findViewById(R.id.transactionAmount);
        transDate = (EditText) findViewById(R.id.transactionDate);
        payee = (Spinner) findViewById(R.id.payeeMember);
        radioDebit = (RadioButton) findViewById(R.id.RadioDebit);
        radioCredit = (RadioButton) findViewById(R.id.RadioCredit);
        transacSubmit = (Button) findViewById(R.id.TransacSubmit);
        transDesc = (EditText) findViewById(R.id.transactionDesc);
        memberNames = new ArrayList<>();
        eventNames = new ArrayList<>();
        membersIds = new ArrayList<>();
        eventMembers = new ArrayList<>();
        payeeArray = new String[2];

        payeeArray[0] = addUserTrans.getId().toString();

        authorized = new ArrayList<>();
        authorized.add("ashish-za.singh@ubs.com");

        Cursor cursor = MainActivity.db.getdata("SELECT ID,name FROM table_user WHERE team = '" + addUserTrans.getUteam() + "'");
        memberNames.clear();
        membersIds.clear();
        if (cursor.getCount() == 0) {
            Toast.makeText(AddTransaction.this, "No Member to show", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                Long id = cursor.getLong(0);
                String name = cursor.getString(1);
                memberNames.add(name);
                membersIds.add(id);
            }
        }

        Cursor cursor1 = MainActivity.db.getdata("SELECT event_name, event_members FROM table_event WHERE event_owner = '" + addUserTrans.getUemail() + "'");
        eventNames.clear();
        eventMembers.clear();
        if (cursor1.getCount() == 0) {
            Toast.makeText(AddTransaction.this, "No Event to show", Toast.LENGTH_LONG).show();
        } else {
            while (cursor1.moveToNext()) {
                String name = cursor1.getString(0);
                String members = cursor1.getString(1);
                eventNames.add(name);
                eventMembers.add(members);
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
                    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, memberNames);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payee.setAdapter(dataAdapter1);
                    transacMode = "credit";
                }
            }

        });

        if (transacType.getCheckedRadioButtonId() == radioCredit.getId()) {
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(AddTransaction.this,
                    R.layout.spinner_text, memberNames);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            payee.setAdapter(dataAdapter2);
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
                        year, month, day);
                dobPicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dobPicker.show();
            }
        });

        mDateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date;
                String dayS = String.valueOf(day);
                String monthS = String.valueOf(month);
                if (month < 10) {
                    monthS = "0" + monthS;
                }
                if (day < 10) {
                    dayS = "0" + dayS;
                }

                date = year + "-" + monthS + "-" + dayS;
                transDate.setText(date);
            }
        };


        transacSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (transacType.getCheckedRadioButtonId() == radioCredit.getId()) {
                    if (authorized.contains(addUserTrans.getUemail())) {
                        String money = amount.getText().toString().trim();
                        String date = transDate.getText().toString().trim();
                        String desc = transDesc.getText().toString().trim();
                        if (money.length() > 0 && date.length() > 0 && desc.length() > 5) {
                            int creditamount = Integer.parseInt(money);
                            long transID = MainActivity.db.newTransaction(payeeName, creditamount, date, transacMode, desc);
                            if (transID > 0) {
                                Long Val = MainActivity.db.addTransaction(payeeArray, String.valueOf(transID));
                                if (Val > 0) {
                                    Toast.makeText(AddTransaction.this, "Transaction added successfully", Toast.LENGTH_LONG).show();
                                    //addUserTrans = resume_Page.updateUserData(AddTransaction.this,addUserTrans.getUemail());
                                    list_test.profileUser = resume_Page.updateUserData(AddTransaction.this,addUserTrans.getUemail());
                                    Intent events = new Intent(AddTransaction.this, Transaction.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("USER", list_test.profileUser);
                                    events.putExtras(args);
                                    startActivity(events);
                                    finish();
                                } else {
                                    Toast.makeText(AddTransaction.this, "Failed", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(AddTransaction.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        } else if (money.length() <= 0) {
                            Toast.makeText(AddTransaction.this, "Add Some Money", Toast.LENGTH_LONG).show();
                        } else if (date.length() <= 0) {
                            Toast.makeText(AddTransaction.this, "Select Transaction Date", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddTransaction.this, "Write Some Description", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AddTransaction.this, "You are not authorized to add a Credit transaction ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (eventNames.size() > 0) {
                        String money = amount.getText().toString().trim();
                        String date = transDate.getText().toString().trim();
                        String desc = transDesc.getText().toString().trim();
                        if (money.length() > 0 && date.length() > 0 && desc.length() > 5) {
                            int creditamount = Integer.parseInt(money);
                            long transID = MainActivity.db.newTransaction(eventNm, creditamount, date, transacMode, desc);
                            if (transID > 0) {
                                Long Val = MainActivity.db.addTransaction(payeeStringArray, String.valueOf(transID));
                                if (Val > 0) {
                                    Toast.makeText(AddTransaction.this, "Transaction added successfully", Toast.LENGTH_LONG).show();
                                    //addUserTrans = resume_Page.updateUserData(AddTransaction.this,addUserTrans.getUemail());
                                    list_test.profileUser = resume_Page.updateUserData(AddTransaction.this,addUserTrans.getUemail());
                                    Intent events = new Intent(AddTransaction.this, Transaction.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("USER", list_test.profileUser);
                                    events.putExtras(args);
                                    startActivity(events);
                                    finish();
                                }
                                else {
                                    Toast.makeText(AddTransaction.this, "Failed", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(AddTransaction.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        } else if (money.length() <= 0) {
                            Toast.makeText(AddTransaction.this, "Add Some Money", Toast.LENGTH_LONG).show();
                        } else if (date.length() <= 0) {
                            Toast.makeText(AddTransaction.this, "Select Transaction Date", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddTransaction.this, "Write Some Description", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AddTransaction.this, "You haven't created any event!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if (transacType.getCheckedRadioButtonId() == radioCredit.getId()) {
            eventMems = "";
            eventNm = "";
            payeeName = parent.getItemAtPosition(position).toString();
            payeeArray[1] = membersIds.get(position).toString();
        } else {
            payeeArray[1] = "";
            payeeName = "";
            eventNm = parent.getItemAtPosition(position).toString();
            eventMems = eventMembers.get(position).toString();
            payeeStringArray = eventMems.split(",", 0);
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
