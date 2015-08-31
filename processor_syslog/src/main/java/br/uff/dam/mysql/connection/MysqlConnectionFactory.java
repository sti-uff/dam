package br.uff.dam.mysql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class MysqlConnectionFactory {
    //static reference to itself
    private static MysqlConnectionFactory instance = new MysqlConnectionFactory();
    public static final String URL = "jdbc:mysql://200.20.0.219:3306/dam";
    public static final String USER = "root";
    public static final String PASSWORD = "tzzk7662";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    private Logger logger;

    private MysqlConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        logger = Logger.getLogger("MysqlConnectionFactory.createConnection()");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("ERROR: Unable to Connect to Database. " + e.getMessage() );
            System.exit(1);
        }
        return connection;
    }

    public static Connection getConnection() {
        return instance.createConnection();
    }
}