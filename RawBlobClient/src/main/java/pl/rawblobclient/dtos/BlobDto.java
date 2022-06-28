package pl.rawblobclient.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.rawblobclient.model.Blob;

public class BlobDto {

    @JsonSerialize
    public final pl.rawblobclient.model.Blob Blob;

    public BlobDto(Blob blob) {
        Blob = blob;
    }

}
