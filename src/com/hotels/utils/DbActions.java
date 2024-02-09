package com.hotels.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public final class DbActions {
    private static String storeAddress = "./db-json-store";
    private static DbActions instance = null;

    private final Gson gson;

    private DbActions() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private DbActions(String address) {
        storeAddress = "./" + address;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static DbActions getInstance() {
        if (instance == null) {
            instance = new DbActions();
        }

        return instance;
    }

    public static DbActions getInstance(String address) {
        if (instance == null) {
            instance = new DbActions(address);
        }

        return instance;
    }

    public <T> void writeObjectToFile(T object, String fileName) {
        String path = DbActions.getPath(fileName);
        String serialized = gson.toJson(object);

        this.writeFile(serialized, path);
    }

    public <T> T readObjectFromFile(String fileName, Class<T> targetClass) {
        String path = DbActions.getPath(fileName);
        String serialized = this.readFile(path);

        T object = gson.fromJson(serialized, targetClass);

        return object;
    }

    private String readFile(String path) {
        StringBuilder result = new StringBuilder();

        try (FileReader stream = new FileReader(path); BufferedReader in = new BufferedReader(stream)) {
            String line = in.readLine();
            while (line != null) {
                result.append(line);
                line = in.readLine();
            }

            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFile(String data, String path) {
        try (FileWriter fw = new FileWriter(path); PrintWriter out = new PrintWriter(fw)) {
            out.print(data);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPath(String fileName) {
        return storeAddress + "/" + fileName;
    }
}
