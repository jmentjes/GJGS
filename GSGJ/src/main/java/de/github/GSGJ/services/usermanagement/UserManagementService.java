package de.github.GSGJ.services.usermanagement;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.usermanagement.User;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseServiceSettings;
import de.github.GSGJ.services.usermanagement.impl.ChangeDataManagementImpl;
import de.github.GSGJ.services.usermanagement.impl.LoginManagementImpl;
import de.github.GSGJ.services.usermanagement.impl.RegisterManagementImpl;
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

        this.registerManagement = new RegisterManagementImpl();
        this.loginManagement = new LoginManagementImpl();
        this.changeDataManagement = new ChangeDataManagementImpl();
    }

    @Override
    public void handle(ServerEvent obj) {
        JSONObject jsonObject = obj.getJSON();
        String subject = (String) jsonObject.get("subject");
        User user = createUser(jsonObject);
        switch (subject){
            case "login":
                loginManagement.login(user,jsonObject);
                break;
            case "logout":
                loginManagement.logout(user,jsonObject);
                break;
            case "register":
                registerManagement.register(user,jsonObject);
                break;
            case "delete":
                registerManagement.delete(user,jsonObject);
                break;
            case "change-data":
                changeDataManagement.changeData(user,jsonObject);
                break;
            default:
                logger.error("Subject not recognized: "+subject);
                jsonObject.put("error-message","Subject not recognized");
                obj.getConnection().send(jsonObject);
                break;
        }
    }

    private User createUser(JSONObject jsonObject) {
        //TODO create User Object
        return null;
    }
}
