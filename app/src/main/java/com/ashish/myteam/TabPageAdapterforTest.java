package com.ashish.myteam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPageAdapterforTest extends FragmentPagerAdapter {

    private int numTabs;
    //String profileMail;
    public TabPageAdapterforTest(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment =  new UpcomingEvents();
                Bundle bundle = new Bundle();
                bundle.putString("edttext", "");
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
