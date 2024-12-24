package com.lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.net.URL;
import java.lang.reflect.Type;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonProcessor {
    private Gson gson = new GsonBuilder().create();

    public String get(String urlString) throws extendedException {
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new extendedException("HTTP error code: " + responseCode);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } 
        catch (UnknownHostException exc) {
            throw new extendedException("Unknown host: " + exc.getMessage(), exc);
        }
        catch (IOException exc) {
            throw new extendedException("Network error: " + exc.getMessage(), exc);
        } 
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public List<User> getUsers(String url) throws extendedException {
        String jsonResponse = get(url);
        Type listType = new TypeToken<List<User>>() {}.getType();
        try {
            return gson.fromJson(jsonResponse, listType);
        }
        catch (JsonSyntaxException exc) {
            throw new extendedException("JSON users parsing error: " + exc.getMessage(), exc);
        }
    }

    public List<Company> getCompanies(String url) throws extendedException {
        String jsonResponse = get(url);
        Type listType = new TypeToken<List<Company>>() {}.getType();
        try {
            return gson.fromJson(jsonResponse, listType);
        }
        catch (JsonSyntaxException exc) {
            throw new extendedException("JSON companies parsing error: " + exc.getMessage(), exc);
        }
    }
}
