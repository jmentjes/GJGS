package de.github.GSGJ.com.impl.netty;

import de.github.GSGJ.com.AbstractConnection;
import io.netty.channel.ChannelHandlerContext;
import org.json.simple.JSONObject;

import java.net.SocketAddress;

/**
 * Created by Kojy on 18.06.2017.
 */
public class NettyConnection extends AbstractConnection {
    private ChannelHandlerContext channelHandlerContext;

    public NettyConnection(ChannelHandlerContext handlerContext){
        this.channelHandlerContext = handlerContext;
    }

    @Override
    public void send(JSONObject jsonObject) {
        this.channelHandlerContext.write(jsonObject.toJSONString());
    }

    @Override
    public SocketAddress getAddress() {
        return channelHandlerContext.channel().remoteAddress();
    }
}
