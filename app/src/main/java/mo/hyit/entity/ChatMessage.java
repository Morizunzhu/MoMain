package mo.hyit.entity;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String senderId;
    private String message;
    private String toerId;
    private int type;

    public ChatMessage() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToerId() {
        return toerId;
    }

    public void setToerId(String toerId) {
        this.toerId = toerId;
    }
}
