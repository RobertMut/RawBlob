package pl.rawblob.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import pl.rawblob.entities.Blob;

public class BlobDto {

    @JsonSerialize
    public final Blob Blob;

    public BlobDto(pl.rawblob.entities.Blob blob) {
        Blob = blob;
    }

}
