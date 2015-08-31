/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.dao;

import br.uff.dam.mysql.connection.DBUtil;
import br.uff.dam.mysql.connection.MysqlConnectionFactory;
import br.uff.dam.mysql.model.DatabaseUserWeb;
import br.uff.dam.model.InProcessingRawData;
import br.uff.sti.dam.dao.RawDataService;
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
public class DatabaseUserWebDAO {

    private Connection connection;
    private Statement statement;
    private Logger logger;

    public DatabaseUserWeb getDatabaseUserWeb(String name, InProcessingRawData inProcessingRawData) throws SQLException {
        DatabaseUserWeb databaseUserWeb = null;

        logger = Logger.getLogger("DatabaseInstanceDAO.getDatabaseInstanceWeb()");

        String query = "select id, name from database_user where name='" + name + "'";

        ResultSet rs = null;
        try {

            connection = MysqlConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            if (rs.next()) {
                Long id = rs.getLong("id");
                databaseUserWeb = new DatabaseUserWeb();
                databaseUserWeb.setId(id);
                databaseUserWeb.setName(rs.getString("name"));
            } else {
                if (inProcessingRawData != null) {
                    RawDataService.getInstance().createGenerateNewLogonRawData(inProcessingRawData);
                }
                query = "insert into database_user (name) values (?)";
                ResultSet generatedKeys = null;
                connection = MysqlConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, name);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating DatabaseUser failed, no rows affected.");
                }

                generatedKeys = preparedStatement.getGeneratedKeys();
                databaseUserWeb = new DatabaseUserWeb();
                databaseUserWeb.setName(name);
                if (generatedKeys.next()) {
                    databaseUserWeb.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating DatabaseUser failed, no generated key obtained.");
                }
            }

        } catch (Exception e) {
            System.out.println("Erro DatabaseUser: " + e.getMessage());
        } finally {
            DBUtil.close(rs);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return databaseUserWeb;
    }
}
