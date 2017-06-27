package de.github.GSGJ.com.impl.netty;

import de.github.GSGJ.API.netty.nettycom.Envelope;
import de.github.GSGJ.API.netty.nettycom.Type;
import de.github.GSGJ.API.netty.nettycom.Version;
import de.github.GSGJ.com.AbstractConnection;
import org.jboss.netty.channel.Channel;
import org.json.simple.JSONObject;

import java.net.SocketAddress;

/**
 * Created by Kojy on 18.06.2017.
 */
public class NettyConnection extends AbstractConnection {
    private Channel channel;

    public NettyConnection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(JSONObject jsonObject) {
        if (this.channel != null) {
            this.channel.write(new Envelope(Version.VERSION1, Type.REQUEST, jsonObject.toJSONString().getBytes()));
        }
    }

    @Override
    public SocketAddress getAddress() {
        return channel.getRemoteAddress();
    }
}
