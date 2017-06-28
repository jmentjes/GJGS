package de.github.GSGJ.services.usermanagement.impl;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.structure.Connection;
import de.github.GSGJ.API.util.UsermanagementUtil;
import de.github.GSGJ.database.repositories.UserRepository;
import de.github.GSGJ.services.BaseServiceSettings;
import de.github.GSGJ.services.registry.*;
import de.github.GSGJ.services.usermanagement.LoginManagement;
import de.github.GSGJ.database.entities.User;
import de.github.GSGJ.services.usermanagement.UserManagementService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
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
        user.setPassword(UsermanagementUtil.generateHashPassword(user.getPassword()));
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

                if(this.baseServiceSettings.getUserRegistry().contains(other)){
                    object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "User is already logged in");
                    object.put(JSONCore.CORE.SUCCESS.getId(),"false");
                }else {
                    String privateKey = generatePrivateKey();

                    user.setPassword(null);
                    user.setEmail(other.getEmail());
                    user.setGroups(other.getGroups());
                    user.setId(other.getId());

                    object.put(JSONCore.CORE.SUCCESS.getId(), "true");
                    object.put(JSONCore.CORE_USERMANAGEMENT.EMAIL.getId(),user.getEmail());
                    object.put(JSONCore.CORE.PRIVATE_KEY.getId(), privateKey);
                    object.put(JSONCore.CORE.USERNAME.getId(),user.getName());
                    object.put(JSONCore.CORE.USER_ID.getId(),String.valueOf(user.getId()));
                    object.remove(JSONCore.CORE_USERMANAGEMENT.PASSWORD.getId());

                    baseServiceSettings.getUserRegistry().addUser(user, connection, privateKey);
                }
            }else {
                object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "Username or password is incorrect");
                object.put(JSONCore.CORE.SUCCESS.getId(),"false");
            }
        }
    }

    @Override
    public void logout(User user, JSONObject object, Connection connection) {
        String key = (String) object.get(JSONCore.CORE.PRIVATE_KEY.getId());
        if(key == null){
            object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "Private key is not specified");
            object.put(JSONCore.CORE.SUCCESS.getId(),"false");
            return;
        }

        if(baseServiceSettings.getUserRegistry().containsPrivateKey(key)){
            for (Registry registry : baseServiceSettings.getList()){
                if(!registry.logoutUser(user)){
                    object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "Can't logout user");
                    object.put(JSONCore.CORE.SUCCESS.getId(),"false");
                    return;
                }else {
                    object.remove(JSONCore.CORE.ERROR_MESSAGE.getId());
                    object.put(JSONCore.CORE.SUCCESS.getId(),"true");
                }
            }
        }else {
            object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "Private key is wrong");
            object.put(JSONCore.CORE.SUCCESS.getId(),"false");
            return;
        }
    }

    private String generatePrivateKey(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

}
