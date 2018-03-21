package io.beskedr.domain;

public class ConversationMessage {

    private User sender;
    private String user;
    private String message;
    private long time;

    public ConversationMessage() {}

    public ConversationMessage(User sender, String message, long time) {
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public ConversationMessage(User sender, Conversation convo) {
        this(sender, convo.getLastMessage(), convo.getTime());
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getUser() {
        return user;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public String getTimeFormatted() {
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d", hour, minute);
    }
}
