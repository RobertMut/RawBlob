package pl.rawblob.helpers;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static net.javacrumbs.mocksocket.MockSocket.*;
import static org.hamcrest.CoreMatchers.is;

public class NetworkHelperTest extends TestCase {
    private static String sampleResponse = "sampleResponse\r\n";

    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testRead() throws IOException {
        reset();
        expectCall().andReturn(data(sampleResponse.getBytes(StandardCharsets.UTF_8)));
        Socket socket = SocketFactory.getDefault().createSocket("127.0.0.1", 14998);

        String response = NetworkHelper.read(socket);

        Assert.assertTrue(response.equals(this.sampleResponse.substring(0, sampleResponse.length()-2)));
    }

    @Test
    public void testWrite() throws IOException {
        reset();
        expectCall().andWhenRequest(data(is("sampleWrite".getBytes(StandardCharsets.UTF_8))));
        Socket socket = SocketFactory.getDefault().createSocket("127.0.0.1", 14999);

        NetworkHelper.write(socket, "sampleWrite");
    }
}