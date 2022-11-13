package com.spond.challenge.dto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
 * modified from
 * https://github.com/MattS0000/NASA_Asteroids_App
 * */

public class OrbitClass {
    @SerializedName("orbit_class_type")
    @Expose
    private String orbitClassType;
    @SerializedName("orbit_class_description")
    @Expose
    private String orbitClassDescription;
    @SerializedName("orbit_class_range")
    @Expose
    private String orbitClassRange;
}
