package de.github.GSGJ.services.lobbymanagement;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseServiceSettings;

/**
 * Created by Kojy on 17.06.2017.
 */
public class LobbyManagementService extends AbstractBaseService {


    public LobbyManagementService(BaseServiceSettings baseServiceSettings) {
        super(baseServiceSettings);
    }

    @Override
    public void handle(ServerEvent obj) {
        //TODO handle lobby management
    }
}
