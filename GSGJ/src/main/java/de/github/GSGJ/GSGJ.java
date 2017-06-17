package de.github.GSGJ;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.worker.Worker;
import de.github.GSGJ.com.Server;
import de.github.GSGJ.com.impl.ServerImpl;
import de.github.GSGJ.util.PropertyHandler;
import de.github.GSGJ.util.PropertyHandlerImpl;

/**
 * Created by Kojy on 17.06.2017.
 */
public class GSGJ {

    public static void main(String... args) {
        new GSGJ();
    }

    protected Worker<ServerEvent> worker;
    protected Server server;
    protected PropertyHandler propertyHandler;
    public GSGJ() {
        this(false);
    }

    public GSGJ(boolean delayStart) {
        propertyHandler = new PropertyHandlerImpl("", "serverconfig.properties");
        this.worker = new GSGJWorker();
        Thread workerThread = new Thread(this.worker);
        workerThread.setDaemon(true);
        workerThread.start();

        if (!delayStart) {
            start();
        }
    }

    public void start() {
        int port = Integer.parseInt(propertyHandler.read("server.port"));
        server = new ServerImpl(worker, port);
        initSettings();
    }

    private void initSettings() {
        //TODO init settings for db and stuff
    }
}
