package de.github.GSGJ.com.impl.netty;

import de.github.GSGJ.API.netty.nettycom.Decoder;
import de.github.GSGJ.API.netty.nettycom.Encoder;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.worker.Worker;
import de.github.GSGJ.com.AbstractServer;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by Kojy on 18.06.2017.
 */
public class NettyServerImpl extends AbstractServer {
    protected Logger logger = LoggerFactory.getLogger(NettyServerImpl.class);
    private DefaultChannelGroup channelGroup;
    private ServerChannelFactory serverFactory;

    public NettyServerImpl(Worker<ServerEvent> worker, String address, int port) {
        super(worker, address, port);
    }

    // public methods -------------------------------------------------------------------------------------------------

    public void start() {
        this.serverFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        this.channelGroup = new DefaultChannelGroup(this + "-channelGroup");
        ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("encoder", new Encoder());
                pipeline.addLast("decoder", new Decoder());
                pipeline.addLast("handler", new NettyServerHandler(worker));
                return pipeline;
            }
        };

        ServerBootstrap bootstrap = new ServerBootstrap(this.serverFactory);
        bootstrap.setOption("reuseAddress", true);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.setPipelineFactory(pipelineFactory);

        Channel channel = bootstrap.bind(new InetSocketAddress(this.address, this.port));
        if (!channel.isBound()) {
            this.stop();
        }
        this.channelGroup.add(channel);
    }

    public void stop() {
        if (this.channelGroup != null) {
            this.channelGroup.close();
        }
        if (this.serverFactory != null) {
            this.serverFactory.releaseExternalResources();
        }
    }
}
