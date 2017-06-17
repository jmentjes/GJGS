package de.github.GSGJ.services.registry;

import de.github.GSGJ.API.usermanagement.User;
import org.webbitserver.WebSocketConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kojy on 17.06.2017.
 */
public class UserRegistry implements Registry {
    private Map<User, WebSocketConnection> userRegistry;

    public UserRegistry() {
        this.userRegistry = new HashMap<>();
    }


}
