package de.github.GSGJ.services;

import de.github.GSGJ.API.service.GSGJServiceRegistry;
import de.github.GSGJ.services.registry.ChatRegistry;
import de.github.GSGJ.services.registry.GameRegistry;
import de.github.GSGJ.services.registry.LobbyRegistry;
import de.github.GSGJ.services.registry.UserRegistry;

/**
 * Created by Kojy on 17.06.2017.
 */
public class BaseServiceSettings {
    private GSGJServiceRegistry serviceRegistry;
    private UserRegistry userRegistry;
    private GameRegistry gameRegistry;
    private LobbyRegistry lobbyRegistry;
    private ChatRegistry chatRegistry;

    public BaseServiceSettings(GSGJServiceRegistry serviceRegistry,
                               UserRegistry userRegistry,
                               GameRegistry gameRegistry,
                               LobbyRegistry lobbyRegistry,
                               ChatRegistry chatRegistry) {
        this.serviceRegistry = serviceRegistry;
        this.userRegistry = userRegistry;
        this.gameRegistry = gameRegistry;
        this.lobbyRegistry = lobbyRegistry;
        this.chatRegistry = chatRegistry;
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
}
