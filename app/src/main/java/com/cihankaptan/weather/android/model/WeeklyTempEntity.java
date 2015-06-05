package com.cihankaptan.weather.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by cnkaptan on 6/4/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class WeeklyTempEntity implements Parcelable {


    /**
     * min : 292.76
     * eve : 293.84
     * max : 294.21
     * morn : 292.84
     * night : 293.02
     * day : 294.12
     */
    private String min;
    private String eve;
    private String max;
    private String morn;
    private String night;
    private String day;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getEve() {
        return eve;
    }

    public void setEve(String eve) {
        this.eve = eve;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMorn() {
        return morn;
    }

    public void setMorn(String morn) {
        this.morn = morn;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.min);
        dest.writeString(this.eve);
        dest.writeString(this.max);
        dest.writeString(this.morn);
        dest.writeString(this.night);
        dest.writeString(this.day);
    }

    public WeeklyTempEntity() {
    }

    protected WeeklyTempEntity(Parcel in) {
        this.min = in.readString();
        this.eve = in.readString();
        this.max = in.readString();
        this.morn = in.readString();
        this.night = in.readString();
        this.day = in.readString();
    }

    public static final Creator<WeeklyTempEntity> CREATOR = new Creator<WeeklyTempEntity>() {
        public WeeklyTempEntity createFromParcel(Parcel source) {
            return new WeeklyTempEntity(source);
        }

        public WeeklyTempEntity[] newArray(int size) {
            return new WeeklyTempEntity[size];
        }
    };
}
