package com.ashish.myteam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Events extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem comingEvent, previousEvent;
    ViewPager viewPager;
    TabPageAdapter pageAdapter;
    String profileMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        tabLayout = findViewById(R.id.tablayout);
        comingEvent = findViewById(R.id.upcoming);
        previousEvent = findViewById(R.id.previous);
        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new TabPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        profileMail = getIntent().getStringExtra("Email");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        Bundle bundle = new Bundle();
//        bundle.putString("edttext", "ABcd");
//        // set Fragmentclass Arguments
//        UpcomingEvents upcomingEvent = new UpcomingEvents();
//        upcomingEvent.setArguments(bundle);
    }


    public class TabPageAdapter extends FragmentPagerAdapter {

        private int numTabs;

        //String profileMail;
        public TabPageAdapter(FragmentManager fm, int numTabs) {
            super(fm);
            this.numTabs = numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new UpcomingEvents();
                    Bundle bundle = new Bundle();
                    bundle.putString("edttext", "abc");
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new PreviousEvents();
                    break;
                default:
                    fragment = null;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return numTabs;
        }
    }
}

