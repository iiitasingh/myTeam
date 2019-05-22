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
public class Credits_Fragment extends Fragment {


    public Credits_Fragment() {
        // Required empty public constructor
    }
    ArrayList<transaction_data> transList;
    ListView transListView;
    transactionListAdapter transAdapter;
    User creditTrans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View transacView = inflater.inflate(R.layout.fragment_credits, container, false);
        transListView = (ListView)transacView.findViewById(R.id.transCreditList);
        transList = new ArrayList<>();

        creditTrans = (User) getArguments().getSerializable("User_Trans");
        String trans = creditTrans.getUtransaction();
        String[] names = trans.split(",",0);
        String sql = "SELECT * FROM table_transaction WHERE transaction_type = 'credit' AND transaction_id IN (" + DatabaseHelper.makePlaceholders(names.length) + ")  ORDER BY transaction_date";
        Cursor TransacDetails = MainActivity.db.getEvents(sql,names);
        //Cursor TransacDetails = MainActivity.db.getdata("SELECT * FROM table_transaction WHERE transaction_type = 'credit'");
        transList.clear();
        long amount =0;
        if(TransacDetails.getCount() == 0) {
            transAdapter = new transactionListAdapter(getActivity(), R.layout.transaction_list_template, transList);
            Toast.makeText(getActivity(), "There are no contents in this list!",Toast.LENGTH_LONG).show();
        } else {
            while(TransacDetails.moveToNext()){
                String userid = TransacDetails.getString(1);
                amount = TransacDetails.getLong(2);
                String date = TransacDetails.getString(3);
                String type = TransacDetails.getString(4);
                String desc = TransacDetails.getString(5);
                transList.add(new transaction_data(amount,date,userid,type,desc));
            }
            transAdapter = new transactionListAdapter(getActivity(), R.layout.transaction_list_template, transList);
        }
        transListView.setAdapter(transAdapter);
        return transacView;
    }

}
