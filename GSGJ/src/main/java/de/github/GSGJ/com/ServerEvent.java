package de.github.GSGJ.com;

import org.webbitserver.WebSocketConnection;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface ServerEvent {
    String getMessage();

    Server getServer();

    WebSocketConnection getConnection();

    ServerEventType getEventType();
}
