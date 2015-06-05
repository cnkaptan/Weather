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
public class CoordEntity implements Parcelable {
    /**
     * lon : 139
     * lat : 35
     */
    private String lon;
    private String lat;


    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lon);
        dest.writeString(this.lat);
    }

    public CoordEntity() {
    }

    private CoordEntity(Parcel in) {
        this.lon = in.readString();
        this.lat = in.readString();
    }

    public static final Creator<CoordEntity> CREATOR = new Creator<CoordEntity>() {
        public CoordEntity createFromParcel(Parcel source) {
            return new CoordEntity(source);
        }

        public CoordEntity[] newArray(int size) {
            return new CoordEntity[size];
        }
    };
}