package de.github.GSGJ.com.impl;

import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.structure.ServerEventType;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 17.06.2017.
 */
public class ServerEventImpl implements ServerEvent {
    private JSONObject json;
    private Connection connection;
    private ServerEventType eventType;

    public ServerEventImpl(JSONObject json, Connection connection, ServerEventType eventType) {
        this.json = json;
        this.connection = connection;
        this.eventType = eventType;
    }

    @Override
    public JSONObject getJSON() {
        return json;
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
