package de.github.GSGJ.services.usermanagement;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseServiceSettings;
import de.github.GSGJ.services.usermanagement.impl.ChangeDataManagementImpl;
import de.github.GSGJ.services.usermanagement.impl.LoginManagementImpl;
import de.github.GSGJ.services.usermanagement.impl.RegisterManagementImpl;
import de.github.GSGJ.database.entities.User;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kojy on 17.06.2017.
 */
public class UserManagementService extends AbstractBaseService {
    private Logger logger = LoggerFactory.getLogger(UserManagementService.class);
    private RegisterManagement registerManagement;
    private LoginManagement loginManagement;
    private ChangeDataManagement changeDataManagement;

    public UserManagementService(BaseServiceSettings baseServiceSettings) {
        super(baseServiceSettings);

        this.registerManagement = new RegisterManagementImpl(baseServiceSettings);
        this.loginManagement = new LoginManagementImpl(baseServiceSettings);
        this.changeDataManagement = new ChangeDataManagementImpl(baseServiceSettings);
    }

    @Override
    public void handle(ServerEvent obj) {
        JSONObject jsonObject = obj.getJSON();
        String subject = (String) jsonObject.get(JSONCore.CORE.SUBJECT.getId());

        logger.debug("Incoming message with subject {}", subject);
        User user = createUser(jsonObject);
        if (user == null) {
            jsonObject.put("error-message", "Can't read user information");
            obj.getConnection().send(jsonObject);
            return;
        }
        switch (subject) {
            case "login":
                loginManagement.login(user, jsonObject, obj.getConnection());
                break;
            case "logout":
                loginManagement.logout(user, jsonObject, obj.getConnection());
                break;
            case "register":
                registerManagement.register(user, jsonObject);
                break;
            case "delete":
                registerManagement.delete(user, jsonObject);
                break;
            case "change-data":
                changeDataManagement.changeData(user, jsonObject);
                break;
            default:
                logger.error("Subject not recognized: " + subject);
                jsonObject.put("error-message", "Subject not recognized");
                break;
        }

        obj.getConnection().send(jsonObject);
    }

    private User createUser(JSONObject jsonObject) {
        String username = (String) jsonObject.get(JSONCore.CORE.USERNAME.getId());
        String email = (String) jsonObject.get(JSONCore.CORE_USERMANAGEMENT.EMAIL.getId());
        String password = (String) jsonObject.get(JSONCore.CORE_USERMANAGEMENT.PASSWORD.getId());
        if (username == null) {
            return null;
        }
        return new User(username,email,password);
    }


}
