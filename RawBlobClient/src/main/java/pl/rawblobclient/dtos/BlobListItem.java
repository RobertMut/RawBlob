package pl.rawblobclient.dtos;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * BlobListItem dto
 */
public class BlobListItem implements Serializable {
    @JsonProperty("blobName")
    private String blobName;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("uploadDate")
    private String uploadDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Constructs BlobListItem
     */
    public BlobListItem() {
    }

    /**
     * Constructs BlobListItem
     * @param blobName blob name
     * @param size blob size
     * @param uploadDate blob upload date
     */
    public BlobListItem(String blobName, Integer size, String uploadDate) {
        super();
        this.blobName = blobName;
        this.size = size;
        this.uploadDate = uploadDate;
    }

    //Getters and setters

    @JsonProperty("blobName")
    public String getBlobName() {
        return blobName;
    }

    @JsonProperty("blobName")
    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty("uploadDate")
    public String getUploadDate() {
        return uploadDate;
    }

    @JsonProperty("uploadDate")
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
