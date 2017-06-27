package de.github.GSGJ.services.usermanagement.impl;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.util.UsermanagementUtil;
import de.github.GSGJ.database.repositories.UserRepository;
import de.github.GSGJ.services.BaseServiceSettings;
import de.github.GSGJ.services.registry.DatabaseRegistry;
import de.github.GSGJ.services.registry.UserRegistry;
import de.github.GSGJ.services.usermanagement.RegisterManagement;
import de.github.GSGJ.database.entities.User;
import org.json.simple.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Kojy on 17.06.2017.
 */
public class RegisterManagementImpl implements RegisterManagement {
    private BaseServiceSettings baseServiceSettings;

    public RegisterManagementImpl(BaseServiceSettings baseServiceSettings) {
        this.baseServiceSettings = baseServiceSettings;
    }

    @Override
    public void register(User user, JSONObject object) {
        boolean uName = UsermanagementUtil.checkUsername(user.getName());
        boolean uPW = UsermanagementUtil.checkPassword(user.getPassword());
        boolean uEmail = UsermanagementUtil.checkEmail(user.getEmail());
        if(!uName || !uPW || !uEmail){
            object.put(JSONCore.CORE.SUCCESS.getId(),"false");

            if(!uName){
                object.put(JSONCore.CORE.ERROR_MESSAGE.getId(),"Can't save username");
            }else if (!uPW){
                object.put(JSONCore.CORE.ERROR_MESSAGE.getId(),"Can't save password");
            }else if(!uEmail){
                object.put(JSONCore.CORE.ERROR_MESSAGE.getId(),"Can't save email");
            }

        }else {
            UserRepository userRepository = this.baseServiceSettings.getDatabaseRegistry().getUserRepository();
            List<User> list = userRepository.findByNameAndPw(user.getName(),user.getPassword());

            if(list.size() != 0){
                object.put(JSONCore.CORE.SUCCESS.getId(),"false");
                object.put(JSONCore.CORE.ERROR_MESSAGE.getId(),"User already exists");
                return;
            }

            object.put(JSONCore.CORE.SUCCESS.getId(),"true");
            DatabaseRegistry databaseRegistry = baseServiceSettings.getDatabaseRegistry();
            user.setPassword(UsermanagementUtil.generateHashPassword(user.getPassword()));
            databaseRegistry.getUserRepository().registerUser(user);
        }
    }

    @Override
    public void delete(User user, JSONObject object) {
        //TODO delete user
    }

}
