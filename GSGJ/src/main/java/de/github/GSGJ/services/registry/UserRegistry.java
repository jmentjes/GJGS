package de.github.GSGJ.services.registry;

import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.database.entities.User;
import org.webbitserver.WebSocketConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kojy on 17.06.2017.
 */
public class UserRegistry implements Registry {
    private Map<User, Connection> userRegistry;

    public UserRegistry() {
        this.userRegistry = new HashMap<>();
    }

    public void addUser(User user, Connection connection) {
        this.userRegistry.put(user, connection);
    }

    public void updateUser(User user) {
        Connection connection = this.removeUser(user);
        this.addUser(user, connection);
    }

    public Connection removeUser(User user) {
        return this.userRegistry.remove(user);
    }

    public User removeUser(WebSocketConnection connection) {
        User toRemove = getUserFor(connection);
        this.userRegistry.remove(toRemove);
        return toRemove;
    }

    public User getUserFor(WebSocketConnection connection) {
        for (User user : this.userRegistry.keySet()) {
            Connection registeredConnection = this.userRegistry.get(user);
            if (connection.equals(registeredConnection)) {
                return user;
            }
        }
        return null;
    }

    public Connection getWebSocketConnectionFor(User user) {
        return this.userRegistry.get(user);
    }

    public boolean exists(User user) {
        return this.userRegistry.containsKey(user);
    }

    public boolean exists(WebSocketConnection connection) {
        return this.userRegistry.containsValue(connection);
    }
}
