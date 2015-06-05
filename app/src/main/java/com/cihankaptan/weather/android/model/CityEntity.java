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
public class CityEntity implements Parcelable {
    /**
     * coord : {"lon":138.933334,"lat":34.966671}
     * id : 1851632
     * name : Shuzenji
     * population : 0
     * country : JP
     */
    private CoordEntity coord;
    private String id;
    private String name;
    private String population;
    private String country;

    public void setCoord(CoordEntity coord) {
        this.coord = coord;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CoordEntity getCoord() {
        return coord;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPopulation() {
        return population;
    }

    public String getCountry() {
        return country;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.coord, 0);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.population);
        dest.writeString(this.country);
    }

    public CityEntity() {
    }

    protected CityEntity(Parcel in) {
        this.coord = in.readParcelable(CoordEntity.class.getClassLoader());
        this.id = in.readString();
        this.name = in.readString();
        this.population = in.readString();
        this.country = in.readString();
    }

    public static final Creator<CityEntity> CREATOR = new Creator<CityEntity>() {
        public CityEntity createFromParcel(Parcel source) {
            return new CityEntity(source);
        }

        public CityEntity[] newArray(int size) {
            return new CityEntity[size];
        }
    };
}