package pl.rawblobclient.interfaces.services;

import pl.rawblobclient.dtos.BlobsListDto;

import java.io.InputStream;

public interface ICommandService {
    BlobsListDto ListBlobs();

    void DownloadBlob(String blobName);

    void Delete(String blobName);

    void Upload(String filename, InputStream stream);
}
