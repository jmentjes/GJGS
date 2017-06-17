package de.github.GSGJ.com;


/**
 * Created by Kojy on 17.06.2017.
 */
public interface Server {
    void addServerEventListener(ServerEventListener eventListener);

    void removeServerEventListener(ServerEventListener eventListener);
}
