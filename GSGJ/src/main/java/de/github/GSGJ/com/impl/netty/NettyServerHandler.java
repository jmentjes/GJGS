package de.github.GSGJ.com.impl.netty;

import de.github.GSGJ.API.netty.nettycom.LoopBackTimeStamp;
import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.structure.ServerEventType;
import de.github.GSGJ.API.worker.Worker;
import de.github.GSGJ.com.impl.ServerEventImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private JSONParser jsonParser;
    private Worker<ServerEvent> worker;

    public NettyServerHandler(Worker<ServerEvent> worker){
        this.worker = worker;
        this.jsonParser = new JSONParser();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof JSONObject){
            this.notifyWorker(ServerEventType.MESSAGE,new NettyConnection(ctx), (JSONObject) msg);
        }else if(msg instanceof String){
            this.notifyWorker(ServerEventType.MESSAGE,new NettyConnection(ctx), (String) msg);
        }else {
            logger.error("Can't read message "+msg);
        }
    }

    // Here is how we send out heart beat for idle to long
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
//            if (event.state() == IdleState.ALL_IDLE) { // idle for no read and write
//                ctx.writeAndFlush(new LoopBackTimeStamp());
//            }
            //TODO unregister connection
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
        //TODO unregister connection
    }


    private void notifyWorker(ServerEventType eventType, Connection connection, String message) {
        try {
            this.notifyWorker(new ServerEventImpl((JSONObject) jsonParser.parse(message), connection, eventType));
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
    }

    private void notifyWorker(ServerEventType eventType, Connection connection, JSONObject obj){
        this.notifyWorker(new ServerEventImpl(obj,connection,eventType));
    }

    private void notifyWorker(ServerEvent event){
        this.worker.processData(event);
    }

}
