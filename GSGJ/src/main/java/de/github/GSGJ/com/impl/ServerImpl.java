package de.github.GSGJ.com.impl;

import de.github.GSGJ.com.Server;
import de.github.GSGJ.com.ServerEvent;
import de.github.GSGJ.com.ServerEventListener;
import de.github.GSGJ.com.ServerEventType;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.handler.StaticFileHandler;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kojy on 17.06.2017.
 */
public class ServerImpl extends BaseWebSocketHandler implements Server {
    protected List<ServerEventListener> listeners;
    protected WebServer webServer;
    protected JSONParser parser;
    protected Logger logger = LoggerFactory.getLogger(ServerImpl.class);

    public ServerImpl() {
        try {
            webServer = WebServers.createWebServer(8080)
                    .add(new StaticFileHandler(getClass().getResource("/html").toURI().getPath()))
                    .add("/server", this)
                    .start()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.listeners = new LinkedList<>();
        this.parser = new JSONParser();

        logger.info("Hello World from Server! Server is running on {}", webServer.getUri().toString());
    }


    @Override
    public void addServerEventListener(ServerEventListener eventListener) {
        this.listeners.add(eventListener);
    }

    @Override
    public void removeServerEventListener(ServerEventListener eventListener) {
        this.listeners.remove(eventListener);
    }

    @Override
    public void onOpen(WebSocketConnection webSocketConnection) {
        notifyListener(ServerEventType.OPEN, webSocketConnection, "");
    }

    @Override
    public void onClose(WebSocketConnection webSocketConnection) {
        notifyListener(ServerEventType.CLOSE, webSocketConnection, "");
    }

    @Override
    public void onMessage(WebSocketConnection webSocketConnection, String s) throws Throwable {
        notifyListener(ServerEventType.MESSAGE, webSocketConnection, s);
    }

    @Override
    public void onPing(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {
        notifyListener(ServerEventType.PING, webSocketConnection, "Ping");
    }

    private void notifyListener(ServerEventType type, WebSocketConnection connection, String message) {
        notifyListeners(new ServerEventImpl(message, this, connection, type));
    }

    private void notifyListeners(ServerEvent event) {
        for (ServerEventListener listener : listeners) {
            listener.handleEvent(event);
        }
    }
}
