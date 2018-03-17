package ru.manunin.kedr.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Александр on 02.12.2017.
 */
public class DbConnector {

    private static String driver = "jdbc:mysql";
    private static String host = "localhost";
    private static String port = "3306";
    private static String dbName = "kedrbudget";
    private static Logger logger = LoggerFactory.getLogger("ru.manunin.kedr.db.DbConnector");

    public static Connection connection;


    private static void driverRegister() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            logger.error("Error of driver registration " + e.getMessage());
        }
    }

    public static Connection connect(String login, String pass) {

        String url = String.format("%s://%s:%s/%s", driver, host, port, dbName);

        try {
            driverRegister();
            connection = DriverManager.getConnection(url, login, pass);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Error of connecting " + e.getMessage());
        }

        return connection;
    }

    public static void close() {

        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Error of connection closing " + e.getMessage());
        }

    }

}
