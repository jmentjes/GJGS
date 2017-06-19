package de.github.GSGJ.API.service;

/**
 * Created by claudio on 19.06.17.
 */
public interface EventListener<E> {
    public void onEventDispatch(E event);
}
