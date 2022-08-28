package pl.rawblob.interfaces.services.azure;

import pl.rawblob.dtos.BlobListItem;
import pl.rawblob.dtos.Message;

import java.net.Socket;
import java.util.List;

/**
 * BlobService interface
 */
public interface IBlobService {

    /**
     * Get list of blobs
     * @return BlobListItem List
     * @see BlobListItem
     */
    List<BlobListItem> getBlobs();

    /**
     * Downloads blob
     * @param blobName blobname to be downloaded
     * @param socket connected socket
     * @return Message when download was ended
     */
    Message getBlobByName(String blobName, Socket socket);

    /**
     * Deletes blob
     * @param blobname blobname to be deleted
     */
    void deleteBlob(String blobname);

    /**
     * Uploads blob
     * @param blobName blobname to be uploaded
     * @param socket connected socket
     */
    void uploadBlob(String blobName, Socket socket);
}
