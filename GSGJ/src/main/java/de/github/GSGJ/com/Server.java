package de.github.GSGJ.com;

import org.webbitserver.WebSocketHandler;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface Server extends WebSocketHandler{
    void start();
    void stop();
}
