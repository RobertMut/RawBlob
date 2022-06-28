package pl.rawblobclient.model;

import java.util.Base64;

public class Blob {
    public final String BlobName;
    public final String Data;

    public Blob(String blobName, String data) {
        this.BlobName = blobName;
        this.Data = data;
    }
}
