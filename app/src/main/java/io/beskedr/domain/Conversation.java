package io.beskedr.domain;

import com.google.firebase.database.Exclude;

public class Conversation {

    @Exclude
    private User other;
    private String otherUser;
    private String lastMessageOwner;
    private String message;
    private long time;

    public Conversation() {
    }

    public Conversation(User other, ConversationMessage convo) {
        this.other = other;
        this.otherUser = other.getUsername();
        this.lastMessageOwner = convo.getUser();
        this.message = convo.getMessage();
        this.time = convo.getTime();
    }

    public String getOtherUser() {
        return otherUser;
    }

    @Exclude
    public User getOther() {
        return other;
    }

    public void setOther(User other) {
        this.other = other;
    }

    public String getLastMessageOwner() {
        return lastMessageOwner;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public void update(Conversation c) {
        this.message = c.message;
        this.lastMessageOwner = c.lastMessageOwner;
        this.time = c.time;
    }

    @Exclude
    public String getTimeFormatted() {
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d", hour, minute);
    }

}
