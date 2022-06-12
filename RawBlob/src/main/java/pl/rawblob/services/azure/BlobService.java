package pl.rawblob.services.azure;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.annotations.Command;
import pl.rawblob.dtos.BlobDto;
import pl.rawblob.dtos.BlobsListDto;
import pl.rawblob.entities.Blob;
import pl.rawblob.interfaces.services.azure.IBlobService;
import pl.rawblob.interfaces.services.azure.IKeyVaultService;
import pl.rawblob.entities.BlobListItem;

import java.util.Base64;
import java.util.stream.Collectors;

@Component
public class BlobService implements IBlobService {
    private IKeyVaultService keyVaultService;
    private BlobContainerClient blobContainerClient;

    @Autowired
    public BlobService(IKeyVaultService keyVaultService) {
        this.keyVaultService = keyVaultService;
        this.blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(keyVaultService.getAzureStorage())
                .containerName("items")
                .sasToken(keyVaultService.getSasToken())
                .buildClient();
    }

    @Override
    public BlobsListDto GetBlobs() {
        var blobs = blobContainerClient.listBlobs();
        var k = keyVaultService.getAzureStorage();
        var s = keyVaultService.getSasToken();

        if (blobs.stream().count() == 0) return new BlobsListDto(null);

        var blobList = blobs.stream().map(blob ->
        {
            var properties = blob.getProperties();
            return new BlobListItem(blob.getName(), properties.getContentLength(), properties.getCreationTime().toString());
        }).collect(Collectors.toList());

        return new BlobsListDto(blobList);
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
