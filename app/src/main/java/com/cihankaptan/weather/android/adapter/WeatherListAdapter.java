package com.cihankaptan.weather.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cihankaptan.weather.R;
import com.cihankaptan.weather.android.model.WeeklyItemEntity;
import com.cihankaptan.weather.android.model.WeeklyWeatherResponse;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cnkaptan on 6/3/15.
 */
public class WeatherListAdapter extends BaseAdapter {

    @InjectView(R.id.weather_icon)
    ImageView weatherIcon;
    @InjectView(R.id.weather_condition)
    TextView weatherCondition;
    @InjectView(R.id.weather_degree)
    TextView weatherDegree;

    WeeklyWeatherResponse weeklyWeatherResponse;
    private String path;
    private String degreeType;
    private String degree;
    public final String CELCIUS = "celcius";
    public final String FAHRENEIT = "fahreneit";
    public final String KELVIN = "kelvin";

    public WeatherListAdapter(WeeklyWeatherResponse weeklyWeatherResponse, String degreeType) {
        this.weeklyWeatherResponse = weeklyWeatherResponse;
        this.degreeType = degreeType;
    }

    @Override
    public int getCount() {
        return weeklyWeatherResponse.getList().size();
    }

    @Override
    public WeeklyItemEntity getItem(int position) {
        return weeklyWeatherResponse.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list_item, parent, false);
        ButterKnife.inject(this,view);


        path = "http://openweathermap.org/img/w/" +
                getItem(position).getWeather().get(0).getIcon() + ".png";
        Picasso.with(parent.getContext()).load(path)
                .into(weatherIcon);

        degree = getItem(position).getTemp().getDay();
        if (degreeType.equals(CELCIUS)){
                degree = String.valueOf((int) (Double.parseDouble(degree) - 273));

        }else if(degreeType.equals(FAHRENEIT)){
                degree  = String.valueOf((int) (Double.parseDouble(degree) - 273) * 9 / 5 + 32);

        }
        weatherDegree.setText(degree+"\u00B0");
        weatherCondition.setText(getItem(position).getWeather().get(0).getDescription());



        return view;
    }
}
