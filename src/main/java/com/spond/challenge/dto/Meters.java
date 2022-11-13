package com.spond.challenge.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
 * modified from
 * https://github.com/MattS0000/NASA_Asteroids_App
 * */

public class Meters {
    @SerializedName("estimated_diameter_min")
    @Expose
    private Double estimatedDiameterMin;
    @SerializedName("estimated_diameter_max")
    @Expose
    private Double estimatedDiameterMax;

    public Double getEstimatedDiameterAvg(){
        return (estimatedDiameterMax + estimatedDiameterMin)/2;
    }
}