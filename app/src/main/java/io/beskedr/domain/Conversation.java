package io.beskedr.domain;

public class Conversation {

    private String id;
    private String lastMessage;
    private long time;

    public Conversation() {}

    public Conversation(String id, String lastMessage, long time) {
        this.id = id;
        this.lastMessage = lastMessage;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public long getTime() {
        return time;
    }
}
