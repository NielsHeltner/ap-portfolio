package io.beskedr.domain;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Locale;

public class ConversationMessage implements Comparable<ConversationMessage> {

    @Exclude
    private User sender;
    private String user = "";
    private String message = "";
    private long time = 0;

    public ConversationMessage() {}

    public ConversationMessage(User sender, String message, long time) {
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public ConversationMessage(String user, String message, long time) {
        this.user = user;
        this.message = message;
        this.time = time;
    }

    public ConversationMessage setSender(User sender) {
        this.sender = sender;
        this.user = sender.getUsername();
        return this;
    }

    public String getUser() {
        return user;
    }

    @Exclude
    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    @Exclude
    public String getTimeFormatted() {
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;

        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }

    @Override
    public int compareTo(@NonNull ConversationMessage o) {
        return (int) (time - o.time);
    }
}
