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
    Spinner payee,payeeEvents;
    RadioButton radioDebit, radioCredit;
    Button transacSubmit;
    ArrayList<Long> membersIds;
    ArrayList<String> memberNames;
    ArrayList<String> eventNames;
    ArrayList<String> eventMembers;
    ArrayList<Long> eventIds;
    String[] nothing = {""};
    String transacMode, payeeName, eventNm, eventMems;
    Long selectedEventId;
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
        payeeEvents = (Spinner) findViewById(R.id.payeeEvents);
        radioDebit = (RadioButton) findViewById(R.id.RadioDebit);
        radioCredit = (RadioButton) findViewById(R.id.RadioCredit);
        transacSubmit = (Button) findViewById(R.id.TransacSubmit);
        transDesc = (EditText) findViewById(R.id.transactionDesc);
        memberNames = new ArrayList<>();
        eventNames = new ArrayList<>();
        membersIds = new ArrayList<>();
        eventMembers = new ArrayList<>();
        eventIds = new ArrayList<>();
        payeeArray = new String[2];

        payeeArray[0] = addUserTrans.getId().toString();

        authorized = new ArrayList<>();

        Cursor cursor1 = MainActivity.db.getdata("SELECT event_ID, event_name, event_members FROM table_event WHERE contribution = 'true' AND event_owner = '" + addUserTrans.getUemail() + "'");
        eventNames.clear();
        eventMembers.clear();
        eventIds.clear();
        if (cursor1.getCount() == 0) {
            Toast.makeText(AddTransaction.this, "No Event to show", Toast.LENGTH_LONG).show();
        } else {
            while (cursor1.moveToNext()) {
                Long id = cursor1.getLong(0);
                String name = cursor1.getString(1);
                String members = cursor1.getString(2);
                eventIds.add(id);
                eventNames.add(name);
                eventMembers.add(members);
            }
        }

        transacType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.RadioDebit) {
                    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, nothing);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payee.setEnabled(false);
                    payee.setClickable(false);
                    payee.setAdapter(dataAdapter1);

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, eventNames);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payeeEvents.setEnabled(true);
                    payeeEvents.setClickable(true);
                    payeeEvents.setAdapter(dataAdapter);

                    transDesc.setFocusableInTouchMode(true);
                    transDesc.requestFocus();
                    transDesc.setFocusable(true);
                    transDesc.setClickable(true);
                    transacMode = "debit";
                } else {
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, memberNames);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payee.setEnabled(true);
                    payee.setClickable(true);
                    payee.setAdapter(dataAdapter2);

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, eventNames);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payeeEvents.setEnabled(true);
                    payeeEvents.setClickable(true);
                    payeeEvents.setAdapter(dataAdapter);

                    transDesc.setFocusableInTouchMode(false);
                    transDesc.requestFocus();
                    transDesc.setFocusable(false);
                    transDesc.setClickable(false);
                    transacMode = "credit";
                }
            }

        });

        if (transacType.getCheckedRadioButtonId() == radioCredit.getId() && eventNames.size() > 0) {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTransaction.this,
                    R.layout.spinner_text, eventNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            transDesc.setFocusableInTouchMode(false);
            transDesc.requestFocus();
            transDesc.setFocusable(false);
            transDesc.setClickable(false);
            payeeEvents.setEnabled(true);
            payeeEvents.setClickable(true);
            payeeEvents.setAdapter(dataAdapter);
            transacMode = "credit";
        }

        payee.setOnItemSelectedListener(this);
        payeeEvents.setOnItemSelectedListener(this);

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
                    if (eventNames.size() > 0) {
                        String money = amount.getText().toString().trim();
                        String date = transDate.getText().toString().trim();
                        String desc = "";
                        if (money.length() > 0 && date.length() > 0) {
                            int creditAmount = Integer.parseInt(money);
                            long transID = MainActivity.db.newTransaction(payeeName, creditAmount, date, transacMode, desc);
                            if (transID > 0) {
                                Long val1 = MainActivity.db.addCreditTransaction(selectedEventId, String.valueOf(transID));
                                Long val2 = MainActivity.db.addCreditMember(selectedEventId, payeeArray[1]);
                                Long Val = MainActivity.db.addTransaction(payeeArray, String.valueOf(transID));
                                if (Val > 0 && val1 > 0 && val2 > 0) {
                                    updateCreditAmt(selectedEventId);
                                    Toast.makeText(AddTransaction.this, "Transaction added successfully", Toast.LENGTH_LONG).show();
                                    addUserTrans = resume_Page.updateUserData(AddTransaction.this,addUserTrans.getUemail());
                                    list_test.profileUser = resume_Page.updateUserData(AddTransaction.this, addUserTrans.getUemail());
                                    Intent events = new Intent(AddTransaction.this, Transaction.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("USER", list_test.profileUser);
                                    events.putExtras(args);
                                    startActivity(events);
                                    finish();
                                } else {
                                    Toast.makeText(AddTransaction.this, "Failed val =" + Val + " val1=" + val1 + " val2=" + val2, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(AddTransaction.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        } else if (money.length() <= 0) {
                            Toast.makeText(AddTransaction.this, "Add Some Money", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddTransaction.this, "Select Transaction Date", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(AddTransaction.this, "You haven't created any event!", Toast.LENGTH_SHORT).show();
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
                                Long val1 = MainActivity.db.addDebitTransaction(selectedEventId, String.valueOf(transID));
                                Long Val = MainActivity.db.addTransaction(payeeStringArray, String.valueOf(transID));
                                if (Val > 0 && val1 >0) {
                                    updateCreditAmt(selectedEventId);
                                    Toast.makeText(AddTransaction.this, "Transaction added successfully", Toast.LENGTH_LONG).show();
                                    addUserTrans = resume_Page.updateUserData(AddTransaction.this,addUserTrans.getUemail());
                                    list_test.profileUser = resume_Page.updateUserData(AddTransaction.this,addUserTrans.getUemail());
                                    Intent events = new Intent(AddTransaction.this, Transaction.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("USER", list_test.profileUser);
                                    events.putExtras(args);
                                    startActivity(events);
                                    finish();
                                }
                                else {
                                    Toast.makeText(AddTransaction.this, "Failed val="+Val+" Val1="+val1, Toast.LENGTH_LONG).show();
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
        if(parent.getId() == R.id.payeeEvents)
        {
            if(eventNames.size() >0) {
                selectedEventId = eventIds.get(position);
                eventNm = parent.getItemAtPosition(position).toString();
                eventMems = eventMembers.get(position).toString();
                payeeStringArray = eventMems.split(",", 0);

                String sql = "SELECT ID,name FROM table_user WHERE ID IN (" + DatabaseHelper.makePlaceholders(payeeStringArray.length) + ")";
                Cursor membersDetails = MainActivity.db.getEvents(sql, payeeStringArray);
                memberNames.clear();
                membersIds.clear();
                if (membersDetails.getCount() == 0) {
                    Toast.makeText(AddTransaction.this, "No Member to show", Toast.LENGTH_LONG).show();
                } else {
                    while (membersDetails.moveToNext()) {
                        Long Memid = membersDetails.getLong(0);
                        String name = membersDetails.getString(1);
                        memberNames.add(name);
                        membersIds.add(Memid);
                    }
                }

                if (transacType.getCheckedRadioButtonId() == radioDebit.getId()) {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, nothing);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payee.setEnabled(false);
                    payee.setClickable(false);
                    transDesc.setFocusableInTouchMode(true);
                    transDesc.requestFocus();
                    transDesc.setFocusable(true);
                    transDesc.setClickable(true);
                    payee.setAdapter(dataAdapter);
                    transacMode = "debit";
                } else {
                    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(AddTransaction.this,
                            R.layout.spinner_text, memberNames);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payee.setEnabled(true);
                    payee.setClickable(true);
                    transDesc.setFocusableInTouchMode(false);
                    transDesc.requestFocus();
                    transDesc.setFocusable(false);
                    transDesc.setClickable(false);
                    payee.setAdapter(dataAdapter1);
                    transacMode = "credit";
                }
            }
        }
        else if(parent.getId() == R.id.payeeMember) {
            if (memberNames.size() > 0) {
                payeeName = parent.getItemAtPosition(position).toString();
                payeeArray[1] = membersIds.get(position).toString();
                //Toast.makeText(AddTransaction.this, payeeName +"Selected", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void updateCreditAmt(long eventId) {

        long event_contri = 0,spent_amount = 0 ,remaining_contri = 0;
        String credit_transactions = "";
        String debit_transactions = "";
        String[] CrTransArray;
        String[] DbtTransArray;

        Cursor cursor = MainActivity.db.getdata("SELECT credit_transactions, debit_transactions FROM table_event WHERE event_ID =" + eventId);
        if (cursor.getCount() == 0) {
            Toast.makeText(AddTransaction.this, "No transactions", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                credit_transactions = cursor.getString(0);
                debit_transactions = cursor.getString(1);
            }
        }
        if(credit_transactions.length() > 0) {
            CrTransArray = credit_transactions.split(",", 0);
            String sql = "SELECT sum(amount) FROM table_transaction WHERE transaction_id IN (" + DatabaseHelper.makePlaceholders(CrTransArray.length) + ")";
            Cursor Credit = MainActivity.db.getEvents(sql, CrTransArray);
            if (Credit.getCount() == 0) {
                Toast.makeText(AddTransaction.this, "No Credit Transaction", Toast.LENGTH_LONG).show();
            } else {
                while (Credit.moveToNext()) {
                    event_contri = Credit.getLong(0);
                }
            }
        }

        if(debit_transactions.length() > 0) {
            DbtTransArray = debit_transactions.split(",", 0);
            String sql = "SELECT sum(amount) FROM table_transaction WHERE transaction_id IN (" + DatabaseHelper.makePlaceholders(DbtTransArray.length) + ")";
            Cursor debit = MainActivity.db.getEvents(sql, DbtTransArray);
            if (debit.getCount() == 0) {
                Toast.makeText(AddTransaction.this, "No Debit Transaction", Toast.LENGTH_LONG).show();
            } else {
                while (debit.moveToNext()) {
                    spent_amount = debit.getLong(0);
                }
            }
        }

        remaining_contri = event_contri - spent_amount;
        Long res = MainActivity.db.updateContriAmounts(event_contri,spent_amount,remaining_contri,eventId);

        if(res > 0 ){
            //Toast.makeText(AddTransaction.this, "amounts = "+event_contri+"="+spent_amount+"="+remaining_contri, Toast.LENGTH_LONG).show();
        }
    }
}
