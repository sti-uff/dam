/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.dao;

import br.uff.dam.mysql.connection.DBUtil;
import br.uff.dam.mysql.connection.MysqlConnectionFactory;
import br.uff.dam.mysql.model.DatabaseInstanceWeb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author 01245189158
 */
public class DatabaseInstanceWebDAO {

    private Connection connection;
    private Statement statement;
    private Logger logger;

    public DatabaseInstanceWeb getDatabaseInstanceWeb(String AUTH_INSTANCENAME, String ip) throws SQLException {
        DatabaseInstanceWeb databaseInstanceWeb = null;

        logger = Logger.getLogger("DatabaseInstanceDAO.getDatabaseInstanceWeb()");

        String query = "select id, ip, name from database_instance where name='" + AUTH_INSTANCENAME + "' ";

        ResultSet rs = null;
        try {

            connection = MysqlConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            
            if (rs.next()) {
                databaseInstanceWeb = new DatabaseInstanceWeb();
                databaseInstanceWeb.setId(rs.getLong("id"));
                databaseInstanceWeb.setIp(rs.getString("ip"));
                databaseInstanceWeb.setName(rs.getString("name"));
            }else{
                query = "insert into database_instance (ip, name) values (?, ?)";
                ResultSet generatedKeys = null;
                connection = MysqlConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, ip);
                preparedStatement.setString(2, AUTH_INSTANCENAME);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating DatabaseInstance failed, no rows affected.");
                }

                generatedKeys = preparedStatement.getGeneratedKeys();
                databaseInstanceWeb = new DatabaseInstanceWeb();
                databaseInstanceWeb.setIp(ip);
                databaseInstanceWeb.setName(AUTH_INSTANCENAME);
                if (generatedKeys.next()) {
                    databaseInstanceWeb.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating DatabaseInstance failed, no generated key obtained.");
                }
            }



        }catch(Exception e){
            System.out.println("Erro DatabaseInstance: "+e.getMessage());
        } finally {
            DBUtil.close(rs);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return databaseInstanceWeb;
    }
}
