package io.beskedr.gui;

public class Conversation {

    private String name;
    private String message;
    private String time;

    public Conversation(String name, String message, String time) {
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
