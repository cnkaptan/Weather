package com.cihankaptan.weather.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cihankaptan.weather.R;
import com.cihankaptan.weather.android.model.CurrentWeatherResponse;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TodayFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = TodayFragment.class.getSimpleName();
    @InjectView(R.id.weather_humidity)
    TextView weatherHumidity;
    @InjectView(R.id.weather_precipitation)
    TextView weatherPrecipitation;
    @InjectView(R.id.weather_pressure)
    TextView weatherPressure;
    @InjectView(R.id.first_line)
    LinearLayout firstLine;
    @InjectView(R.id.weather_wind)
    TextView weatherWind;
    @InjectView(R.id.weather_direction)
    TextView weatherDirection;
    @InjectView(R.id.second_line)
    LinearLayout secondLine;
    @InjectView(R.id.weather_icon)
    ImageView weatherIcon;
    @InjectView(R.id.weather_condition)
    TextView weatherCondition;
    @InjectView(R.id.weather_city)
    TextView weatherCity;
    @InjectView(R.id.weather_degree)
    TextView weatherDegree;
    private String mParam1;
    private CurrentWeatherResponse currentWeatherResponse;
    private View view;
    private String path;
    private String degree;


    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(CurrentWeatherResponse currentWeatherResponse) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putParcelable("today", currentWeatherResponse);
        fragment.setArguments(args);
        return fragment;
    }

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentWeatherResponse = getArguments().getParcelable("today");
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String prefList = sharedPreferences.getString("PREF_LIST", "no selection");

        if (prefList != null && currentWeatherResponse != null){
            if (prefList.equals("metric")){
                degree = String.valueOf((int) (Double.parseDouble(currentWeatherResponse.getMain().getTemp()) - 273));

            }else if(prefList.equals("imperial")){
                degree  = String.valueOf((int) (Double.parseDouble(currentWeatherResponse.getMain().getTemp()) - 273) * 9 / 5 + 32);

            }else{
                degree =  currentWeatherResponse.getMain().getTemp();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.inject(this, view);

        if (currentWeatherResponse != null) {
            weatherHumidity.setText(currentWeatherResponse.getMain().getHumidity()+"%");
            weatherPressure.setText(currentWeatherResponse.getMain().getPressure()+" hPa");
            weatherWind.setText(currentWeatherResponse.getWind().getSpeed()+" km/h");
            weatherDirection.setText(currentWeatherResponse.getWind().getDirection());
            weatherDegree.setText(degree+"\u00B0");
            path = "http://openweathermap.org/img/w/" +
                    currentWeatherResponse.getWeather().get(0).getIcon() + ".png";
            Picasso.with(container.getContext()).load(path)
                                                .into(weatherIcon);
            weatherCity.setText(currentWeatherResponse.getName());
            weatherCondition.setText(currentWeatherResponse.getWeather().get(0).getDescription());
        }


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
