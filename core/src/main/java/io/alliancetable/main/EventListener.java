package io.alliancetable.main;

public interface EventListener<T extends Event> {
    void onEvent(T event);

    default void printMessage(T event) {
        System.out.println(event);
    }
}
