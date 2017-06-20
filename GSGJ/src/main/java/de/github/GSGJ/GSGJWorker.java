package de.github.GSGJ;

import de.github.GSGJ.API.service.GSGJServiceRegistry;
import de.github.GSGJ.API.service.Service;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.API.worker.AbstractWorker;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseService;
import de.github.GSGJ.services.BaseServiceSettings;
import de.github.GSGJ.services.chatmanagement.ChatManagementService;
import de.github.GSGJ.services.gamemanagement.GameManagementService;
import de.github.GSGJ.services.lobbymanagement.LobbyManagementService;
import de.github.GSGJ.services.registry.ChatRegistry;
import de.github.GSGJ.services.registry.GameRegistry;
import de.github.GSGJ.services.registry.LobbyRegistry;
import de.github.GSGJ.services.registry.UserRegistry;
import de.github.GSGJ.services.requestmanagement.RequestManagementService;
import de.github.GSGJ.services.usermanagement.UserManagementService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kojy on 17.06.2017.
 */
public class GSGJWorker extends AbstractWorker<ServerEvent> implements GSGJServiceRegistry {
    private List<Service> services;
    private List<BaseService> baseServices;

    public GSGJWorker() {
        this.services = new LinkedList<>();
        this.baseServices = new LinkedList<>();
        initBaseServices();
    }

    private void initBaseServices() {
        BaseServiceSettings baseServiceSettings = new BaseServiceSettings(
                this,
                new UserRegistry(),
                new GameRegistry(),
                new LobbyRegistry(),
                new ChatRegistry()
        );

        createThread(new UserManagementService(baseServiceSettings));
        createThread(new LobbyManagementService(baseServiceSettings));
        createThread(new GameManagementService(baseServiceSettings));
        createThread(new ChatManagementService(baseServiceSettings));
        createThread(new RequestManagementService(baseServiceSettings));
    }

    private void createThread(AbstractBaseService service) {
        Thread t = new Thread(service);
        t.setDaemon(true);
        t.start();

        this.baseServices.add(service);
    }

    @Override
    public void handle(ServerEvent obj) {
        logger.debug("Incoming message {}", obj);

        String serviceID = (String) obj.getJSON().get("service");
        for (BaseService baseService : baseServices) {
            if(baseService.getID().equals(serviceID)) {
                baseService.handleEvent(obj);
                return;
            }
        }

        for (Service service : services){
            if(service.getID().equals(serviceID)){
                service.handleEvent(obj);
            }
        }
    }


    @Override
    public Service getServiceByID(String id) {
        for (Service service : services) {
            if (service.getID().equals(id)) {
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

        for (Service service : services) {
            if (service.getID().equals(id)) {
                toRemove = service;
                break;
            }
        }

        if (toRemove != null) {
            services.remove(toRemove);
        }
    }

    @Override
    public Service getBaseService(String id) {
        for (BaseService baseService : baseServices) {
            if (baseService.getID().equals(id)) {
                return baseService;
            }
        }
        return null;
    }
}
