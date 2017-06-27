package de.github.GSGJ.API.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by claudio on 19.06.17.
 */
public class EventDispatcher {

    private HashMap<Class<? extends Event>, ArrayList<EventListener>> listeners;

    public EventDispatcher() {
        this.listeners = new HashMap<>();
    }

    public void listen(EventListener listener, Class<? extends Event> eventClass) {
        ArrayList<EventListener> currentListeners = listeners.get(eventClass);
        currentListeners.add(listener);
    }

    public void unlisten(EventListener listener, Class<? extends Event> eventClass) {
        ArrayList<EventListener> currentListeners = listeners.get(eventClass);
        currentListeners.remove(listener);
    }

    public void dispatch(Event event) {
        Class<Event> eventClass = event.getEventClass();
        if (listeners.containsKey(eventClass)) {
            ArrayList<EventListener> eventListeners = listeners.get(eventClass);
            if (!eventListeners.isEmpty()) {
                for (EventListener evtListener : eventListeners) {
                    evtListener.onEventDispatch(event);
                }
            }
        }

    }
}
