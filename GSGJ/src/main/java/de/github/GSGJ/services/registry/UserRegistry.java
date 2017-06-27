package de.github.GSGJ.services.registry;

import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.database.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webbitserver.WebSocketConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kojy on 17.06.2017.
 */
public class UserRegistry implements Registry {
    private Map<User, Wrapper> userRegistry;

    public UserRegistry() {
        this.userRegistry = new HashMap<>();
    }

    public void addUser(User user, Connection connection, String key) {
        this.userRegistry.put(user, new Wrapper(connection,key));
    }

    public void updateUser(User user) {
        Wrapper wrapper = this.userRegistry.remove(user);
        this.addUser(user, wrapper.getConnection(),wrapper.getKey());
    }

    public void updateConnection(Connection connection){
        User user = getUserFor(connection);
        Wrapper wrapper = this.userRegistry.remove(user);
        wrapper.setConnection(connection);
        this.userRegistry.put(user, wrapper);
    }

    public Connection removeUser(User user) {
        Wrapper wrapper = this.userRegistry.remove(user);
        if(wrapper == null){
            return null;
        }else {
            return wrapper.getConnection();
        }
    }

    public User removeUser(Connection connection) {
        User toRemove = getUserFor(connection);
        this.userRegistry.remove(toRemove);
        return toRemove;
    }

    public User getUserFor(Connection connection) {
        for (User user : this.userRegistry.keySet()) {
            Connection registeredConnection = this.userRegistry.get(user).getConnection();
            if (connection.equals(registeredConnection)) {
                return user;
            }
        }
        return null;
    }

    public Connection getWebSocketConnectionFor(User user) {
        return this.userRegistry.get(user).getConnection();
    }

    public boolean contains(User user) {
        return this.userRegistry.containsKey(user);
    }

    public boolean contains(Connection connection) {
        for (Wrapper wrapper : this.userRegistry.values()){
            if(wrapper.getConnection().hashCode() == connection.hashCode()){
                return true;
            }
        }
        return false;
    }

    public boolean containsPrivateKey(String privateKey){
        Logger logger = LoggerFactory.getLogger(UserRegistry.class);
        for (Wrapper wrapper : this.userRegistry.values()){
            if(wrapper.getKey().equals(privateKey)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean logoutUser(User user) {
        return this.removeUser(user) != null;
    }

    private class Wrapper{
        private Connection connection;
        private String key;
        private Wrapper(Connection connection, String key){
            this.connection = connection;
            this.key = key;
        }

        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }

        public String getKey() {
            return key;
        }
    }
}
