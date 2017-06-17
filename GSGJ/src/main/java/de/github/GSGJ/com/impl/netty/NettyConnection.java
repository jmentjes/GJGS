package de.github.GSGJ.com.impl.netty;

import de.github.GSGJ.com.impl.AbstractConnection;
import org.json.simple.JSONObject;

import java.net.SocketAddress;

/**
 * Created by Kojy on 18.06.2017.
 */
public class NettyConnection extends AbstractConnection {
    @Override
    public void send(JSONObject jsonObject) {
        //TODO implement netty connection
    }

    @Override
    public SocketAddress getAddress() {
        return null;
    }
}
