package de.github.GSGJ.com;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kojy on 18.06.2017.
 */
public class MultipleServerManager {
    private List<Server> serverList;

    public MultipleServerManager(){
        this(new LinkedList<>());
    }

    public MultipleServerManager(List<Server> serverList){
        if(serverList == null){
            this.serverList = new LinkedList<>();
        }else {
            this.serverList = serverList;
        }
    }

    public void addServer(Server server){
        this.serverList.add(server);
    }

    //TODO add more stuff for multiple severs like port verification and single ip

}
