package servertest.com;

import de.github.GSGJ.API.netty.nettycom.Envelope;
import de.github.GSGJ.API.netty.nettycom.Type;
import de.github.GSGJ.API.netty.nettycom.Version;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Kojy on 26.06.2017.
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {
    private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final ChannelGroup channelGroup;
    private Channel channel;
    private Transceiver transceiver;

    public ClientHandler(Transceiver transceiver, ChannelGroup channels) {
        this.channelGroup = channels;
        this.transceiver = transceiver;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        if (e.getMessage() instanceof Envelope) {
            Object object = getMessage(((Envelope) e.getMessage()).getPayload());
            if (object == null) return;
            if (object instanceof JSONObject) {
                transceiver.receive((JSONObject) object);
            } else if (object instanceof String) {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse((String) object);
                transceiver.receive(jsonObject);

            } else {
                logger.error("Can't read message " + object);
            }
        } else {
            super.messageReceived(ctx, e);
        }
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        this.channel = e.getChannel();
        this.channelGroup.add(e.getChannel());
    }

    public void sendMessage(JSONObject jsonObject) {
        if (this.channel != null) {
            this.channel.write(new Envelope(Version.VERSION1, Type.REQUEST, jsonObject.toJSONString().getBytes()));
            System.out.println("Sending message: " + jsonObject.toJSONString());
        }
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
}
