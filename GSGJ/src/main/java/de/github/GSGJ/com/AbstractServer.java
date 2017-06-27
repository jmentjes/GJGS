package de.github.GSGJ.com;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.worker.Worker;

/**
 * Created by Lenovo on 23.06.2017.
 */
public abstract class AbstractServer implements Server {
    protected Worker<ServerEvent> worker;
    protected int port;
    protected String address;

    public AbstractServer(Worker<ServerEvent> worker, String address, int port) {
        this.worker = worker;
        this.port = port;
        this.address = address;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getAddress() {
        return address;
    }
}
