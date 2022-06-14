package pl.rawblob.services.azure;

import com.azure.core.util.BinaryData;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobListDetails;
import com.azure.storage.blob.models.ListBlobsOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.annotations.Command;
import pl.rawblob.dtos.BlobDto;
import pl.rawblob.dtos.BlobsListDto;
import pl.rawblob.entities.Blob;
import pl.rawblob.interfaces.services.azure.IBlobService;
import pl.rawblob.interfaces.services.azure.IKeyVaultService;
import pl.rawblob.entities.BlobListItem;
import pl.rawblob.services.CommandService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlobService implements IBlobService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandService.class);
    private IKeyVaultService keyVaultService;
    private BlobContainerClient blobContainerClient;

    @Autowired
    public BlobService(IKeyVaultService keyVaultService) {
        this.keyVaultService = keyVaultService;

        this.blobContainerClient = new BlobContainerClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .containerName("items")
                .connectionString(keyVaultService.getAzureStorage())
                .buildClient();
    }

    @Override
    public BlobsListDto GetBlobs() {
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

        return new BlobsListDto(blobArrayList);
    }

    @Override
    public BlobDto GetBlobByName(String blobName) {
        var blobClient = blobContainerClient.getBlobClient(blobName);
        var data = blobClient.downloadContent();

        return new BlobDto(new Blob(blobName, data.toBytes()));
    }

    @Override
    public void DeleteBlob(String blobName) {
        var blobClient = blobContainerClient.getBlobClient(blobName);

        blobClient.deleteIfExists();
    }

    @Override
    public void UploadBlob(String blobName, String base64Data) {
        byte[] data = Base64.getDecoder().decode(base64Data);
        var blobClient = blobContainerClient.getBlobClient(blobName);

        blobClient.upload(BinaryData.fromBytes(data));
    }
}
