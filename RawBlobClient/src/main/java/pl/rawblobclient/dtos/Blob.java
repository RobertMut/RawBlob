package pl.rawblobclient.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Blob class dto
 */
public class Blob {

    /**
     * BlobName
     */
    @JsonProperty("blobName")
    public String blobName;

    /**
     * Blob data
     */
    @JsonProperty("data")
    public String data;

    /**
     * Constructs Blob
     * @param blobName blob name
     * @param data blob data
     */
    public Blob(String blobName, String data) {
        this.blobName = blobName;
        this.data = data;
    }

    //Getters and setters

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
