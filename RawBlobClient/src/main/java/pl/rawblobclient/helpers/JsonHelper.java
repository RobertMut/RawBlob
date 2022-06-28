package pl.rawblobclient.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {

    public static <T> T deserialize(String json, Class<T> type){
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(json, type);
    }
}
