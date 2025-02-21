package io.alliancetable.main;

public abstract class Event {

    String message;

    public Event(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
