package de.github.GSGJ.services.usermanagement;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseServiceSettings;
import de.github.GSGJ.services.usermanagement.impl.ChangeDataManagementImpl;
import de.github.GSGJ.services.usermanagement.impl.LoginManagementImpl;
import de.github.GSGJ.services.usermanagement.impl.RegisterManagementImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kojy on 17.06.2017.
 */
public class UserManagementService extends AbstractBaseService {
    private Logger logger = LoggerFactory.getLogger(UserManagementService.class);
    private JSONParser parser;
    private RegisterManagement registerManagement;
    private LoginManagement loginManagement;
    private ChangeDataManagement changeDataManagement;

    public UserManagementService(BaseServiceSettings baseServiceSettings) {
        super(baseServiceSettings);

        this.registerManagement = new RegisterManagementImpl();
        this.loginManagement = new LoginManagementImpl();
        this.changeDataManagement = new ChangeDataManagementImpl();

        this.parser = new JSONParser();
    }

    @Override
    public void handle(ServerEvent obj) {
        JSONObject jsonObject = createJSONObject(obj.getMessage());
        if (jsonObject == null) return;

        //TODO handle usermanagement

        switch (obj.getEventType()) {
            case MESSAGE:
                break;
            case OPEN:
                break;
            case CLOSE:
                break;
            case PING:
                break;
        }
    }

    private JSONObject createJSONObject(String message) {
        try {
            return (JSONObject) parser.parse(message);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
