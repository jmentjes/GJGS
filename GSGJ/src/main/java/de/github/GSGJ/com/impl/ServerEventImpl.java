package de.github.GSGJ.com.impl;

import de.github.GSGJ.com.Server;
import de.github.GSGJ.com.ServerEvent;
import de.github.GSGJ.com.ServerEventType;
import org.webbitserver.WebSocketConnection;

/**
 * Created by Kojy on 17.06.2017.
 */
public class ServerEventImpl implements ServerEvent {
    private String message;
    private Server server;
    private WebSocketConnection connection;
    private ServerEventType eventType;

    public ServerEventImpl(String message, Server server, WebSocketConnection connection, ServerEventType eventType) {
        this.message = message;
        this.server = server;
        this.connection = connection;
        this.eventType = eventType;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Server getServer() {
        return server;
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
