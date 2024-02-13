package com.hotels.room;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RoomDeserializer implements JsonDeserializer<Room>, JsonSerializer<Room> {
    private static String className = "classNameForSerialization";
    @Override
    public Room deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject item = json.getAsJsonObject();
        String className = item.get(RoomDeserializer.className).getAsString();

        try {
            Class<?> clz = Class.forName(className);
            return context.deserialize(json, clz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonElement serialize(Room src, Type typeOfSrc, JsonSerializationContext context) {


        Gson gson = new Gson(); //without this line it will not work
        gson.toJson(src, src.getClass()); //and this one
        JsonElement jsonElement = gson.toJsonTree(src); //it needs to replace to another method...toJsonTree
        jsonElement.getAsJsonObject().addProperty(className, src.getClass().getCanonicalName());
        return jsonElement;
    }
}
