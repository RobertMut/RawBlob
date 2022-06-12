package pl.rawblob.interfaces.services.azure;

import pl.rawblob.annotations.Command;
import pl.rawblob.dtos.BlobDto;
import pl.rawblob.dtos.BlobsListDto;
import pl.rawblob.entities.Blob;

public interface IBlobService {

    BlobsListDto GetBlobs();

    BlobDto GetBlobByName(String blobName);

    void DeleteBlob(String blobname);

    void UploadBlob(String blobName, String base64Data);
}
