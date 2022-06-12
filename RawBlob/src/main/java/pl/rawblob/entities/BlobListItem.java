package pl.rawblob.entities;

public class BlobListItem {
    public final String BlobName;
    public final int Size;
    public final String UploadDate;

    public BlobListItem(String blobName, long size, String uploadDate) {
        BlobName = blobName;
        Size = (int)(size/Math.pow(1024, 2));;
        UploadDate = uploadDate;
    }
}
