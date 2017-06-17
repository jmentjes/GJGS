package de.github.GSGJ.services.chatmanagement;

import de.github.GSGJ.API.service.GSGJServiceRegistry;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseService;

/**
 * Created by Kojy on 17.06.2017.
 */
public class ChatManagementService extends AbstractBaseService {

    public ChatManagementService(GSGJServiceRegistry serviceRegistry) {
        super(serviceRegistry);
    }

    @Override
    public void handle(ServerEvent obj) {
        //TODO implement chatmanagement service
    }
}
