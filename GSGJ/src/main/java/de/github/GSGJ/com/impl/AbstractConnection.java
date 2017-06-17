package de.github.GSGJ.com.impl;

import de.github.GSGJ.API.structure.Connection;

/**
 * Created by Kojy on 18.06.2017.
 */
public abstract class AbstractConnection implements Connection {

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Connection){
            Connection otherConnection = (Connection) obj;
            return otherConnection.getAddress().equals(this.getAddress());
        }

        return false;
    }
}
