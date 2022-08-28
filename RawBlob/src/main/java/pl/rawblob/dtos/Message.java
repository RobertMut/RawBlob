package pl.rawblob.dtos;

import com.google.gson.annotations.SerializedName;

/**
 * Message dto
 */
public class Message {
    /**
     * Message string
     */
    @SerializedName("Message")
    public String Message;

    /**
     * Constructs message
     * @param message message
     */
    public Message(String message){
        this.Message = message;
    }

    //Getters and setters
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
