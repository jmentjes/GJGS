package de.github.GSGJ.services.usermanagement;

import de.github.GSGJ.database.entities.User;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface ChangeDataManagement {
    void changeData(User user, JSONObject object);
}
