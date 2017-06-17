package de.github.GSGJ.API.structure;

import org.webbitserver.WebSocketConnection;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface ServerEvent {
    String getMessage();

    Connection getConnection();

    ServerEventType getEventType();
}
