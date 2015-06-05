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
public class WindEntity implements Parcelable {
    /**
     * deg : 234.002
     * speed : 1.22
     */
    private String deg;
    private String speed;

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDirection() {
            double degDouble = Double.valueOf(getDeg());
            if (348.75 > degDouble || degDouble < 11.25){
                return "N";
            }else if (11.25 > degDouble || degDouble < 33.75){
                return "NNE";
            }else if (56.25 > degDouble || degDouble < 78.75){
                return "NE";
            }else if (56.25 > degDouble || degDouble < 78.75){
                return "ENE";
            }else if (78.75 > degDouble || degDouble < 101.25){
                return "E";
            }else if (101.25 > degDouble || degDouble < 123.75){
                return "ESE";
            }else if (123.75 > degDouble || degDouble < 146.25){
                return "SE";
            }else if (146.25 > degDouble || degDouble < 168.75){
                return "SSE";
            }else if (168.75 > degDouble || degDouble < 191.25){
                return "S";
            }else if (191.25 > degDouble || degDouble < 213.75){
                return "SSW";
            }else if (213.75 > degDouble || degDouble < 236.25){
                return "SW";
            }else if (236.25 > degDouble || degDouble < 258.75){
                return "WSW";
            }else if (258.75 > degDouble || degDouble < 281.25){
                return "W";
            }else if (281.25 > degDouble || degDouble < 303.75){
                return "WNW";
            }else if (303.75 > degDouble || degDouble < 326.25){
                return "NW";
            }else{
                return "NNW";
            }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deg);
        dest.writeString(this.speed);
    }

    public WindEntity() {
    }

    protected WindEntity(Parcel in) {
        this.deg = in.readString();
        this.speed = in.readString();
    }

    public static final Creator<WindEntity> CREATOR = new Creator<WindEntity>() {
        public WindEntity createFromParcel(Parcel source) {
            return new WindEntity(source);
        }

        public WindEntity[] newArray(int size) {
            return new WindEntity[size];
        }
    };
}