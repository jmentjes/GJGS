package servertest.com;

import org.json.simple.JSONObject;

/**
 * Created by Kojy on 26.06.2017.
 */
public interface Transceiver {
    void send(JSONObject object);

    void receive(JSONObject jsonObject);

    void start();

    void stop();

}
