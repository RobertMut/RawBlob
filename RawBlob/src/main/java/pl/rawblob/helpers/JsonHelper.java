package pl.rawblob.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * JsonHelper serialize/deserialize
 */
public class JsonHelper {

    /**
     * Deserializes json
     * @param json input string
     * @return object
     * @param <T> type
     */
    public static <T> T Deserialize(String json){
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<T>(){}.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Serializes json
     * @param obj object to serialize
     * @return string
     */
    public static String Serialize(Object obj){
        Gson gson = new Gson();

        return gson.toJson(obj);
    }
}
