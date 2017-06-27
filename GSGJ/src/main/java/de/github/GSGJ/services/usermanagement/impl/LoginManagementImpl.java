package de.github.GSGJ.services.usermanagement.impl;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.services.usermanagement.LoginManagement;
import de.github.GSGJ.services.usermanagement.model.entities.User;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kojy on 17.06.2017.
 */
public class LoginManagementImpl implements LoginManagement {
    private static Logger logger = LoggerFactory.getLogger(LoginManagementImpl.class);

    @Override
    public void login(User user, JSONObject object) {
        //TODO login user
        logger.info(object.toJSONString());
        object.put(JSONCore.CORE.SUCCESS.getId(), "true");
    }

    @Override
    public void logout(User user, JSONObject object) {
        //TODO logout user
    }
}
