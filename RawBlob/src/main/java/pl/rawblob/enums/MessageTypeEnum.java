package pl.rawblob.enums;

import pl.rawblob.models.Message;

public enum MessageTypeEnum {
    Message(Message.class);

    public final Class type;

    private MessageTypeEnum(Class type){
        this.type = type;
    }
}
