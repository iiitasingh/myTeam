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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View transView = inflater.inflate(R.layout.fragment_debits_, container, false);
        transacListView = (ListView)transView.findViewById(R.id.transDebitList);

        transacList = new ArrayList<>();
        Cursor TransDetails = MainActivity.db.getdata("SELECT * FROM table_transaction WHERE transaction_type = 'debit'");
        transacList.clear();
        long userid=0 ,amount =0;
        if(TransDetails.getCount() == 0) {
            transacAdapter = new transactionListAdapter(getActivity(), R.layout.transaction_list_template, transacList);
            Toast.makeText(getActivity(), "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }
        else {
            while(TransDetails.moveToNext()){
                userid = TransDetails.getLong(1);
                amount = TransDetails.getLong(2);
                String date = TransDetails.getString(3);
                String type = TransDetails.getString(4);
                //byte[] img = eventDetails.getBlob(1);
                transacList.add(new transaction_data(amount,date,userid,type));
            }
            transacAdapter = new transactionListAdapter(getActivity(), R.layout.transaction_list_template, transacList);
        }
        transacListView.setAdapter(transacAdapter);

        return transView;
    }

}
