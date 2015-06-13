package com.cihankaptan.weather.android.ui;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cihankaptan.weather.R;
import com.cihankaptan.weather.android.adapter.ForecastAdapter;
import com.cihankaptan.weather.android.model.WeeklyWeatherResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ForecastFragment extends Fragment {

    private static final String TAG = ForecastFragment.class.toString();
    WeeklyWeatherResponse weeklyWeatherResponse;
    @InjectView(R.id.weatherList)
    RecyclerView weatherList;

    private View view;
    private String degreeType;
    public GsonBuilder gsonBuilder;
    public Gson gson;
    public String json;

    public static ForecastFragment newInstance(String json) {

        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString("json", json);
        fragment.setArguments(args);
        return fragment;
    }

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        if (getArguments() != null) {
            this.json = getArguments().getString("json");
            weeklyWeatherResponse = gson.fromJson(json, WeeklyWeatherResponse.class);
            Log.e(TAG, weeklyWeatherResponse.toString());
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String prefList = sharedPreferences.getString(getActivity().getString(R.string.pref_list), "no selection");

        switch (prefList) {
            case "metric":
                degreeType = getString(R.string.celcius);

                break;
            case "imperial":
                degreeType = getString(R.string.fahreneit);

                break;
            default:
                degreeType = getString(R.string.kelvin);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.inject(this, view);

        ForecastAdapter forecastAdapter = new ForecastAdapter(weeklyWeatherResponse, degreeType,getActivity());
        weatherList.setAdapter(forecastAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        weatherList.setLayoutManager(layoutManager);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
