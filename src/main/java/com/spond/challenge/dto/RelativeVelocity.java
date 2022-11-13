package com.spond.challenge.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
 * modified from
 * https://github.com/MattS0000/NASA_Asteroids_App
 * */

public class RelativeVelocity {
    @SerializedName("kilometers_per_second")
    @Expose
    private String kilometersPerSecond;
    @SerializedName("kilometers_per_hour")
    @Expose
    private String kilometersPerHour;
    @SerializedName("miles_per_hour")
    @Expose
    private String milesPerHour;

    public String getKilometersPerSecond() {
        return kilometersPerSecond;
    }
}