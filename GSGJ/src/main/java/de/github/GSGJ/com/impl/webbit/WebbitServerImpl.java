package de.github.GSGJ.com.impl.webbit;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.structure.ServerEventType;
import de.github.GSGJ.API.worker.Worker;
import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.com.Server;
import de.github.GSGJ.com.impl.ServerEventImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
public class WebbitServerImpl extends BaseWebSocketHandler implements Server {
    protected WebServer webServer;
    protected Logger logger = LoggerFactory.getLogger(WebbitServerImpl.class);
    protected Worker<ServerEvent> worker;
    protected JSONParser jsonParser;
    protected int port;
    protected String address;

    public WebbitServerImpl(Worker<ServerEvent> worker,String address, int port) {
        try {
            webServer = WebServers.createWebServer(port)
                    .add(new StaticFileHandler(getClass().getResource("/html").toURI().getPath()))
                    .add("/server", this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.worker = worker;
        this.jsonParser = new JSONParser();
        this.port = port;
        this.address = address;
        logger.info("Hello World from Server! Server is running on {}", webServer.getUri().toString());
    }

    @Override
    public void start() {
        try {
            this.webServer = this.webServer.start().get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    public void stop() {
        this.webServer.stop();
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void onOpen(WebSocketConnection webSocketConnection) {
        notifyWorker(ServerEventType.OPEN, new WebbitConnection(webSocketConnection), "");
    }

    @Override
    public void onClose(WebSocketConnection webSocketConnection) {
        notifyWorker(ServerEventType.CLOSE, new WebbitConnection(webSocketConnection), "");
    }

    @Override
    public void onMessage(WebSocketConnection webSocketConnection, String s) throws Throwable {
        notifyWorker(ServerEventType.MESSAGE, new WebbitConnection(webSocketConnection), s);
    }

    @Override
    public void onPing(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {
        notifyWorker(ServerEventType.PING, new WebbitConnection(webSocketConnection), "Ping");
    }

    private void notifyWorker(ServerEventType eventType, Connection connection, String message) {
        try {
            this.notifyWorker(new ServerEventImpl((JSONObject) jsonParser.parse(message), connection, eventType));
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
    }

    private void notifyWorker(ServerEvent event) {
        this.worker.processData(event);
    }

}
