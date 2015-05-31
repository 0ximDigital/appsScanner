package tscanner.msquared.hr.travelscanner.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Matej on 5/31/2015.
 */
public class SettingsFragment extends android.support.v4.app.Fragment {


    public static final String TITLE = "Setting";

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
