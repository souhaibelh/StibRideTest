package mvp.model.db;
import config.ConfigManager;
import java.sql.*;

/**
 * This class manages one instance of the database
 */
public class DBManager {
    private Connection connection;

    private DBManager() {
        String url = "jdbc:sqlite:" + ConfigManager.getInstance().getProperty("db.url");
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
        }
    }

    /**
     * @return the database connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @return the instance of DBManager that is created in the internal class
     */
    public static DBManager getInstance() {
        return DBManagerHolder.instance;
    }

    /**
     * Private internal class only DBManager has access to
     */
    private static class DBManagerHolder {
        private static DBManager instance = new DBManager();
    }
}
