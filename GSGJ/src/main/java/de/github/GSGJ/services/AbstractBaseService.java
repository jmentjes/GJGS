package de.github.GSGJ.services;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.worker.AbstractWorker;

/**
 * Created by Kojy on 17.06.2017.
 */
public abstract class AbstractBaseService extends AbstractWorker<ServerEvent> implements BaseService {
    protected BaseServiceSettings baseServiceSettings;

    public AbstractBaseService(BaseServiceSettings baseServiceSettings) {
        this.baseServiceSettings = baseServiceSettings;
    }

    @Override
    public String getID() {
        return getClass().getName();
    }

    @Override
    public void handleEvent(ServerEvent event) {
        String privateKey = (String) event.getJSON().get(JSONCore.CORE.PRIVATE_KEY);
        if(this.baseServiceSettings.getUserRegistry().containsPrivateKey(privateKey)) {
            this.handle(event);
        }else {
            logger.error("PrivateKey not recognized");
        }
    }

    @Override
    public abstract void handle(ServerEvent obj);
}
