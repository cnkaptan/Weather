package com.cihankaptan.weather.android.ui;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cihankaptan.weather.R;
import com.cihankaptan.weather.android.adapter.WeatherListAdapter;
import com.cihankaptan.weather.android.model.WeeklyWeatherResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    private static final String TAG = WeatherFragment.class.toString();
    WeeklyWeatherResponse weeklyWeatherResponse;
    @InjectView(R.id.weather_list)
    ListView weatherList;
    private View view;
    private String degreeType;
    public final String CELCIUS = "celcius";
    public final String FAHRENEIT = "fahreneit";
    public final String KELVIN = "kelvin";
    public GsonBuilder gsonBuilder;
    public Gson gson;
    public String json;

    public static WeatherFragment newInstance(String json) {

        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString("json",json);
        fragment.setArguments(args);
        return fragment;
    }

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        if (getArguments() != null) {
            this.json = getArguments().getString("json");
            weeklyWeatherResponse = gson.fromJson(json,WeeklyWeatherResponse.class);
            Log.e(TAG, weeklyWeatherResponse.toString());
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String prefList = sharedPreferences.getString("PREF_LIST", "no selection");

        if (prefList != null){
            if (prefList.equals("metric")){
                degreeType = CELCIUS;
//                degree = String.valueOf((int)(Double.parseDouble(currentWeatherResponse.getMain().getTemp()) - 273));

            }else if(prefList.equals("imperial")){
                degreeType = FAHRENEIT;
//                degree  = String.valueOf((int)(Double.parseDouble(currentWeatherResponse.getMain().getTemp()) - 273)*9/5 +32);

            }else{
                degreeType = KELVIN;
//                degree =  currentWeatherResponse.getMain().getTemp();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.inject(this, view);

        weatherList.setAdapter(new WeatherListAdapter(weeklyWeatherResponse,degreeType));

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
