package com.cihankaptan.weather.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cihankaptan.weather.R;
import com.cihankaptan.weather.android.adapter.SectionAdapter;
import com.cihankaptan.weather.android.model.CurrentWeatherResponse;
import com.cihankaptan.weather.android.model.WeeklyWeatherResponse;
import com.cihankaptan.weather.android.service.WeatherApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @InjectView(R.id.content_frame)
    FrameLayout contentFrame;
    @InjectView(R.id.left_drawer)
    ListView mDrawerList;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MainActivity.class.getSimpleName();
    private Location mCurrentLocation;
    private LocationRequest mLocationRequest;
    private LocationManager locationManager;
    private Location gpsLocation;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private int LOCATION_RECUEST;
    private MaterialDialog dialog;


    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mSectionTitles = {"Today", "Forecast"};
    private ActionBar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private double latitude;
    private double longitude;
    private WeatherApi weatherApi;
    private CurrentWeatherResponse todayWR;
    private WeeklyWeatherResponse weeklyWR;
    private Gson gson;
    private int mActionBarSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);



        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        setGoogleApiClient();
        setLocationRequest();
        setRestAdapter();

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isNetworkAvailable()){
            showMaterialDialogNetwork();
        }
        if (!isGPSEnabled){
            showMaterialDialogLocation();
        }

        mTitle = mDrawerTitle = getTitle();
        actionBar = getSupportActionBar();



        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue));

        //Shadow
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerList.setAdapter(new SectionAdapter(mSectionTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                actionBar.setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                actionBar.setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        selectItem(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onStart");
        mGoogleApiClient.connect();

        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        mActionBarSize = (int) styledAttributes.getDimension(0, 0);


        gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        getDataAndSetUI(gpsLocation);


    }

    private void getDataAndSetUI(Location location) {
        if (location != null && isNetworkAvailable()) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            weatherApi.getCurrentWeather(latitude + "", longitude + "", new Callback<CurrentWeatherResponse>() {
                @Override
                public void success(CurrentWeatherResponse currentWeatherResponse, Response response) {
                    Log.e(TAG, "Success");
                    todayWR = currentWeatherResponse;
                    selectItem(0);

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, "failure = " + error.toString());
                }
            });


            weatherApi.getWeaklyWeather(latitude + "", longitude + "", "16", new Callback<WeeklyWeatherResponse>() {
                @Override
                public void success(WeeklyWeatherResponse weeklyWeatherResponse, Response response) {
                    weeklyWR = weeklyWeatherResponse;
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }else if (!isNetworkAvailable()) {
            showMaterialDialogNetwork();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onStop");
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        if (position == 0){
            fragment = TodayFragment.newInstance(todayWR);
        }else{
            String json = gson.toJson(weeklyWR);
            fragment = WeatherFragment.newInstance(json);
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // update selected item title, then close the drawer
        setTitle(mSectionTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), PrefActivity.class);
                intent.putExtra(SearchManager.QUERY, actionBar.getTitle());
                intent.putExtra("actionManager",mActionBarSize);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 0);
                }
                return true;

            case R.id.action_about:
                showMaterialAboutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        actionBar.setTitle(mTitle);
    }

    private void setGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void setLocationRequest() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(60 * 1000 * 5)        // 5 minute
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e(TAG, "Location services connected.");

        if (gpsLocation != null){
            Log.e(TAG, gpsLocation.toString());
            handleNewLocation(gpsLocation);
        }else if(isGPSEnabled && gpsLocation == null && !isNetworkEnabled){
            showMaterialDialogLocation();
        }else if (mCurrentLocation == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }else{
            handleNewLocation(mCurrentLocation);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    private void handleNewLocation(Location mCurrentLocation) {
        Log.e(TAG,"Latitude = "+mCurrentLocation.getLatitude()+"\tLongitude = "+mCurrentLocation.getLongitude());

        getDataAndSetUI(mCurrentLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }


    private void showMaterialDialogNetwork() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Opss , Sorry!")
                .content("Please check network settings.")
                .positiveText("Wifi ")
                .negativeText("Mobile")
                .neutralText("Cancel")
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

    public void showMaterialAboutDialog(){
        new MaterialDialog.Builder(this)
                .title("About")
                .content("Weather app by made \nCihan Kaptan")
                .positiveText("OK")
                .show();
    }

    private void showMaterialDialogLocation() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Opss , Sorry!")
                .content("Please check Location and GPS settings.")
                .positiveText("Location Settings")
                .negativeText("Cancel")
                .autoDismiss(true);
        builder.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                LOCATION_RECUEST = 100;
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), LOCATION_RECUEST);

            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
            }


        });

        dialog = builder.build();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String prefList = sharedPreferences.getString("PREF_LIST", "no selection");
        Log.e(TAG, prefList);
        selectItem(0);

        if (requestCode == Activity.RESULT_OK && resultCode == LOCATION_RECUEST){
            dialog.dismiss();
        }
    }

    public void setRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org/data/2.5")
                .build();

        weatherApi = restAdapter.create(WeatherApi.class);
    }


}
