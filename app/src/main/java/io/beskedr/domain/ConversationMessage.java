package io.beskedr.domain;

public class ConversationMessage {

    private User sender;
    private String message;
    private String time;

    public ConversationMessage(User sender, String message, String time) {
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
