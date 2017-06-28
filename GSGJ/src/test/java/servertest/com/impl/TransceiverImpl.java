package servertest.com.impl;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.netty.nettycom.Decoder;
import de.github.GSGJ.API.netty.nettycom.Encoder;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servertest.ServerTest;
import servertest.com.AbstractTransceiver;
import servertest.com.ClientHandler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by Kojy on 26.06.2017.
 */
public class TransceiverImpl extends AbstractTransceiver {
    private static Logger logger = LoggerFactory.getLogger(TransceiverImpl.class);
    protected InetAddress inetAddress;
    protected int port;
    private ChannelFactory clientFactory;
    private ChannelGroup channelGroup;
    private ClientHandler handler;

    public TransceiverImpl() {
        this(false, null, 0);
    }

    public TransceiverImpl(boolean connect, InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;

        if (connect) {
            this.start();
        }
    }

    public void start() {
        // For production scenarios, use limited sized thread pools
        this.clientFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        this.channelGroup = new DefaultChannelGroup(this + "-channelGroup");
        this.handler = new ClientHandler(this, channelGroup);
        ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("encoder", new Encoder());
                pipeline.addLast("decoder", new Decoder());
                pipeline.addLast("handler", handler);
                return pipeline;
            }
        };

        ClientBootstrap bootstrap = new ClientBootstrap(this.clientFactory);
        bootstrap.setOption("reuseAddress", true);
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setPipelineFactory(pipelineFactory);


        boolean connected = bootstrap.connect(new InetSocketAddress(inetAddress, port)).awaitUninterruptibly().isSuccess();
        if (!connected) {
            this.stop();
        }
    }

    public void stop() {
        if (this.channelGroup != null) {
            this.channelGroup.close();
        }
        if (this.clientFactory != null) {
            this.clientFactory.releaseExternalResources();
        }
    }

    public void send(JSONObject object) {
        this.handler.sendMessage(object);
    }

    public void receive(JSONObject jsonObject) {
        ServerTest.receive(jsonObject);
    }
}
