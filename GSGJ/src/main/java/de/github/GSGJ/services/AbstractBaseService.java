package de.github.GSGJ.services;

import de.github.GSGJ.API.service.GSGJServiceRegistry;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.worker.AbstractWorker;

/**
 * Created by Kojy on 17.06.2017.
 */
public abstract class AbstractBaseService extends AbstractWorker<ServerEvent> implements BaseService {
    protected GSGJServiceRegistry serviceRegistry;


    public AbstractBaseService(GSGJServiceRegistry serviceRegistry){
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public String getID() {
        return getClass().getName();
    }

    @Override
    public void handleEvent(ServerEvent event) {
        this.handle(event);
    }

    @Override
    public abstract void handle(ServerEvent obj);
}
