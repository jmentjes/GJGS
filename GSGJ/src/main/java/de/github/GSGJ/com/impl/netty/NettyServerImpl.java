package de.github.GSGJ.com.impl.netty;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.worker.Worker;
import de.github.GSGJ.com.Server;
import de.github.GSGJ.API.netty.nettycom.TimeStampDecoder;
import de.github.GSGJ.API.netty.nettycom.TimeStampEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kojy on 18.06.2017.
 */
public class NettyServerImpl implements Server {
    private Logger logger = LoggerFactory.getLogger(NettyServerImpl.class);
    private ServerBootstrap serverBootstrap;
    private Worker<ServerEvent> worker;
    private int port;

    public NettyServerImpl(Worker<ServerEvent> worker, int port){
        this.port = port;
        this.worker = worker;

        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boosGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);

        // ===========================================================
        // 1. define a separate thread pool to execute handlers with
        //    slow business logic. e.g database operation
        // ===========================================================
        final EventExecutorGroup group = new DefaultEventExecutorGroup(1500); //thread pool of 1500

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("idleStateHandler",new IdleStateHandler(0,0,5)); // add with name
                pipeline.addLast(new TimeStampEncoder()); // add without name, name auto generated
                pipeline.addLast(new TimeStampDecoder()); // add without name, name auto generated

                //===========================================================
                // 2. run handler with slow business logic
                //    in separate thread from I/O thread
                //===========================================================
                pipeline.addLast(group,"serverHandler",new NettyServerHandler());
            }
        });

        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            serverBootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
        logger.info("Hello World from Server! Server is running on {}", port );
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
