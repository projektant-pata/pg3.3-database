package cz.spse.bajer.core;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DatabaseConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static int MAXIMUM_POOL_SIZE;
    private static int MINIMUM_IDLE;
    private static int IDLE_TIMEOUT;
    private static int CONNECTION_TIMEOUT;

    private static HikariDataSource dataSource;

    static {
        try {
            loadConfigFromXML("/home/projektant-pata/Projekty/Školní/Java/Database/src/main/java/cz/spse/bajer/config/dbconfig.xml");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
            config.setMinimumIdle(MINIMUM_IDLE);
            config.setIdleTimeout(IDLE_TIMEOUT);
            config.setConnectionTimeout(CONNECTION_TIMEOUT);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Nepodařilo se načíst konfiguraci databáze", e);
        }
    }

    private static void loadConfigFromXML(String configFile) throws Exception {
        File file = new File(configFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        // Načtení údajů pro připojení
        Element connection = (Element) doc.getElementsByTagName("connection").item(0);
        URL = getElementValue(connection, "url");
        USER = getElementValue(connection, "user");
        PASSWORD = getElementValue(connection, "password");

        // Načtení konfigurace poolu
        Element pool = (Element) doc.getElementsByTagName("pool").item(0);
        MAXIMUM_POOL_SIZE = Integer.parseInt(getElementValue(pool, "maximum-pool-size"));
        MINIMUM_IDLE = Integer.parseInt(getElementValue(pool, "minimum-idle"));
        IDLE_TIMEOUT = Integer.parseInt(getElementValue(pool, "idle-timeout"));
        CONNECTION_TIMEOUT = Integer.parseInt(getElementValue(pool, "connection-timeout"));
    }

    private static String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    public static Connection getConnection() {
        try {
            Connection conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}