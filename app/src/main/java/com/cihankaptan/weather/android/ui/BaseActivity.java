package com.cihankaptan.weather.android.ui;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cihankaptan.weather.R;
import com.cihankaptan.weather.android.WeatherAppLication;
import com.cihankaptan.weather.android.service.WeatherApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;

/**
 * Created by cnkaptan on 6/6/15.
 */
public class BaseActivity extends AppCompatActivity {
    public LocationManager locationManager;
    public boolean isGPSEnabled;
    public boolean isNetworkEnabled;
    public WeatherApi weatherApi;
    public Gson gson;
    public ActionBar actionBar;
    public WeatherAppLication weatherApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        weatherApp = (WeatherAppLication)getApplication();
        super.onCreate(savedInstanceState);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        setRestAdapter();

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    public void setRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.open_weather_base_url))
                .build();

        weatherApi = restAdapter.create(WeatherApi.class);
    }


    public void showMaterialAboutDialog(){
        new MaterialDialog.Builder(this)
                .title(R.string.about)
                .content(R.string.about_me_by_person)
                .positiveText(R.string.ok)
                .show();
    }

    public void showMaterialDialogNetwork() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(R.string.sorry)
                .content(R.string.check_network_text)
                .positiveText(R.string.wifi)
                .negativeText(R.string.mobile)
                .neutralText(R.string.cancel)
                .autoDismiss(true);
        builder.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);

            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
//                               startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS),0);
                startActivityForResult(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS), 0);
            }

            @Override
            public void onNeutral(MaterialDialog dialog) {
                super.onNeutral(dialog);

            }
        });
        builder.show();

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
}
