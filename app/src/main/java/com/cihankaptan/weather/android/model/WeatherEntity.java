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
public class WeatherEntity implements Parcelable {
    /**
     * icon : 02n
     * description : few clouds
     * main : Clouds
     * id : 801
     */
    private String icon;
    private String description;
    private String main;
    private String id;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getMain() {
        return main;
    }

    public String getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.description);
        dest.writeString(this.main);
        dest.writeString(this.id);
    }

    public WeatherEntity() {
    }

    protected WeatherEntity(Parcel in) {
        this.icon = in.readString();
        this.description = in.readString();
        this.main = in.readString();
        this.id = in.readString();
    }

    public static final Creator<WeatherEntity> CREATOR = new Creator<WeatherEntity>() {
        public WeatherEntity createFromParcel(Parcel source) {
            return new WeatherEntity(source);
        }

        public WeatherEntity[] newArray(int size) {
            return new WeatherEntity[size];
        }
    };
}