/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.dao;

import br.uff.dam.mysql.connection.DBUtil;
import br.uff.dam.mysql.connection.MysqlConnectionFactory;
import br.uff.dam.mysql.model.EventWeb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Logger;

/**
 *
 * @author thiago
 */
public class EventDAO {

    private Connection connection;
    private PreparedStatement statement;
    private Logger logger;

    public void insertEvent(EventWeb event) throws SQLException {
        logger = Logger.getLogger("EventDAO.insertEvent()");

        String query = "insert into event (timestamp, connection_id, text, event_type_id, client_address, server_address, tables, columns, timestamp_in_miliseconds) values (?,?,?,?,?,?,?,?,?)";

        ResultSet generatedKeys = null;
        try {

            connection = MysqlConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, new java.sql.Timestamp(event.getTimestamp().getTime()));
            if(event.getConnectionWebId() != 0){
                statement.setLong(2, event.getConnectionWebId());
            }else{
                statement.setNull(2, Types.NULL);
            }
            statement.setString(3, event.getText());

            if(event.getEventTypeId() != null){
                statement.setLong(4, event.getEventTypeId());
            } else {
                statement.setNull(4, Types.NULL);
            }

            statement.setString(5, event.getClientAddress());
            statement.setString(6, event.getServerAddress());
            statement.setString(7, event.getTables());
            statement.setString(8, event.getColumns());
            statement.setLong(9, event.getTimestampInMiliseconds());


            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating event failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                event.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating event failed, no generated key obtained.");
            }


        } finally {
            DBUtil.close(generatedKeys);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
    }
}
