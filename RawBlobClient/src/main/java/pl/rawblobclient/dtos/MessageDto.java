package pl.rawblobclient.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.rawblobclient.model.Message;

public class MessageDto {
    @JsonSerialize
    public final Message message;

    public MessageDto(String message){
        this.message = new Message(message);
    }

}
