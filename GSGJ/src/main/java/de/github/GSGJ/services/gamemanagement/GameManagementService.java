package de.github.GSGJ.services.gamemanagement;

import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseServiceSettings;

/**
 * Created by Kojy on 17.06.2017.
 */
public class GameManagementService extends AbstractBaseService {

    public GameManagementService(BaseServiceSettings baseServiceSettings) {
        super(baseServiceSettings);
    }

    @Override
    public void handle(ServerEvent obj) {
        //TODO handle gamemanagement
    }
}
