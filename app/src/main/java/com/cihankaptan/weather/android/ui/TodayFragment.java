package com.cihankaptan.weather.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
import butterknife.Optional;


public class TodayFragment extends Fragment {
    private static final String TAG = TodayFragment.class.getSimpleName();
    @Optional @InjectView(R.id.weather_humidity)
    TextView weatherHumidity;
    @Optional @InjectView(R.id.weather_precipitation)
    TextView weatherPrecipitation;
    @Optional @InjectView(R.id.weather_pressure)
    TextView weatherPressure;
    @Optional @InjectView(R.id.first_line)
    LinearLayout firstLine;
    @Optional @InjectView(R.id.weather_wind)
    TextView weatherWind;
    @Optional @InjectView(R.id.weather_direction)
    TextView weatherDirection;
    @Optional @InjectView(R.id.second_line)
    LinearLayout secondLine;
    @Optional @InjectView(R.id.weather_icon)
    ImageView weatherIcon;
    @Optional @InjectView(R.id.weather_condition)
    TextView weatherCondition;
    @Optional @InjectView(R.id.weather_city)
    TextView weatherCity;
    @Optional @InjectView(R.id.weather_degree)
    TextView weatherDegree;
    private String mParam1;
    private CurrentWeatherResponse currentWeatherResponse;
    private View view;
    private String path;
    private String degree;
    private MainActivity activity;


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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentWeatherResponse = getArguments().getParcelable(activity.getString(R.string.today));
        }

        String prefList = activity.weatherApp.getSharedPreferences().
                getString(getActivity().getString(R.string.pref_list), activity.getString(R.string.no_selection));

        if (prefList != null && currentWeatherResponse != null){
            if (prefList.equals(activity.getString(R.string.metric))){
                degree = String.valueOf((int) (Double.parseDouble(currentWeatherResponse.getMain().getTemp()) - 273));

            }else if(prefList.equals(activity.getString(R.string.imperial))){
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
            weatherHumidity.setText(currentWeatherResponse.getMain().getHumidity()+getString(R.string.percent_symbol));
            weatherPressure.setText(currentWeatherResponse.getMain().getPressure()+getString(R.string.hPa));
            weatherWind.setText(currentWeatherResponse.getWind().getSpeed()+getString(R.string.km_h));
            weatherDirection.setText(currentWeatherResponse.getWind().getDirection());
            weatherDegree.setText(degree+getString(R.string.degree_symbol));
            path = getActivity().getString(R.string.open_weather_img_base_url) +
                    currentWeatherResponse.getWeather().get(0).getIcon() + ".png";
            Picasso.with(container.getContext()).load(path)
                                                .into(weatherIcon);
            weatherCity.setText(currentWeatherResponse.getName());
            weatherCondition.setText(currentWeatherResponse.getWeather().get(0).getDescription());
        }


        return view;
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
