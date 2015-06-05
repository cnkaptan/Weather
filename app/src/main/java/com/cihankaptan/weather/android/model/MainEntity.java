package com.cihankaptan.weather.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Cihan on 01.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MainEntity implements Parcelable {
    /**
     * temp : 292.74
     * temp_min : 292.04
     * humidity : 76
     * pressure : 1014
     * temp_max : 293.71
     */
    private String temp;
    private String temp_min;
    private String humidity;
    private String pressure;
    private String temp_max;



    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
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

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.temp);
        dest.writeString(this.temp_min);
        dest.writeString(this.humidity);
        dest.writeString(this.pressure);
        dest.writeString(this.temp_max);
    }

    public MainEntity() {
    }

    private MainEntity(Parcel in) {
        this.temp = in.readString();
        this.temp_min = in.readString();
        this.humidity = in.readString();
        this.pressure = in.readString();
        this.temp_max = in.readString();
    }

    public static final Creator<MainEntity> CREATOR = new Creator<MainEntity>() {
        public MainEntity createFromParcel(Parcel source) {
            return new MainEntity(source);
        }

        public MainEntity[] newArray(int size) {
            return new MainEntity[size];
        }
    };
}