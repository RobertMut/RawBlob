package pl.rawblob.dtos;

import com.google.gson.annotations.Expose;

/**
 * Blob list item dto
 */
public class BlobListItem {
    /**
     * Blob name
     */
    @Expose
    public String blobName;

    /**
     * Size
     */
    @Expose
    public int size;

    /**
     * Upload date
     */
    @Expose
    public String uploadDate;

    /**
     * Constructs BlobListItem
     * @param blobName filename
     * @param size file size
     * @param uploadDate self-explanatory
     */
    public BlobListItem(String blobName, long size, String uploadDate) {
        this.blobName = blobName;
        this.size = (int)size/1024;
        this.uploadDate = uploadDate;
    }

    //Getters and setters
    public BlobListItem(){}

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

}
