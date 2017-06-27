package de.github.GSGJ.services.usermanagement;

import de.github.GSGJ.services.usermanagement.model.entities.User;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface RegisterManagement {
    void register(User user, JSONObject object);

    void delete(User user, JSONObject object);
}
