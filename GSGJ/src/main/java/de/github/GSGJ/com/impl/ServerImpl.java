package de.github.GSGJ.com.impl;

import de.github.GSGJ.API.worker.Worker;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.structure.ServerEventType;
import de.github.GSGJ.com.Server;
import de.github.GSGJ.structure.ServerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.handler.StaticFileHandler;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kojy on 17.06.2017.
 */
public class ServerImpl extends BaseWebSocketHandler implements Server {
    protected WebServer webServer;
    protected Logger logger = LoggerFactory.getLogger(ServerImpl.class);
    protected Worker<ServerEvent> worker;

    public ServerImpl(Worker<ServerEvent> worker, int port) {
        try {
            webServer = WebServers.createWebServer(port)
                    .add(new StaticFileHandler(getClass().getResource("/html").toURI().getPath()))
                    .add("/server", this)
                    .start()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.worker = worker;
        logger.info("Hello World from Server! Server is running on {}", webServer.getUri().toString());
    }

    @Override
    public void start() {
        this.webServer.start();
    }

    @Override
    public void stop() {
        this.webServer.stop();
    }

    @Override
    public void onOpen(WebSocketConnection webSocketConnection) {
        notifyWorker(ServerEventType.OPEN, webSocketConnection, "");
    }

    @Override
    public void onClose(WebSocketConnection webSocketConnection) {
        notifyWorker(ServerEventType.CLOSE, webSocketConnection, "");
    }

    @Override
    public void onMessage(WebSocketConnection webSocketConnection, String s) throws Throwable {
        notifyWorker(ServerEventType.MESSAGE, webSocketConnection, s);
    }

    @Override
    public void onPing(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {
        notifyWorker(ServerEventType.PING, webSocketConnection, "Ping");
    }

    private void notifyWorker(ServerEventType eventType, WebSocketConnection connection, String message){
        this.notifyWorker(new ServerEventImpl(message, connection, eventType));
    }

    private void notifyWorker(ServerEvent event){
        this.worker.processData(event);
    }

}
