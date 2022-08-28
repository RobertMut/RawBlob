package pl.rawblob.dtos;

import java.util.Base64;

/**
 * Blob dto
 */
public class Blob {
    /**
     * Blob Name
     */
    public final String blobName;

    /**
     * File data
     */
    public final String data;

    /**
     * Constructs blob
     * @param blobName filename
     * @param data byte array
     */
    public Blob(String blobName, byte[] data) {
        this.blobName = blobName;
        this.data = Base64.getEncoder().encodeToString(data);
    }
}
