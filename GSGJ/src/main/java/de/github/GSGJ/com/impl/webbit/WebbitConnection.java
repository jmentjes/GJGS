package de.github.GSGJ.com.impl.webbit;

import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.com.impl.AbstractConnection;
import org.json.simple.JSONObject;
import org.webbitserver.WebSocketConnection;

import java.net.SocketAddress;

/**
 * Created by Kojy on 18.06.2017.
 */
public class WebbitConnection extends AbstractConnection {
    private WebSocketConnection webSocketConnection;

    public WebbitConnection(WebSocketConnection webSocketConnection) {
        this.webSocketConnection = webSocketConnection;
    }

    @Override
    public void send(JSONObject jsonObject) {
        //TODO implement send method
    }

    @Override
    public SocketAddress getAddress() {
        return webSocketConnection.httpRequest().remoteAddress();
    }
}
