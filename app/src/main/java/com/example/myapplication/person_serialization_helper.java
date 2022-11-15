package com.example.myapplication;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

//https://stackoverflow.com/questions/33187942/custom-gson-serialization-for-an-abstract-class
public class person_serialization_helper implements JsonDeserializer<Player> {
    @Override
    public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String type = json.getAsJsonObject().get("type").getAsString();
        switch (type) {
            case "Human":
                return context.deserialize(json, Human.class);
            case "Computer":
                return context.deserialize(json, Computer.class);
            default:
                throw new IllegalArgumentException("Illegal Player");
        }
    }
}
