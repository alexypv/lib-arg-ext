/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.opencode.library.web.utils;

/**
 * @author popov.a
 */


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class JsonConverter {

    private final Gson gson;

    public JsonConverter() {
        gson = new GsonBuilder().create();
    }

    public <T> String convertEntityToJson(T entity, String name) {
        JsonObject jsonObject = gson.toJsonTree(entity).getAsJsonObject();
        JsonObject result = new JsonObject();
        result.add(name, jsonObject);
        return result.toString();
    }

    public <T> String convertListEntitiesToJson(List<T> entities, String name) {
        JsonArray jarray = gson.toJsonTree(entities).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(name, jarray);
        return jsonObject.toString();
    }

}

