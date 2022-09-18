package pl.rawblob.services;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.rawblob.dtos.BlobListItem;
import pl.rawblob.dtos.Message;
import pl.rawblob.interfaces.services.azure.IBlobService;
import pl.rawblob.model.Client;
import pl.rawblob.services.azure.BlobService;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CommandServiceTest extends TestCase {

    @Mock
    private IBlobService blobService;

    @InjectMocks
    private CommandService commandService;

    private Client client;

    public CommandServiceTest() {
        this.client = new Client();

        this.client.setSocket(new Socket());
        this.client.setTerminated(false);
    }

    public void setUp() throws Exception {
        super.setUp();

        blobService = Mockito.spy(BlobService.class);

        commandService = new CommandService();
    }

    @Test
    public void testParserRunsList() {
        var blobs = new ArrayList<BlobListItem>();
        var blob = new BlobListItem("test.png", 1000, "2019-01-01");
        blobs.add(blob);

        Mockito.when(blobService.getBlobs()).thenReturn(blobs);

        var blobsResponse = (List<BlobListItem>)this.commandService.parser("List", client);

        Assert.assertTrue(blobsResponse.get(0).getBlobName() == "test.png");
    }

    @Test
    public void testParserRunsDelete() {
        Mockito.doNothing().when(blobService).deleteBlob(Mockito.anyString());

        var message = (Message)this.commandService.parser("Delete test.png", client);

        Assert.assertTrue(message.getMessage() == "Deleted");
        Mockito.verify(blobService, Mockito.atLeast(1)).deleteBlob("test.png");
    }

    @Test
    public void testParserRunsUpload(){
        Mockito.doNothing().when(blobService).uploadBlob(Mockito.anyString(), Mockito.any(Socket.class));

        var message = (Message)this.commandService.parser("Upload test.png", client);
        Assert.assertTrue(message.getMessage() == "Created");

        Mockito.verify(blobService, Mockito.atLeast(1)).uploadBlob("test.png", client.getSocket());
    }

    @Test
    public void testParserGetBlobByName() {
        Mockito.when(blobService.getBlobByName(Mockito.anyString(), Mockito.any(Socket.class))).thenReturn(new Message("testresponse"));

        var message = (Message)this.commandService.parser("Download test.png", client);

        Assert.assertTrue(message.getMessage() == "testresponse");

        Mockito.verify(blobService, Mockito.times(1)).getBlobByName("test.png", client.getSocket());
    }

    @Test
    public void testParserCommandNotFound(){
        var message = (Message)this.commandService.parser("WRONGCOMMAND", client);

        Assert.assertTrue(message.getMessage() == "Command not found!");
    }
}