package de.github.GSGJ.API.structure;

import org.json.simple.JSONObject;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface ServerEvent {
    JSONObject getJSON();

    Connection getConnection();

    ServerEventType getEventType();
}
