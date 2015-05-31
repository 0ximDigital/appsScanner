package tscanner.msquared.hr.travelscanner.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;


import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.adapters.SettingsAdapter;

import java.util.List;


public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
    }

        private void init(){
            Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);

            ViewPager pager=(ViewPager)findViewById(R.id.vPager);
            SettingsAdapter pagerAdapter=new SettingsAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

        }



}
