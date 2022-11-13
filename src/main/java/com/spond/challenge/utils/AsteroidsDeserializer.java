package com.spond.challenge.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.spond.challenge.dto.AsteroidByDates;

public class AsteroidsDeserializer implements JsonDeserializer<AsteroidByDates> {
    public final static String TAG = "AsteroidsByDatesDeserializer";

    public AsteroidByDates deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){
        Gson gson = new Gson();
        AsteroidByDates asteroidByDates = gson.fromJson(json, AsteroidByDates.class);
        asteroidByDates.setupAsteroidsMap();
        return asteroidByDates;
    }
}