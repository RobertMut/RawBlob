package pl.rawblob.helpers;

import com.google.gson.internal.LinkedTreeMap;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.util.Assert;
import pl.rawblob.dtos.Message;

public class JsonHelperTest extends TestCase {
    private String sampleJson;

    public void setUp() throws Exception {
        super.setUp();
        sampleJson = "{\"Message\":\"sample message\"}";
    }

    @Test
    public void testDeserialize() {
        LinkedTreeMap message = JsonHelper.Deserialize(sampleJson);

        Assert.hasText("sample message", message.get("Message").toString());
    }

    @Test
    public void testSerialize() {
        Message message = new Message("sample message");
        String serialized = JsonHelper.Serialize(message);

        Assert.isInstanceOf(Message.class, message);
        Assert.isTrue(serialized.equals(sampleJson));
    }
}