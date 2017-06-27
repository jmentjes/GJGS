package de.github.GSGJ.services.usermanagement.impl;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.API.util.UsermanagementUtil;
import de.github.GSGJ.database.repositories.UserRepository;
import de.github.GSGJ.services.BaseServiceSettings;
import de.github.GSGJ.services.registry.UserRegistry;
import de.github.GSGJ.services.usermanagement.LoginManagement;
import de.github.GSGJ.database.entities.User;
import de.github.GSGJ.services.usermanagement.UserManagementService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Kojy on 17.06.2017.
 */
public class LoginManagementImpl implements LoginManagement {
    private static Logger logger = LoggerFactory.getLogger(LoginManagementImpl.class);
    private BaseServiceSettings baseServiceSettings;

    public LoginManagementImpl(BaseServiceSettings baseServiceSettings){
        this.baseServiceSettings = baseServiceSettings;
    }

    @Override
    public void login(User user, JSONObject object, Connection connection) {
        //TODO implement password hashing

        boolean uName = UsermanagementUtil.checkUsername(user.getName());
        boolean uPW = UsermanagementUtil.checkPassword(user.getPassword());
        if(!uName || !uPW){
            object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "Username or password is incorrect");
            object.put(JSONCore.CORE.SUCCESS.getId(),"false");
            return;
        }else {
            UserRepository userRepository = baseServiceSettings.getDatabaseRegistry().getUserRepository();
            List others = userRepository.findByNameAndPw(user.getName(),user.getPassword());
            if(others.size() != 1){
                object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "Can't find user");
                object.put(JSONCore.CORE.SUCCESS.getId(),"false");
                return;
            }

            User other = (User) others.get(0);
            if(user.getName().equals(other.getName()) && user.getPassword().equals(other.getPassword())){
                object.put(JSONCore.CORE.SUCCESS.getId(), "true");
                object.put(JSONCore.CORE_USERMANAGEMENT.EMAIL.getId(),other.getEmail());
                //TODO change this
                object.put(JSONCore.CORE.PRIVATE_KEY, other.getId());
                object.remove(JSONCore.CORE_USERMANAGEMENT.PASSWORD.getId());
                baseServiceSettings.getUserRegistry().addUser(user,connection);
            }
        }
    }

    @Override
    public void logout(User user, JSONObject object, Connection connection) {
        //TODO logout user
        UserRegistry userRegistry = baseServiceSettings.getUserRegistry();
        userRegistry.removeUser(user);
    }
}
