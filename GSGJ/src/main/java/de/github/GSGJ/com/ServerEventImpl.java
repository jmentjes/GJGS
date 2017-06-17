package de.github.GSGJ.com;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.structure.ServerEventType;
import org.webbitserver.WebSocketConnection;

/**
 * Created by Kojy on 17.06.2017.
 */
public class ServerEventImpl implements ServerEvent {
    private String message;
    private WebSocketConnection connection;
    private ServerEventType eventType;

    public ServerEventImpl(String message, WebSocketConnection connection, ServerEventType eventType) {
        this.message = message;
        this.connection = connection;
        this.eventType = eventType;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public WebSocketConnection getConnection() {
        return connection;
    }

    @Override
    public ServerEventType getEventType() {
        return eventType;
    }
}
