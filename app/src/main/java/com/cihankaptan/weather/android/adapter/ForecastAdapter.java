package com.cihankaptan.weather.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cihankaptan.weather.R;
import com.cihankaptan.weather.android.model.WeeklyItemEntity;
import com.cihankaptan.weather.android.model.WeeklyWeatherResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by cnkaptan on 6/3/15.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    ArrayList<WeeklyItemEntity> forecastList;
    private String path;
    private String degreeType;
    private String degree;
    public final String CELCIUS = "celcius";
    public final String FAHRENEIT = "fahreneit";
    public final String KELVIN = "kelvin";
    private String description;
    private int anInt;

    public ForecastAdapter(WeeklyWeatherResponse weeklyWeatherResponse){
        forecastList = (ArrayList<WeeklyItemEntity>) weeklyWeatherResponse.getList();
    }


    public ForecastAdapter(WeeklyWeatherResponse weeklyWeatherResponse, String degreeType) {
        forecastList = (ArrayList<WeeklyItemEntity>)weeklyWeatherResponse.getList();
        this.degreeType = degreeType;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list_item, parent, false);
        ForecastViewHolder viewHolder = new ForecastViewHolder(view,parent.getContext());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.bind(forecastList.get(position));
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{
        Context context;
        ImageView weatherIcon;
        TextView weatherCondition;
        TextView weatherDegree;
        public ForecastViewHolder(View itemView,Context context) {
            super(itemView);
            this.context = context;
            weatherIcon =(ImageView)itemView.findViewById(R.id.weather_icon);
            weatherCondition= (TextView)itemView.findViewById(R.id.weather_condition);
            weatherDegree = (TextView)itemView.findViewById(R.id.weather_degree);
        }


        public void bind(WeeklyItemEntity weeklyItemEntity){
            path = "http://openweathermap.org/img/w/" +
                            weeklyItemEntity.getWeather().get(0).getIcon() + ".png";
            Picasso.with(context).load(path)
                    .into(weatherIcon);

            degree = weeklyItemEntity.getTemp().getDay();
            if (degreeType.equals(CELCIUS)){
                    degree = String.valueOf((int) (Double.parseDouble(degree) - 273));

            }else if(degreeType.equals(FAHRENEIT)){
                    degree  = String.valueOf((int) (Double.parseDouble(degree) - 273) * 9 / 5 + 32);

            }
            weatherDegree.setText(degree+"\u00B0");

            long timestamp = Long.valueOf(weeklyItemEntity.getDt())*1000l;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            anInt = cal.get(Calendar.DAY_OF_WEEK);
            description = weeklyItemEntity.getWeather().get(0).getDescription();
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
        }
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
