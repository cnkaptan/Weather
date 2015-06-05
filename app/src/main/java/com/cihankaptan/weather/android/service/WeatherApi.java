package com.cihankaptan.weather.android.service;


import com.cihankaptan.weather.android.model.CurrentWeatherResponse;
import com.cihankaptan.weather.android.model.WeeklyWeatherResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by Cihan on 01.06.2015.
 */
public interface WeatherApi {
    @Headers("x-api-key:ef99499b13235606e8b1c4fa79bd9b43")
    @GET("/weather")
    void getCurrentWeather(@Query("lat") String lat, @Query("lon") String lon,
                           Callback<CurrentWeatherResponse> currentlyResponse);

    @Headers("x-api-key:ef99499b13235606e8b1c4fa79bd9b43")
    @GET("/forecast/daily")
    void getWeaklyWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("cnt") String count,
                          Callback<WeeklyWeatherResponse> weeklyWeatherResponse);


}
