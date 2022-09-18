package pl.rawblob.services.azure;

import com.azure.core.util.BinaryData;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.rawblob.TearDown;
import pl.rawblob.helpers.NetworkHelper;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static net.javacrumbs.mocksocket.MockSocket.data;
import static net.javacrumbs.mocksocket.MockSocket.expectCall;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class BlobServiceTest extends TestCase {
    @Mock
    private KeyVaultService keyVaultService;

    @InjectMocks
    private BlobService blobService;

    private BlobServiceClient blobServiceClient;
    private BlobContainerClient blobContainerClient;

    public BlobServiceTest(){
        keyVaultService = Mockito.mock(KeyVaultService.class);
        var credentials = new DefaultAzureCredentialBuilder().build();
        var binaryData = BinaryData.fromBytes("sampletext".getBytes(StandardCharsets.UTF_8));
        Mockito.when(keyVaultService.getAzureStorage()).thenReturn(TearDown.ConnectionString);
        blobServiceClient = new BlobServiceClientBuilder()
                .credential(credentials)
                .connectionString(TearDown.ConnectionString)
                .buildClient();
        blobContainerClient = blobServiceClient.createBlobContainerIfNotExists("items");

        var sampleFileTxt = blobContainerClient.getBlobClient("sample.txt");
        sampleFileTxt.deleteIfExists();
        sampleFileTxt.upload(binaryData);
        var sampleFile2Txt = blobContainerClient.getBlobClient("sample2.txt");
        sampleFile2Txt.deleteIfExists();
        sampleFile2Txt.upload(binaryData);

        this.blobService = new BlobService(keyVaultService);
    }

    @Test
    public void testGetBlobs() {
        var blobs = this.blobService.getBlobs();
        var blob = blobs.get(0);

        Assert.assertTrue(blob.getBlobName().equals("sample.txt"));
    }

    @Test
    public void testGetBlobByName() throws IOException {
        try (MockedStatic<NetworkHelper> mockedNetworkHelper = Mockito.mockStatic(NetworkHelper.class)){
            var socket = new Socket();
            socket.bind(new InetSocketAddress(16556));
            var blob = this.blobService.getBlobByName("sample.txt", socket);

            Assert.assertTrue(blob.getMessage().equals("<EOF>"));
            mockedNetworkHelper.verify(() -> NetworkHelper.write(Mockito.any(Socket.class), Mockito.anyString()), times(1));
        }
    }

    @Test
    public void testDeleteBlob() {
        this.blobService.deleteBlob("sample2.txt");

        var blobs = this.blobService.getBlobs();
        Assert.assertFalse(blobs.stream().anyMatch(x -> x.getBlobName().equals("sample2.txt")));
    }

    @Test
    public void testUploadBlob() throws IOException {
        try (MockedStatic<NetworkHelper> mockedNetworkHelper = Mockito.mockStatic(NetworkHelper.class)) {
            mockedNetworkHelper.when(() -> NetworkHelper.read(Mockito.any(Socket.class))).thenReturn("sampletext<EOF>");
            expectCall().andReturn(data("sample<EOF>\r\n".getBytes(StandardCharsets.UTF_8)));
            Socket socket = SocketFactory.getDefault().createSocket("127.0.0.1", 16556);

            this.blobService.uploadBlob("newblob.txt", socket);

            mockedNetworkHelper.verify(() -> NetworkHelper.write(Mockito.any(Socket.class), Mockito.anyString()), times(1));
            mockedNetworkHelper.verify(() -> NetworkHelper.read(Mockito.any(Socket.class)), times(1));
        }
    }

    @After
    @Override
    public void tearDown() throws Exception {
        var blob = blobContainerClient.getBlobClient("newblob.txt");
        blob.deleteIfExists();

        blobContainerClient.deleteIfExists();
        super.tearDown();
    }
}