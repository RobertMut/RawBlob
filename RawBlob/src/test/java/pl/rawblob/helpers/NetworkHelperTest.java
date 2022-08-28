package pl.rawblob.helpers;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@RunWith(MockitoJUnitRunner.class)
public class NetworkHelperTest extends TestCase {
    @Mock
    private Socket socket;

    @Mock
    private OutputStream outputStream;

    @Mock
    private InputStream inputStream;

    @Captor
    private ArgumentCaptor<byte[]> sampleWrite;
    private String sampleResponse = "sampleResponse";

    public void setUp() throws Exception {
        super.setUp();
        this.socket = Mockito.mock(Socket.class);
        this.outputStream = Mockito.mock(OutputStream.class);

        Mockito.when(socket.getOutputStream()).thenReturn(outputStream);
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);
        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(sampleResponse.getBytes(StandardCharsets.UTF_8)));

    }

    @Test
    public void testRead() {
        String response = NetworkHelper.read(socket);

        Assert.assertTrue(response.equals(this.sampleResponse));
    }

    @Test
    public void testWrite() throws IOException {
        NetworkHelper.write(socket, "sampleWrite");
        Mockito.verify(outputStream).write(sampleWrite.capture());

        Assert.assertEquals(sampleWrite.getValue(), "sampleWrite");
    }
}