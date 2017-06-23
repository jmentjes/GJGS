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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
    private static Logger logger = LoggerFactory.getLogger(GSGJ.class);

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
        int port = -1;
        try {
            port = Integer.parseInt(propertyHandler.read("server.port"));
        }catch (NumberFormatException e){
            logger.error(e.getMessage(),e);
        }
        if (port < 0){
            logger.error("Can't start servers, port is invalid");
            return;
        }

        Server netty = null;
        Server webbit = null;
        try {
            netty = new NettyServerImpl(worker, InetAddress.getByName("localhost").getHostAddress(),port+1);
            webbit = new WebbitServerImpl(worker,InetAddress.getByName("localhost").getHostAddress(), port);
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(),e);
        }

        multipleServerManager = new MultipleServerManager();
        multipleServerManager.addServer(netty);
        multipleServerManager.addServer(webbit);

        multipleServerManager.startServers();
        initSettings();
    }

    private void initSettings() {
        //TODO init settings for db and stuff
        SessionFactory factory;
        try{
            factory = new Configuration().configure("resources/hibernate/hibernate.cfg.xml").buildSessionFactory();
        }catch (Throwable ex) {
            logger.error("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        UserRepository userRepository = new UserRepository(factory);

        int id = userRepository.addUser("hans", "§dieter", null);


    }
}
