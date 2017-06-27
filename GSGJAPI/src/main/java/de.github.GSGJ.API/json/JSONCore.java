package de.github.GSGJ.API.json;

/**
 * Created by Kojy on 26.06.2017.
 */
public class JSONCore {
    public enum CORE {
        SERVICE("service"), SUBJECT("subject"), USERNAME("username"), ERROR_MESSAGE("error-message"), PRIVATE_KEY("private-key"), SUCCESS("success"),
        USER_ID("user-id");

        String id;

        CORE(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }


    public enum CORE_MESSAGE {
        CHAT_ID("chat-id"), MESSAGE("chat-message");

        String id;

        CORE_MESSAGE(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

    }

    public enum CORE_GAME {
        GAME_ID("game-id");

        String id;

        CORE_GAME(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

    }

    public enum CORE_LOBBY {
        LOBBY_ID("lobby-id");

        String id;

        CORE_LOBBY(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    public enum CORE_REQUEST {

    }

    public enum CORE_USERMANAGEMENT {
        PASSWORD("password"), EMAIL("email");

        String id;

        CORE_USERMANAGEMENT(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }
}
