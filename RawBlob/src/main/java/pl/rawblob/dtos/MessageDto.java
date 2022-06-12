package pl.rawblob.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;

import pl.rawblob.models.Message;

public class MessageDto  {
    @JsonSerialize
    public final Message message;

    public MessageDto(String message){
        this.message = new Message(message);
    }

}
