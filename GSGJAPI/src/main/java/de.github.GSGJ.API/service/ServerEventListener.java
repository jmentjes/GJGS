package de.github.GSGJ.API.service;


import de.github.GSGJ.API.structure.ServerEvent;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface ServerEventListener<T extends ServerEvent> {
    void handleEvent(T event);
}
