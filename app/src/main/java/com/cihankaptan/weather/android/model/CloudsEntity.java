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
public class CloudsEntity implements Parcelable {
    /**
     * all : 24
     */
    private String all;

    public void setAll(String all) {
        this.all = all;
    }

    public String getAll() {
        return all;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.all);
    }

    public CloudsEntity() {
    }

    private CloudsEntity(Parcel in) {
        this.all = in.readString();
    }

    public static final Creator<CloudsEntity> CREATOR = new Creator<CloudsEntity>() {
        public CloudsEntity createFromParcel(Parcel source) {
            return new CloudsEntity(source);
        }

        public CloudsEntity[] newArray(int size) {
            return new CloudsEntity[size];
        }
    };
}