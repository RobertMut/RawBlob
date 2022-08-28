package pl.rawblobclient.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JsonHelper class
 */
public class JsonHelper {

    /**
     * Deserializes json to object
     * @param json input json
     * @return Object
     * @param <T> Object type to be deserialized to
     * @throws JsonProcessingException
     */
    public static <T> T deserialize(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, new TypeReference<T>() {});
    }
}
