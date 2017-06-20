package de.github.GSGJ.services.usermanagement.model.events;

import de.github.GSGJ.API.service.Event;
import de.github.GSGJ.API.usermanagement.User;
import org.json.simple.JSONObject;

/**
 * Created by Kojy on 17.06.2017.
 */
public class UserEvent implements Event {
    private User user;
    private JSONObject object;

    @Override
    public Class<Event> getEventClass() {
        try {
//            return (Class<Event>) this.getClass();
        } catch(ClassCastException exc) {
            exc.printStackTrace();
        }
        return null;
    }
}
