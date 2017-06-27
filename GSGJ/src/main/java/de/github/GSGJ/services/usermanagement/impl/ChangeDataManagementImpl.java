package de.github.GSGJ.services.usermanagement.impl;

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
        //TODO change data for user
    }
}
