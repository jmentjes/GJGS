package de.github.GSGJ.services.registry;

import de.github.GSGJ.database.entities.User;
import de.github.GSGJ.database.repositories.UserRepository;
import de.github.GSGJ.database.repositories.UsergroupRepository;

/**
 * Created by Kojy on 27.06.2017.
 */
public class DatabaseRegistry implements Registry {
    private UserRepository userRepository;
    private UsergroupRepository usergroupRepository;

    public DatabaseRegistry(UserRepository userRepository,
                            UsergroupRepository usergroupRepository) {
        this.userRepository = userRepository;
        this.usergroupRepository = usergroupRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UsergroupRepository getUsergroupRepository() {
        return usergroupRepository;
    }

    @Override
    public boolean logoutUser(User user) {
        //ignored
        return true;
    }
}
