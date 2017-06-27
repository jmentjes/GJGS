package de.github.GSGJ.services.registry;

import de.github.GSGJ.database.entities.User;
import de.github.GSGJ.services.lobbymanagement.Lobby;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kojy on 17.06.2017.
 */
public class LobbyRegistry implements Registry {
    private List<Lobby> lobbyList;

    public LobbyRegistry(){
        lobbyList = new ArrayList<>();
    }

    public void addLobby(Lobby lobby){
        this.lobbyList.add(lobby);
    }

    public Lobby removeLobby(Lobby lobby){
        return this.removeLobby(lobby);
    }

    public Lobby removeLobby(int id){
        return this.removeLobby(new Lobby(id));
    }

    public void updateLobby(Lobby lobby){
        this.lobbyList.remove(lobby);
        this.lobbyList.add(lobby);
    }

    public boolean contains(Lobby lobby){
        return lobbyList.contains(lobby);
    }

    public boolean contains(int id){
        return lobbyList.contains(new Lobby(id));
    }

    public boolean removeUser(User user){
        boolean bool = false;
        for (Lobby lobby : this.lobbyList){
            if(removeUser(lobby,user)){
                bool = true;
            }
        }
        return bool;
    }

    public boolean removeUser(Lobby lobby, User user){
        Lobby listLobby = lobbyList.remove(lobbyList.indexOf(lobby));
        if (listLobby == null){
            return false;
        }
        List<User> list = listLobby.getUserList();
        User owner = listLobby.getOwner();

        if(!list.remove(user)){
            if(owner.equals(user)){
                if(listLobby.getUserList().size() == 0){
                    this.removeLobby(listLobby);
                }else {
                    User newOwner = listLobby.getUserList().remove(0);
                    listLobby.setOwner(newOwner);
                }
            }else {
                this.lobbyList.add(listLobby);
                return false;
            }
        }

        listLobby.setUserList(list);
        this.addLobby(listLobby);
        return true;
    }

    public void changeHost(Lobby lobby, User newOwner){
        Lobby listLobby = this.lobbyList.remove(lobbyList.indexOf(lobby));
        List<User> users = listLobby.getUserList();
        users.remove(newOwner);
        listLobby.setUserList(users);
        listLobby.setOwner(newOwner);
        this.lobbyList.add(listLobby);
    }

    public Lobby getLobby(Lobby lobby){
        return this.getLobby(this.lobbyList.indexOf(lobby));
    }

    public Lobby getLobby(int index){
        return this.lobbyList.get(index);
    }

    public void joinLobby(User user, Lobby lobby){
        Lobby listLobby = this.lobbyList.remove(this.lobbyList.indexOf(lobby));
        List<User> users = listLobby.getUserList();
        users.add(user);
        listLobby.setUserList(users);
        this.lobbyList.add(listLobby);
    }

    @Override
    public boolean logoutUser(User user) {
        removeUser(user);
        return true;
    }
}
