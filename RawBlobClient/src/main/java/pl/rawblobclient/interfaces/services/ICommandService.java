package pl.rawblobclient.interfaces.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * CommandService interface
 */
public interface ICommandService {

    /**
     * Receives blob items
     * @return LinkedHashMap List
     */
    List<LinkedHashMap<String, String>> listBlobs();

    /**
     * Downloads blob to current directory
     * @param blobName blob to be downloaded
     */
    void downloadBlob(String blobName);

    /**
     * Deletes blob
     * @param blobName
     * @throws IOException
     */
    void delete(String blobName) throws IOException;

    /**
     * Uploads blob
     * @param filename blob name
     * @param stream file stream
     */
    void upload(String filename, InputStream stream);
}
