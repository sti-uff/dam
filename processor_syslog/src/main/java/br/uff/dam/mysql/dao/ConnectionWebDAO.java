/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.dao;

import br.uff.dam.mysql.connection.DBUtil;
import br.uff.dam.mysql.connection.MysqlConnectionFactory;
import br.uff.dam.mysql.model.ConnectionWeb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 *
 * @author 01245189158
 */
public class ConnectionWebDAO {
    
    private Connection connection;
    private PreparedStatement statement;

    public ConnectionWebDAO() {
    }

    public ConnectionWeb insertConnectionWeb(ConnectionWeb connectionWeb) throws SQLException{
        //String query = "insert into alert (timestamp, criticality_id, event_id, rule_id) values (?, ?, ?, ?)";
        //String query = "insert into connection (client_address, server_address, connection_end, connection_start, database_instance_id, database_user_id) values (?, ?, ?, ?, ?, ?)";
        String query = "insert into connection (client_address, server_address, connection_start, database_instance_id, database_user_id) values (?, ?, ?, ?, ?)";
        ResultSet generatedKeys = null;
        try {
            connection = MysqlConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, connectionWeb.getClientAddress());
            statement.setString(2, connectionWeb.getServerAddress());
            //statement.setInt(2, connectionWeb.getClientPort());
            
            statement.setTimestamp(3, new java.sql.Timestamp(connectionWeb.getConnectionStart().getTime()));
            if(connectionWeb.getDatabaseInstanceId() != 0l ){
                statement.setLong(4, connectionWeb.getDatabaseInstanceId());
            }else{
                statement.setNull(4, Types.NULL);
            }
            statement.setLong(5, connectionWeb.getDatabaseUserId());
            
            
            //statement.setLong(6, connectionWeb.getDatabaseUser() == null ? 0 : connectionWeb.getDatabaseUser().getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating connection failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                connectionWeb.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating connection failed, no generated key obtained.");
            }
        }catch(Exception e){
            System.out.println("Erro ConnectionWeb: "+e.getMessage());
        } finally {
            DBUtil.close(generatedKeys);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return connectionWeb;
    }

    public ConnectionWeb updateConnectionWeb(ConnectionWeb connectionWeb) throws SQLException{
        String query = "update connection set database_instance_id = ? where id= ?";
        ResultSet generatedKeys = null;
        try {
            connection = MysqlConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, connectionWeb.getDatabaseInstanceId());
            statement.setLong(2, connectionWeb.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating connection failed, no rows affected.");
            }
            
        }catch(Exception e){
            System.out.println("Erro ConnectionWeb: "+e.getMessage());
        } finally {
            DBUtil.close(generatedKeys);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return connectionWeb;
    }

}
