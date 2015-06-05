package com.cihankaptan.weather.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cihan on 01.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CurrentWeatherResponse implements Parcelable {

    /**
     * dt : 1433188444
     * coord : {"lon":139,"lat":35}
     * weather : [{"icon":"02n","description":"few clouds","main":"Clouds","id":801}]
     * name : Shuzenji
     * cod : 200
     * main : {"temp":292.74,"temp_min":292.04,"humidity":76,"pressure":1014,"temp_max":293.71}
     * clouds : {"all":24}
     * id : 1851632
     * sys : {"country":"JP","sunrise":1433100671,"sunset":1433152345,"id":10294,"type":3,"message":0.0037}
     * base : cmc stations
     * wind : {"deg":234.002,"speed":1.22}
     */
    private String dt;
    private CoordEntity coord;
    private List<WeatherEntity> weather;
    private String name;
    private String cod;
    private MainEntity main;
    private CloudsEntity clouds;
    private String id;
    private SysEntity sys;
    private String base;
    private WindEntity wind;

    public void setDt(String dt) {
        this.dt = dt;
    }

    public void setCoord(CoordEntity coord) {
        this.coord = coord;
    }

    public void setWeather(ArrayList<WeatherEntity> weather) {
        this.weather = weather;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public void setMain(MainEntity main) {
        this.main = main;
    }

    public void setClouds(CloudsEntity clouds) {
        this.clouds = clouds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSys(SysEntity sys) {
        this.sys = sys;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setWind(WindEntity wind) {
        this.wind = wind;
    }

    public String getDt() {
        return dt;
    }

    public CoordEntity getCoord() {
        return coord;
    }

    public List<WeatherEntity> getWeather() {
        return weather;
    }

    public String getName() {
        return name;
    }

    public String getCod() {
        return cod;
    }

    public MainEntity getMain() {
        return main;
    }

    public CloudsEntity getClouds() {
        return clouds;
    }

    public String getId() {
        return id;
    }

    public SysEntity getSys() {
        return sys;
    }

    public String getBase() {
        return base;
    }

    public WindEntity getWind() {
        return wind;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dt);
        dest.writeParcelable(this.coord, 0);
        dest.writeTypedList(weather);
        dest.writeString(this.name);
        dest.writeString(this.cod);
        dest.writeParcelable(this.main, 0);
        dest.writeParcelable(this.clouds, 0);
        dest.writeString(this.id);
        dest.writeParcelable(this.sys, 0);
        dest.writeString(this.base);
        dest.writeParcelable(this.wind, 0);
    }

    public CurrentWeatherResponse() {
    }

    protected CurrentWeatherResponse(Parcel in) {
        this.dt = in.readString();
        this.coord = in.readParcelable(CoordEntity.class.getClassLoader());
        this.weather = in.createTypedArrayList(WeatherEntity.CREATOR);
        this.name = in.readString();
        this.cod = in.readString();
        this.main = in.readParcelable(MainEntity.class.getClassLoader());
        this.clouds = in.readParcelable(CloudsEntity.class.getClassLoader());
        this.id = in.readString();
        this.sys = in.readParcelable(SysEntity.class.getClassLoader());
        this.base = in.readString();
        this.wind = in.readParcelable(WindEntity.class.getClassLoader());
    }

    public static final Creator<CurrentWeatherResponse> CREATOR = new Creator<CurrentWeatherResponse>() {
        public CurrentWeatherResponse createFromParcel(Parcel source) {
            return new CurrentWeatherResponse(source);
        }

        public CurrentWeatherResponse[] newArray(int size) {
            return new CurrentWeatherResponse[size];
        }
    };
}
