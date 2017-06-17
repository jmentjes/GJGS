package de.github.GSGJ.API.service;

import de.github.GSGJ.API.service.Service;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface GSGJServiceRegistry {
    Service getServiceByID(String id);
    void addService(Service service);
    void removeService(Service service);
    void removeService(String id);
    Service getBaseService(String id);
}
