package de.github.GSGJ.services.requestmanagement;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseServiceSettings;

/**
 * Created by Kojy on 17.06.2017.
 */
public class RequestManagementService extends AbstractBaseService {

    public RequestManagementService(BaseServiceSettings baseServiceSettings) {
        super(baseServiceSettings);
    }

    @Override
    public void handle(ServerEvent obj) {
        //TODO implement requestmanagment which can be used to request something like logged in users
        //TODO implement something like listeners for clients, notify is xy changes
    }
}
