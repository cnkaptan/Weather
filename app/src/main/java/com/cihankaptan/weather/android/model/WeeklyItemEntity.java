package com.cihankaptan.weather.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnkaptan on 6/4/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class WeeklyItemEntity implements Parcelable {


    /**
     * clouds : 76
     * dt : 1433383200
     * humidity : 94
     * pressure : 1009.14
     * speed : 2.91
     * deg : 64
     * weather : [{}]
     * temp : {}
     */
    private String clouds;
    private String dt;
    private String humidity;
    private String pressure;
    private String speed;
    private String deg;
    private List<WeeklyWeatherEntity> weather;
    private WeeklyTempEntity temp;


    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public List<WeeklyWeatherEntity> getWeather() {
        return weather;
    }

    public void setWeather(List<WeeklyWeatherEntity> weather) {
        this.weather = weather;
    }

    public WeeklyTempEntity getTemp() {
        return temp;
    }

    public void setTemp(WeeklyTempEntity temp) {
        this.temp = temp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clouds);
        dest.writeString(this.dt);
        dest.writeString(this.humidity);
        dest.writeString(this.pressure);
        dest.writeString(this.speed);
        dest.writeString(this.deg);
        dest.writeList(this.weather);
        dest.writeParcelable(this.temp, 0);
    }

    public WeeklyItemEntity() {
    }

    protected WeeklyItemEntity(Parcel in) {
        this.clouds = in.readString();
        this.dt = in.readString();
        this.humidity = in.readString();
        this.pressure = in.readString();
        this.speed = in.readString();
        this.deg = in.readString();
        this.weather = new ArrayList<WeeklyWeatherEntity>();
        in.readList(this.weather, List.class.getClassLoader());
        this.temp = in.readParcelable(WeeklyTempEntity.class.getClassLoader());
    }

    public static final Creator<WeeklyItemEntity> CREATOR = new Creator<WeeklyItemEntity>() {
        public WeeklyItemEntity createFromParcel(Parcel source) {
            return new WeeklyItemEntity(source);
        }

        public WeeklyItemEntity[] newArray(int size) {
            return new WeeklyItemEntity[size];
        }
    };
}
