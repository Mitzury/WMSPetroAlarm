package Mitzury.SQL;

import java.util.HashMap;
import java.util.Map;

public class ConnectionData {
    public static class ConnectionDataInner {
        private final String url;
        private final String user;
        private final String password;

        public ConnectionDataInner(String url, String user, String password) {
            if (url == null || user == null || password == null) {
                throw new IllegalArgumentException("Arguments cannot be null");
            }
            this.url = url;
            this.user = user;
            this.password = password;
        }
        // Getters for url, user, and password
        public String getUrl() {
            return url;
        }
        public String getUser() {
            return user;
        }
        public String getPassword() {
            return password;
        }
    }

    private static final Map<String, ConnectionDataInner> connectionDataMap = new HashMap<>();

    static {
        connectionDataMap.put("MRM", new ConnectionDataInner(
                "_DB_PATH`",
                "_DB_USER",
                "_DB_PASS"
        ));
        connectionDataMap.put("DMT", new ConnectionDataInner(
                "_DB_PATH`",
                "_DB_USER",
                "_DB_PASS"
        ));
        connectionDataMap.put("MRMtest", new ConnectionDataInner(
                "_DB_PATH`",
                "_DB_USER",
                "_DB_PASS"
        ));
    }

    public static ConnectionDataInner getConnectionData(String id) {
        return connectionDataMap.get(id);
    }
}