package de.github.GSGJ.com.impl;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.structure.ServerEventType;
import de.github.GSGJ.API.structure.Connection;

/**
 * Created by Kojy on 17.06.2017.
 */
public class ServerEventImpl implements ServerEvent {
    private String message;
    private Connection connection;
    private ServerEventType eventType;

    public ServerEventImpl(String message, Connection connection, ServerEventType eventType) {
        this.message = message;
        this.connection = connection;
        this.eventType = eventType;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public ServerEventType getEventType() {
        return eventType;
    }
}
