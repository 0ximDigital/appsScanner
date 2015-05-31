package tscanner.msquared.hr.travelscanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tscanner.msquared.hr.travelscanner.fragments.InfoFragment;
import tscanner.msquared.hr.travelscanner.fragments.SettingsFragment;


public class SettingsAdapter extends FragmentPagerAdapter {

    public SettingsAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return InfoFragment.newInstance();
            case 1:
                return SettingsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return InfoFragment.TITLE;
            case 1:
                return SettingsFragment.TITLE;
            default:
                return "Unknown" + position;
        }
    }
}