package de.github.GSGJ;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.worker.Worker;
import de.github.GSGJ.com.Server;
import de.github.GSGJ.com.MultipleServerManager;
import de.github.GSGJ.com.impl.netty.NettyServerImpl;
import de.github.GSGJ.com.impl.webbit.WebbitServerImpl;
import de.github.GSGJ.services.usermanagement.model.entities.User;
import de.github.GSGJ.services.usermanagement.model.repositories.UserRepository;
import de.github.GSGJ.util.PropertyHandler;
import de.github.GSGJ.util.PropertyHandlerImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Kojy on 17.06.2017.
 */
public class GSGJ {

    public static void main(String... args) {
        new GSGJ();
    }
    protected MultipleServerManager multipleServerManager;
    protected Worker<ServerEvent> worker;
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
        //TODO handle multiple ports
        Server netty = new NettyServerImpl(worker,port+1);
        Server webbit = new WebbitServerImpl(worker, port);
        multipleServerManager = new MultipleServerManager();
        multipleServerManager.addServer(netty);
        multipleServerManager.addServer(webbit);

        initSettings();
    }

    private void initSettings() {
        //TODO init settings for db and stuff
        SessionFactory factory;
        String filename = "";
        try{
            factory = new Configuration().configure("resources/hibernate/hibernate.cfg.xml").buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        UserRepository userRepository = new UserRepository(factory);

        int id = userRepository.addUser("hans", "§dieter", null);


    }
}
