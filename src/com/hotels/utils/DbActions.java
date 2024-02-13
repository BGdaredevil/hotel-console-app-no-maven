package com.hotels.utils;

import com.google.gson.*;
import com.hotels.room.Room;
import com.hotels.room.RoomDeserializer;

import java.io.*;
import java.util.*;

public final class DbActions {
    private static String storeAddress = "./db-json-store";
    private static DbActions instance = null;

    private final Gson gson;

    private DbActions(String address) {
        storeAddress = "./" + address;
        gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Room.class, new RoomDeserializer()).create();
    }

    public static DbActions getInstance() {
        return DbActions.getInstance("db-json-store");
    }

    public static DbActions getInstance(String address) {
        if (instance == null) {
            instance = new DbActions(address);
        }

        return instance;
    }

    public <T> void writeObjectToFile(T object, String fileName) throws IOException {
        String path = DbActions.getPath(fileName);
        String serialized = gson.toJson(object);

        this.writeFile(serialized, path);
    }

    public <T> ArrayDeque<T> readObjectArrFromFile(String fileName, Class<T> targetClass) throws IOException {
        String path = DbActions.getPath(fileName);
        String serialized = this.readFile(path);
        JsonArray items = JsonParser.parseString(serialized).getAsJsonArray();
        ArrayDeque<T> result = new ArrayDeque<>(items.size());

        items.forEach(item -> result.add(gson.fromJson(item, targetClass)));

        return result;
    }

    public <T> Map<String, T> readObjectMapFromFile(String fileName, Class<T> targetClass) throws IOException {
        String path = DbActions.getPath(fileName);
        String serialized = this.readFile(path);

        JsonObject items = JsonParser.parseString(serialized).getAsJsonObject();

        Map<String, T> restored = new HashMap<>(items.size());

        items.entrySet().forEach((entry) ->
                restored.put(entry.getKey(), gson.fromJson(entry.getValue(), targetClass))
        );

        return restored;
    }

    public <T> T readObjectFromFile(String fileName, Class<T> targetClass) throws IOException {
        String path = DbActions.getPath(fileName);
        String serialized = this.readFile(path);

        T object = gson.fromJson(serialized, targetClass);

        return object;

    }

    private String readFile(String path) throws IOException {
        StringBuilder result = new StringBuilder();

        try (FileReader stream = new FileReader(path); BufferedReader in = new BufferedReader(stream)) {
            String line = in.readLine();
            while (line != null) {
                result.append(line);
                line = in.readLine();
            }

            return result.toString();
        }
    }

    private void writeFile(String data, String path) throws IOException {
        try (FileWriter fw = new FileWriter(path); PrintWriter out = new PrintWriter(fw)) {
            out.print(data);
        }
    }

    private static String getPath(String fileName) {
        return storeAddress + "/" + fileName;
    }
}

