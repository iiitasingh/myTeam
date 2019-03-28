package com.ashish.myteam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Transaction extends AppCompatActivity {

    TabLayout TransTabLayout;
    TabItem debit,credit;
    ViewPager TransViewPager;
    ImageView addTrans;
    TransactionPageTabAdapter transactionPageAdapter;
    String profileOwner;
    ArrayList<String> authorized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);

        profileOwner = getIntent().getStringExtra("Email");
        authorized = new ArrayList<>();
        authorized.add("singh.ashish3104@gmail");

        TransTabLayout = findViewById(R.id.TransactionTablayout);
        debit = findViewById(R.id.debits);
        credit = findViewById(R.id.credits);
        TransViewPager = findViewById(R.id.TransactionViewPager);
        addTrans = (ImageView)findViewById(R.id.addTransac) ;
        transactionPageAdapter = new TransactionPageTabAdapter(getSupportFragmentManager(),TransTabLayout.getTabCount());
        TransViewPager.setAdapter(transactionPageAdapter);
        TransViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(TransTabLayout));

        addTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(authorized.contains(profileOwner)) {
                    Intent add = new Intent(Transaction.this, AddTransaction.class);
                    startActivity(add);
                }
                else {
                    Toast.makeText(Transaction.this,"You are not authorized to add a transaction "+profileOwner,Toast.LENGTH_SHORT).show();
                }
            }
        });

        TransTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TransViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
