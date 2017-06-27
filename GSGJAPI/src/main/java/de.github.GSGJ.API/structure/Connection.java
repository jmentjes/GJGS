package de.github.GSGJ.API.structure;

import org.json.simple.JSONObject;

import java.net.SocketAddress;

/**
 * Created by Kojy on 18.06.2017.
 */
public interface Connection {
    void send(JSONObject jsonObject);

    SocketAddress getAddress();
}
