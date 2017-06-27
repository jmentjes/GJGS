package de.github.GSGJ.com.impl.netty;

import de.github.GSGJ.API.netty.nettycom.Envelope;
import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.structure.ServerEventType;
import de.github.GSGJ.API.worker.Worker;
import de.github.GSGJ.com.impl.ServerEventImpl;
import org.jboss.netty.channel.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class NettyServerHandler extends SimpleChannelUpstreamHandler {
    private static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private JSONParser jsonParser;
    private Worker<ServerEvent> worker;

    public NettyServerHandler(Worker<ServerEvent> worker) {
        this.worker = worker;
        this.jsonParser = new JSONParser();
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.debug(ctx.getName());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        logger.info("MEssage:_ " + e.getMessage().toString());


        if (e.getMessage() instanceof Envelope) {
            Object object = getMessage(((Envelope) e.getMessage()).getPayload());
            if (object == null) return;
            if (object instanceof JSONObject) {
                this.notifyWorker(ServerEventType.MESSAGE, new NettyConnection(ctx.getChannel()), (JSONObject) object);
            } else if (object instanceof String) {
                this.notifyWorker(ServerEventType.MESSAGE, new NettyConnection(ctx.getChannel()), (String) object);
            } else {
                logger.error("Can't read message " + object);
            }
        } else {
            super.messageReceived(ctx, e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    /**
     * Deserializes a message object and determines its type.
     *
     * @param data The received byte array
     * @return int
     */
    private Object getMessage(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();

        } catch (IOException | ClassNotFoundException cnf) {
            return new String(data);

        }
    }

    private void notifyWorker(ServerEventType eventType, Connection connection, String message) {
        try {
            this.notifyWorker(new ServerEventImpl((JSONObject) jsonParser.parse(message), connection, eventType));
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void notifyWorker(ServerEventType eventType, Connection connection, JSONObject obj) {
        this.notifyWorker(new ServerEventImpl(obj, connection, eventType));
    }

    private void notifyWorker(ServerEvent event) {
        this.worker.processData(event);
    }

}
