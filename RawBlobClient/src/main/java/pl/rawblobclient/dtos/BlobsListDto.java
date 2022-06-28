package pl.rawblobclient.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.rawblobclient.model.BlobListItem;

import java.util.List;

public class BlobsListDto {
    @JsonSerialize
    public final List<BlobListItem> Blobs;

    public BlobsListDto(List<BlobListItem> blobs) {
        Blobs = blobs;
    }

}
