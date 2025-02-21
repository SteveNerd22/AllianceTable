package io.alliancetable.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventDispatcher {

    private final HashMap<Class<? extends Event>, List<EventListener<? extends Event>>> listenersMap = new HashMap<>();

    public EventDispatcher() {
    }

    public <T extends Event> void addListener(Class<T> eventType, EventListener<T> listener) {
        listenersMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public <T extends Event> void removeEventListener(Class<T> eventType, EventListener<T> listener) {
        List<EventListener<? extends Event>> listeners = listenersMap.get(eventType);

        if (listeners != null) {
            listeners.remove(listener);

            if (listeners.isEmpty()) {
                listenersMap.remove(eventType);
            }
        }
    }

    public void dispatch(Event event) {
        List<EventListener<? extends Event>> listeners = listenersMap.get(event.getClass());

        if (listeners != null) {
            for (EventListener<? extends Event> listener : listeners) {
                // Il cast è sicuro poiché sappiamo che il listener è parametrizzato per questo tipo di evento
                @SuppressWarnings("unchecked")
                EventListener<Event> eventListener = (EventListener<Event>) listener;
                eventListener.onEvent(event);
            }
        }
    }

}
