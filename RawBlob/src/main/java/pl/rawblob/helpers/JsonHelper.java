package pl.rawblob.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.rawblob.models.Message;

public class JsonHelper {

    public static <T> T Deserialize(String json, Class<T> type){
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(json, type);
    }

    public static String Serialize(Object obj){
        Gson gson = new Gson();

        return gson.toJson(obj);
    }
}
