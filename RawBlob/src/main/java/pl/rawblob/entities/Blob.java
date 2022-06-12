package pl.rawblob.entities;

import java.util.Base64;

public class Blob {
    public final String BlobName;
    public final String Data;

    public Blob(String blobName, byte[] data) {
        this.BlobName = blobName;
        this.Data = Base64.getEncoder().encodeToString(data);
    }
}
