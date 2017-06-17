package de.github.GSGJ.com;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface ServerEventListener<T extends ServerEvent> {
    void handleEvent(T event);
}
