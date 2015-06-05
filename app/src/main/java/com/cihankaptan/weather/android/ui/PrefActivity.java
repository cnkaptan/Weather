package com.cihankaptan.weather.android.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.cihankaptan.weather.R;


/**
 * Created by cnkaptan on 6/4/15.
 */
public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }



}
