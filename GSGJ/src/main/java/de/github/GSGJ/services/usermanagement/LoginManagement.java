package de.github.GSGJ.services.usermanagement;

import de.github.GSGJ.API.usermanagement.User;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface LoginManagement {
    void login(User user, JSONObject object);
    void logout(User user, JSONObject object);
}
