package servertest.com;

import org.json.simple.JSONObject;

/**
 * Created by Kojy on 26.06.2017.
 */
public abstract class AbstractTransceiver implements Transceiver {

    public abstract void send(JSONObject object);

    public abstract void receive(JSONObject jsonObject);
}
