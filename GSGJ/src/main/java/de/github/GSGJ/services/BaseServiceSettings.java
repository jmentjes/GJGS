package de.github.GSGJ.services;

import de.github.GSGJ.API.service.GSGJServiceRegistry;
import de.github.GSGJ.services.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * Created by Kojy on 17.06.2017.
 */
public class BaseServiceSettings {
    private static Logger logger = LoggerFactory.getLogger(BaseServiceSettings.class);
    private GSGJServiceRegistry serviceRegistry;
    private UserRegistry userRegistry;
    private GameRegistry gameRegistry;
    private LobbyRegistry lobbyRegistry;
    private ChatRegistry chatRegistry;
    private DatabaseRegistry databaseRegistry;

    public BaseServiceSettings(GSGJServiceRegistry serviceRegistry,
                               UserRegistry userRegistry,
                               GameRegistry gameRegistry,
                               LobbyRegistry lobbyRegistry,
                               ChatRegistry chatRegistry,
                               DatabaseRegistry databaseRegistry) {
        this.serviceRegistry = serviceRegistry;
        this.userRegistry = userRegistry;
        this.gameRegistry = gameRegistry;
        this.lobbyRegistry = lobbyRegistry;
        this.chatRegistry = chatRegistry;
        this.databaseRegistry = databaseRegistry;
    }

    public GSGJServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

    public UserRegistry getUserRegistry() {
        return userRegistry;
    }

    public GameRegistry getGameRegistry() {
        return gameRegistry;
    }

    public LobbyRegistry getLobbyRegistry() {
        return lobbyRegistry;
    }

    public ChatRegistry getChatRegistry() {
        return chatRegistry;
    }

    public DatabaseRegistry getDatabaseRegistry() {
        return databaseRegistry;
    }

    /**
     * Generates the mapList by using reflection api to get declared attributes
     * @return list containing all attribute field[][]
     */
    public LinkedList<Registry> getList() {
        LinkedList<Registry> list = new LinkedList<>();
        Field[] fields = BaseServiceSettings.class.getDeclaredFields();
        for (Field field : fields){
            if(Registry.class.isAssignableFrom(field.getType())){
                try {
                    Registry registry = (Registry) field.get(this);
                    list.add(registry);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return list;
    }
}
