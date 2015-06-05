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
public class SysEntity implements Parcelable {
    /**
     * country : JP
     * sunrise : 1433100671
     * sunset : 1433152345
     * id : 10294
     * type : 3
     * message : 0.0037
     */
    private String country;
    private String sunrise;
    private String sunset;
    private String id;
    private String type;
    private String message;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.sunrise);
        dest.writeString(this.sunset);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.message);
    }

    public SysEntity() {
    }

    private SysEntity(Parcel in) {
        this.country = in.readString();
        this.sunrise = in.readString();
        this.sunset = in.readString();
        this.id = in.readString();
        this.type = in.readString();
        this.message = in.readString();
    }

    public static final Creator<SysEntity> CREATOR = new Creator<SysEntity>() {
        public SysEntity createFromParcel(Parcel source) {
            return new SysEntity(source);
        }

        public SysEntity[] newArray(int size) {
            return new SysEntity[size];
        }
    };
}