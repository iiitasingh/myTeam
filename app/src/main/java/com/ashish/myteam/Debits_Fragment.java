package com.ashish.myteam;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Debits_Fragment extends Fragment {


    public Debits_Fragment() {
        // Required empty public constructor
    }
    ArrayList<transaction_data> transacList;
    ListView transacListView;
    transactionListAdapter transacAdapter;
    User debitTrans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View transView = inflater.inflate(R.layout.fragment_debits_, container, false);
        transacListView = (ListView)transView.findViewById(R.id.transDebitList);
        transacList = new ArrayList<>();

        debitTrans = (User) getArguments().getSerializable("User_Trans");
        String trans = debitTrans.getUtransaction();
        String[] names = trans.split(",",0);
        String sql = "SELECT * FROM table_transaction WHERE transaction_type = 'debit' AND transaction_id IN (" + DatabaseHelper.makePlaceholders(names.length) + ")  ORDER BY transaction_date";
        Cursor TransDetails = MainActivity.db.getEvents(sql,names);
        transacList.clear();
        long amount =0;
        if(TransDetails.getCount() == 0) {
            transacAdapter = new transactionListAdapter(getActivity(), R.layout.transaction_list_template, transacList);
            Toast.makeText(getActivity(), "No transactions!",Toast.LENGTH_LONG).show();
        }
        else {
            while(TransDetails.moveToNext()){
                String userid = TransDetails.getString(1);
                amount = TransDetails.getLong(2);
                String date = TransDetails.getString(3);
                String type = TransDetails.getString(4);
                String desc = TransDetails.getString(5);
                transacList.add(new transaction_data(amount,date,userid,type,desc));
            }
            transacAdapter = new transactionListAdapter(getActivity(), R.layout.transaction_list_template, transacList);
        }
        transacListView.setAdapter(transacAdapter);

        return transView;
    }

}
