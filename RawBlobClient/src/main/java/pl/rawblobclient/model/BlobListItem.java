package pl.rawblobclient.model;

public class BlobListItem {
    public final String BlobName;
    public final int Size;
    public final String UploadDate;

    public BlobListItem(String blobName, int size, String uploadDate) {
        BlobName = blobName;
        Size = size;
        UploadDate = uploadDate;
    }
}
