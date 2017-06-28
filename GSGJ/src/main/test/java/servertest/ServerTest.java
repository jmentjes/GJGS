package servertest;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.GSGJ;
import org.json.simple.JSONObject;
import servertest.com.Transceiver;
import servertest.com.impl.TransceiverImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Lenovo on 28.06.2017.
 */
public class ServerTest {
    private static String username = "KARLFRANZ";
    private static String password = "KARLFRANZ";
    private static String email = "KARL@FRANZ.DE";
    private static String key = "";
    private static String userID = "";

    public static void receive(JSONObject jsonObject) {
        System.out.println(jsonObject.toJSONString());
        System.out.println("        SUCCESS: "+jsonObject.get(JSONCore.CORE.SUCCESS.getId()));
        System.out.println("        ERROR_MESSAGE: "+jsonObject.get(JSONCore.CORE.ERROR_MESSAGE.getId()));
        if(jsonObject.get(JSONCore.CORE.PRIVATE_KEY.getId()) != null){
            key = (String) jsonObject.get(JSONCore.CORE.PRIVATE_KEY.getId());
        }

        if (jsonObject.get(JSONCore.CORE.USER_ID.getId()) != null){
            userID = (String) jsonObject.get(JSONCore.CORE.USER_ID.getId());
        }
    }

    public static void main(String... args){
        GSGJ.main(args);
        try {
            Transceiver transceiver = new TransceiverImpl(true, InetAddress.getByName("localhost"),8081);
            waitOneSecond();
            sendRegister(transceiver);
            waitOneSecond();
            sendLogin(transceiver);
            waitOneSecond();
            sendLogout(transceiver);
            waitOneSecond();
            sendLogin(transceiver);
            waitOneSecond();
            sendDelete(transceiver);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    private static void waitOneSecond(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendRegister(Transceiver transceiver){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSONCore.CORE.SERVICE.getId(), "de.github.GSGJ.services.usermanagement.UserManagementService");
        jsonObject.put(JSONCore.CORE.SUBJECT.getId(), "register");
        jsonObject.put(JSONCore.CORE.USERNAME.getId(), username);
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.EMAIL.getId(),email);
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.PASSWORD.getId(), password);
        transceiver.send(jsonObject);
    }

    private static void sendLogin(Transceiver transceiver){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSONCore.CORE.SERVICE.getId(), "de.github.GSGJ.services.usermanagement.UserManagementService");
        jsonObject.put(JSONCore.CORE.SUBJECT.getId(), "login");
        jsonObject.put(JSONCore.CORE.USERNAME.getId(), username);
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.EMAIL.getId(),email);
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.PASSWORD.getId(), password);
        transceiver.send(jsonObject);
    }

    private static void sendDelete(Transceiver transceiver){

    }

    private static void sendLogout(Transceiver transceiver){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSONCore.CORE.SERVICE.getId(), "de.github.GSGJ.services.usermanagement.UserManagementService");
        jsonObject.put(JSONCore.CORE.SUBJECT.getId(), "logout");
        jsonObject.put(JSONCore.CORE.USERNAME.getId(), username);
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.EMAIL.getId(),email);
        jsonObject.put(JSONCore.CORE_USERMANAGEMENT.PASSWORD.getId(), password);
        jsonObject.put(JSONCore.CORE.PRIVATE_KEY.getId(),key);
        jsonObject.put(JSONCore.CORE.USER_ID.getId(),userID);
        transceiver.send(jsonObject);
    }
}
