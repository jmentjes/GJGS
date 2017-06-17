package de.github.GSGJ.services.lobbymanagement;

import de.github.GSGJ.API.service.GSGJServiceRegistry;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.services.AbstractBaseService;

/**
 * Created by Kojy on 17.06.2017.
 */
public class LobbyManagementService extends AbstractBaseService {


    public LobbyManagementService(GSGJServiceRegistry serviceRegistry) {
        super(serviceRegistry);
    }

    @Override
    public void handle(ServerEvent obj) {
        //TODO handle lobby management
    }
}
