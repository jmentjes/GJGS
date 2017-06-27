package de.github.GSGJ.services.usermanagement.impl;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.services.BaseServiceSettings;
import de.github.GSGJ.services.usermanagement.ChangeDataManagement;
import de.github.GSGJ.database.entities.User;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 17.06.2017.
 */
public class ChangeDataManagementImpl implements ChangeDataManagement {

    private BaseServiceSettings baseServiceSettings;

    public ChangeDataManagementImpl(BaseServiceSettings baseServiceSettings) {
        this.baseServiceSettings = baseServiceSettings;
    }

    @Override
    public void changeData(User user, JSONObject object) {
        String key = (String) object.get(JSONCore.CORE.PRIVATE_KEY.getId());
        if (key == null) {
            object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "Private key is not specified");
            object.put(JSONCore.CORE.SUCCESS.getId(), "false");
            return;
        }

        if (baseServiceSettings.getUserRegistry().containsPrivateKey(key)) {
            baseServiceSettings.getDatabaseRegistry().getUserRepository().updateUser(user);
        } else {
            object.put(JSONCore.CORE.ERROR_MESSAGE.getId(), "Can't change data, private key is wrong");
            object.put(JSONCore.CORE.SUCCESS.getId(),"false");
        }
    }
}
