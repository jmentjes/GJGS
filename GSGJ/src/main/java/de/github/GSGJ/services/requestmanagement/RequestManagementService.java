package de.github.GSGJ.services.requestmanagement;

import de.github.GSGJ.API.service.GSGJServiceRegistry;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.services.AbstractBaseService;

/**
 * Created by Kojy on 17.06.2017.
 */
public class RequestManagementService extends AbstractBaseService {

    public RequestManagementService(GSGJServiceRegistry serviceRegistry) {
        super(serviceRegistry);
    }

    @Override
    public void handle(ServerEvent obj) {
        //TODO implement requestmanagment which can be used to request something like logged in users
    }
}
