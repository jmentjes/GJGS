package de.github.GSGJ.services.registry;

import de.github.GSGJ.database.entities.User;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface Registry {
    boolean logoutUser(User user);
}
