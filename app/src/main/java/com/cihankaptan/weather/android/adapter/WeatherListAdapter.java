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

import java.util.Calendar;

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
    private String description;
    private int anInt;

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

        long timestamp = Long.valueOf(getItem(position).getDt())*1000l;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        anInt = cal.get(Calendar.DAY_OF_WEEK);
        description = getItem(position).getWeather().get(0).getDescription();
        String[] items = description.split(" ");

        StringBuilder builder = new StringBuilder();
        for (String str: items){
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);
            builder.append(str).append(" ");
        }
        builder.append(getWeekOfDay(anInt));

        weatherCondition.setText(builder.toString());



        return view;
    }


    String getWeekOfDay(int day){
        switch (day){
            case 1: return "Sunday";
            case 2: return "Monday";
            case 3: return "Tuesday";
            case 4: return "Wednesday";
            case 5: return "Thursday";
            case 6: return "Friday";
            case 7: return "Saturday";
            default: return "";
        }
    }
}
