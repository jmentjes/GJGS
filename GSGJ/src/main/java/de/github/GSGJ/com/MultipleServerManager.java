package de.github.GSGJ.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kojy on 18.06.2017.
 */
public class MultipleServerManager {
    private Logger logger = LoggerFactory.getLogger(MultipleServerManager.class);
    private List<Server> serverList;

    public MultipleServerManager() {
        this(new LinkedList<>());
    }

    public MultipleServerManager(List<Server> serverList) {
        if (serverList == null) {
            this.serverList = new LinkedList<>();
        } else {
            this.serverList = serverList;
        }
        checkServers();
    }

    public void addServer(Server server) {
        this.serverList.add(server);
        checkServers();
    }

    public void startServers() {
        checkServers();
        for (Server server : serverList) {
            server.start();
        }
    }

    public void stopServers() {
        checkServers();
        for (Server server : serverList) {
            server.stop();
        }
    }

    private void checkServers() {
        for (Server server : serverList) {
            int port = server.getPort();
            String address = server.getAddress();
            for (Server other : serverList) {
                if (!other.equals(server) && port == other.getPort()) {
                    logger.error("Multiple servers are running on the same port");
                }
                if (!address.equals(other.getAddress())) {
                    logger.error("Multiple servers are not running on the same address");
                }
            }
        }
    }

    //TODO add more stuff for multiple severs like port verification and single ip

}
