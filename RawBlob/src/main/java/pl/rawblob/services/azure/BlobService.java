package pl.rawblob.services.azure;

import com.azure.core.util.BinaryData;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobListDetails;
import com.azure.storage.blob.models.ListBlobsOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.dtos.Blob;
import pl.rawblob.dtos.BlobListItem;
import pl.rawblob.helpers.JsonHelper;
import pl.rawblob.helpers.NetworkHelper;
import pl.rawblob.interfaces.services.azure.IBlobService;
import pl.rawblob.interfaces.services.azure.IKeyVaultService;
import pl.rawblob.dtos.Message;
import pl.rawblob.services.CommandService;

import java.net.Socket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * BlobService class
 */
@Component
public class BlobService implements IBlobService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandService.class);
    private IKeyVaultService keyVaultService;
    private BlobContainerClient blobContainerClient;

    /**
     * Constructs BlobService
     * @param keyVaultService keyvaultservice
     * @see KeyVaultService
     */
    @Autowired
    public BlobService(IKeyVaultService keyVaultService) {
        this.keyVaultService = keyVaultService;

        this.blobContainerClient = new BlobContainerClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .containerName("items")
                .connectionString(keyVaultService.getAzureStorage())
                .buildClient();
    }

    /**
     * Gets blobs from storage
     * @return BlobListItem List
     */
    @Override
    public List<BlobListItem> getBlobs() {
        List<BlobListItem> blobArrayList = new ArrayList<BlobListItem>();
        try {
            blobContainerClient.listBlobs(new ListBlobsOptions().setDetails(new BlobListDetails().setRetrieveMetadata(true)), Duration.ofSeconds(30))
                    .forEach(blobItem -> {
                        var blob = blobItem;
                        var blobListItem = new BlobListItem(blob.getName(), blob.getProperties().getContentLength(), blob.getProperties().getCreationTime().toString());

                        blobArrayList.add(blobListItem);
            });
        }
        catch (Exception e){
            LOGGER.error(e.toString());
        }

        return blobArrayList;
    }

    /**
     * Downloads blob in chunks
     * @implSpec Downloads and send json with data chunk (32768 data per json) then sends message with '<EOF>' to client
     * @param blobName blobname to be downloaded
     * @param socket connected socket
     * @return Message
     */
    @Override
    public Message getBlobByName(String blobName, Socket socket) {
        var blobClient = blobContainerClient.getBlobClient(blobName);
        var data = blobClient.downloadContent().toBytes();


        if(data.length > 32768) {
            for (int i = 0; i < data.length;){
                byte[] chunk = new byte[Math.min(32766, data.length - i)];

                for(int j = 0; j < chunk.length; j++, i++){
                    chunk[j] = data[i];
                }
                NetworkHelper.write(socket, JsonHelper.Serialize(new Blob(blobName, chunk)));
            }
        }

        return new Message("<EOF>");
    }

    /**
     * Deletes blob from storage
     * @param blobName blobname to be deleted
     */
    @Override
    public void deleteBlob(String blobName) {
        var blobClient = blobContainerClient.getBlobClient(blobName);

        blobClient.deleteIfExists();
    }

    /**
     * Uploads blob to storage
     * @implSpec Receiving file consist of 32768 chunks, which will be combined to one data then send to storage
     * @param blobName blobname to be uploaded
     * @param socket connected socket
     */
    @Override
    public void uploadBlob(String blobName, Socket socket) {
        StringBuilder sb = new StringBuilder();
        boolean uploaded = false;
        LOGGER.info("Receiving file..");
        NetworkHelper.write(socket, "UPLOAD");
        while(!uploaded) {
            String chunk = NetworkHelper.read(socket);
            sb.append(chunk);

            LOGGER.info("Received chunk...");

            if(chunk.endsWith("<EOF>")){
                uploaded = true;
                LOGGER.info("Upload finished.!");
            }
        }

        String base64Data = sb.substring(0, sb.length()-5);
        byte[] data = Base64.getDecoder().decode(base64Data);
        var blobClient = blobContainerClient.getBlobClient(blobName);

        blobClient.upload(BinaryData.fromBytes(data));
    }
}
