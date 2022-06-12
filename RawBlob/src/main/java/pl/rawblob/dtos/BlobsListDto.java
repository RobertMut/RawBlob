package pl.rawblob.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import pl.rawblob.entities.BlobListItem;

import java.util.List;

public class BlobsListDto {
    @JsonSerialize
    public final List<BlobListItem> Blobs;

    public BlobsListDto(List<BlobListItem> blobs) {
        Blobs = blobs;
    }

}
