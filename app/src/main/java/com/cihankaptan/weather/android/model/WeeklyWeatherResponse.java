package com.cihankaptan.weather.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by cnkaptan on 6/4/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class WeeklyWeatherResponse implements Parcelable {

    private String message;
    private String cod;
    private CityEntity city;
    private List<WeeklyItemEntity> list;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public List<WeeklyItemEntity> getList() {
        return list;
    }

    public void setList(List<WeeklyItemEntity> list) {
        this.list = list;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.cod);
        dest.writeParcelable(this.city, 0);
        dest.writeTypedList(list);
    }

    public WeeklyWeatherResponse() {
    }

    protected WeeklyWeatherResponse(Parcel in) {
        this.message = in.readString();
        this.cod = in.readString();
        this.city = in.readParcelable(CityEntity.class.getClassLoader());
        this.list = in.createTypedArrayList(WeeklyItemEntity.CREATOR);
    }

    public static final Creator<WeeklyWeatherResponse> CREATOR = new Creator<WeeklyWeatherResponse>() {
        public WeeklyWeatherResponse createFromParcel(Parcel source) {
            return new WeeklyWeatherResponse(source);
        }

        public WeeklyWeatherResponse[] newArray(int size) {
            return new WeeklyWeatherResponse[size];
        }
    };
}
