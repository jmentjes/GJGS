package de.github.GSGJ.structure;

import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseService;
import de.github.GSGJ.API.service.Service;
import de.github.GSGJ.API.worker.AbstractWorker;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.service.GSGJServiceRegistry;
import de.github.GSGJ.services.chatmanagement.ChatManagementService;
import de.github.GSGJ.services.gamemanagement.GameManagementService;
import de.github.GSGJ.services.lobbymanagement.LobbyManagementService;
import de.github.GSGJ.services.requestmanagement.RequestManagementService;
import de.github.GSGJ.services.usermanagement.UserManagementService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kojy on 17.06.2017.
 */
public class GSGJWorker extends AbstractWorker<ServerEvent> implements GSGJServiceRegistry{
    private List<Service> services;
    private List<AbstractBaseService> baseServices;

    public GSGJWorker(){
        this.services = new LinkedList<>();
        this.baseServices = new LinkedList<>();
        initBaseServices();
    }

    private void initBaseServices(){
        createThread(new UserManagementService(this));
        createThread(new LobbyManagementService(this));
        createThread(new GameManagementService(this));
        createThread(new ChatManagementService(this));
        createThread(new RequestManagementService(this));
    }

    private void createThread(AbstractBaseService service){
        Thread t = new Thread(service);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void handle(ServerEvent obj) {
        logger.debug("Incoming message {}",obj);
        notifyBaseServices(obj);
        notifyServices(obj);
    }

    private void notifyBaseServices(ServerEvent event){
        for (BaseService baseService : baseServices){
            baseService.handleEvent(event);
        }
    }

    private void notifyServices(ServerEvent event) {
        for (Service service : services) {
            service.handleEvent(event);
        }
    }

    @Override
    public Service getServiceByID(String id) {
        for (Service service : services){
            if (service.getID().equals(id)){
                return service;
            }
        }
        return null;
    }

    @Override
    public void addService(Service service) {
        this.services.add(service);
    }

    @Override
    public void removeService(Service service) {
        this.services.remove(service);
    }

    @Override
    public void removeService(String id) {
        Service toRemove = null;

        for (Service service : services){
            if(service.getID().equals(id)){
                toRemove = service;
                break;
            }
        }

        if(toRemove != null){
            services.remove(toRemove);
        }
    }

    @Override
    public Service getBaseService(String id){
        for (BaseService baseService : baseServices){
            if(baseService.getID().equals(id)){
                return baseService;
            }
        }
        return null;
    }
}
