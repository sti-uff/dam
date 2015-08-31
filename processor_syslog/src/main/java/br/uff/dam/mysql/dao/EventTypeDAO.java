/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.dao;

import br.uff.dam.mysql.connection.DBUtil;
import br.uff.dam.mysql.connection.MysqlConnectionFactory;
import br.uff.dam.mysql.model.EventTypeWeb;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author thiago
 */
public class EventTypeDAO {

    private Connection connection;
    private Statement statement;
    private Logger logger;

    public Map<String, EventTypeWeb> getMapAllEventTypes() throws SQLException {
        Map<String, EventTypeWeb> allEventTypes = new HashMap<>();

        logger = Logger.getLogger("EventTypeDAO.getAllEventTypes()");

        String query = "select id, description, name, regex_pattern from event_type";

        ResultSet rs = null;
        try {

            connection = MysqlConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                EventTypeWeb eventType = new EventTypeWeb();
                eventType.setId(rs.getLong("id"));
                if (rs.getString("description") != null) {
                    eventType.setDescription(rs.getString("description").toUpperCase());
                }
                if (rs.getString("name") != null) {
                    eventType.setName(rs.getString("name").toUpperCase());
                }
                eventType.setName(rs.getString("name").toUpperCase());
                eventType.setRegexPattern(rs.getString("regex_pattern"));
                allEventTypes.put(eventType.getRegexPattern(), eventType);
            }

        } finally {
            DBUtil.close(rs);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return allEventTypes;
    }

    public List<String> getListAllEventTypes() throws SQLException {
        List<String> allEventTypes = new ArrayList<>();

        logger = Logger.getLogger("EventTypeDAO.getListAllEventTypes()");

        String query = "select name from event_type";

        ResultSet rs = null;
        try {

            connection = MysqlConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                allEventTypes.add(rs.getString("name").toUpperCase());
            }

        } finally {
            DBUtil.close(rs);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return allEventTypes;
    }
}
