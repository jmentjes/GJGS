package de.github.GSGJ.services.lobbymanagement;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.structure.ServerEvent;
import de.github.GSGJ.database.entities.User;
import de.github.GSGJ.services.AbstractBaseService;
import de.github.GSGJ.services.BaseServiceSettings;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kojy on 17.06.2017.
 */
public class LobbyManagementService extends AbstractBaseService {
    private AtomicInteger atomicInteger;

    public LobbyManagementService(BaseServiceSettings baseServiceSettings) {
        super(baseServiceSettings);
        this.atomicInteger = new AtomicInteger();
    }

    @Override
    public void handle(ServerEvent obj) {
        //TODO decide whether to add or not to add a lobbychat or to let the client handle it by himself
        JSONObject jsonObject = obj.getJSON();
        String subject = (String) jsonObject.get(JSONCore.CORE.SUBJECT.getId());
        logger.debug("Incoming message with subject {}", subject);

        User user = User.createUserFromJson(obj.getJSON());
        String privateKey = (String) jsonObject.get(JSONCore.CORE.PRIVATE_KEY.getId());
        if(!baseServiceSettings.getUserRegistry().containsPrivateKey(privateKey)){
            jsonObject.put(JSONCore.CORE.SUCCESS.getId(), "false");
            jsonObject.put(JSONCore.CORE.ERROR_MESSAGE.getId(),"Private key is wrong");
        }else {
            switch (subject){
                case "create-lobby":
                    createLobby(user, jsonObject);
                    break;
                case "join-lobby":
                    joinLobby(user, jsonObject);
                    break;
                case "leave-lobby":
                    leaveLobby(user, jsonObject);
                    break;
                case "start-game":
                    startGame(jsonObject);
                    break;
                default:
                    logger.error("Subject not recognized: " + subject);
                    jsonObject.put("error-message", "Subject not recognized");
                    break;
            }
        }
        obj.getConnection().send(jsonObject);
    }

    private void createLobby(User owner, JSONObject jsonObject){
        Lobby lobby = new Lobby(generateLobbyID(),owner);
        this.baseServiceSettings.getLobbyRegistry().addLobby(lobby);
    }

    private void joinLobby(User user, JSONObject jsonObject){
        int id = Integer.parseInt((String) jsonObject.get(JSONCore.CORE_LOBBY.LOBBY_ID.getId()));
        Lobby lobby = new Lobby(id);
        this.baseServiceSettings.getLobbyRegistry().joinLobby(user,lobby);
    }

    private void leaveLobby(User user, JSONObject jsonObject){
        int id = Integer.parseInt((String) jsonObject.get(JSONCore.CORE_LOBBY.LOBBY_ID.getId()));
        Lobby lobby = new Lobby(id);

        this.baseServiceSettings.getLobbyRegistry().removeUser(lobby,user);
    }

    private void startGame(JSONObject jsonObject){
        //TODO implement start game
    }

    private int generateLobbyID(){
        return atomicInteger.getAndIncrement();
    }
}
