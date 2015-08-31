package br.uff.dam.mysql.dao;

import br.uff.dam.mysql.connection.DBUtil;
import br.uff.dam.mysql.connection.MysqlConnectionFactory;
import br.uff.dam.mysql.model.AlertDecoratedWeb;
import br.uff.dam.mysql.model.AlertWeb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AlertDAO {

    private Connection connection;
    private PreparedStatement prepareStatement;
    private Statement statement;
    private Logger logger;

    public AlertDAO() {
    }

    public void insertAlert(AlertWeb alert) throws SQLException {
        String query = "insert into alert (timestamp, criticality_id, event_id, rule_id, email_sent) values (?, ?, ?, ?, ?)";
        ResultSet generatedKeys = null;
        try {
            connection = MysqlConnectionFactory.getConnection();
            prepareStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setTimestamp(1, new java.sql.Timestamp(alert.getTimestamp().getTime()));
            prepareStatement.setLong(2, alert.getCriticalityId());
            prepareStatement.setLong(3, alert.getEventId());
            prepareStatement.setLong(4, alert.getRuleId());
            
            // Seta 0 no envio de email pois outro processamento é responsável pelo envio de email
            prepareStatement.setLong(5, 0);

            int affectedRows = prepareStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating event failed, no rows affected.");
            }

            generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                alert.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating event failed, no generated key obtained.");
            }
        } finally {
            DBUtil.close(generatedKeys);
            DBUtil.close(prepareStatement);
            DBUtil.close(connection);
        }
    }


    public List<AlertDecoratedWeb> getAlertsDecoratedNotEmailSent() throws SQLException {
        List<AlertDecoratedWeb> allAlertsDecorated = new ArrayList<>();

        logger = Logger.getLogger("RuleDAO.getAllRules()");

        String query = "select r.id, a.id, "
		+ "(select GROUP_CONCAT(distinct d.name SEPARATOR ', ') "
		+ " from rule r "
		+ " left join rule_database_user ru on ru.rule_id = r.id "
		+ " left join database_user d on d.id = ru.database_user_id "
		+ " where a.rule_id = r.id " 
                + " group by r.id)   as users_rule, "
		+ " (select GROUP_CONCAT(distinct e.name SEPARATOR ', ') "
		+ " from rule r "
		+ " left join rule_event_type re on re.rule_id = r.id "
		+ " left join event_type e on e.id = re.event_type_id "
		+ " where a.rule_id = r.id "
                + " group by r.id)   as events_rule, "
		+ " et.name as event, "
                + " e.client_address, "
		+ " e.timestamp, "
		+ " r.ip_list, "
		+ " r.email_list, "
		+ " e.text, " 
		+ " db.name, "
                + " ct.description "
                + " from alert a "
                + " inner join event e on e.id = a.event_id "
                + " left join event_type et on et.id = e.event_type_id "
                + " inner join rule r on r.id = a.rule_id "
                + " inner join connection c on c.id = e.connection_id "
                + " left join database_user db on db.id = c.database_user_id "
                + " inner join criticality ct on ct.id = r.criticality_id "
                + " where a.email_sent = false";



        ResultSet rs = null;
        try {

            connection = MysqlConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {

                AlertDecoratedWeb alertDecorated = new AlertDecoratedWeb();

                alertDecorated.setId(rs.getLong("a.id"));
                alertDecorated.setRuleId(rs.getLong("r.id"));
                alertDecorated.setClientAddress(rs.getString("e.client_address"));
                alertDecorated.setEmailList(rs.getString("r.email_list"));
                alertDecorated.setEvent(rs.getString("event"));
                alertDecorated.setEventRules(rs.getString("events_rule"));

                if((rs.getString("events_rule") == null) || (rs.getString("events_rule").equals(""))){
                    alertDecorated.setEventRules("*");
                } else {
                    alertDecorated.setEventRules(rs.getString("events_rule"));
                }

                if((rs.getString("r.ip_list") == null) || (rs.getString("r.ip_list").equals("")) ){
                    alertDecorated.setIpList("*");
                } else {
                    alertDecorated.setIpList(rs.getString("r.ip_list"));
                }

                
                alertDecorated.setQuery(rs.getString("e.text"));
                alertDecorated.setTimestampEvent(rs.getTimestamp("e.timestamp"));
                alertDecorated.setUser(rs.getString("db.name"));
                alertDecorated.setUserRules(rs.getString("users_rule"));
                alertDecorated.setCriticalityDescription(rs.getString("ct.description"));

                allAlertsDecorated.add(alertDecorated);

            }


        } finally {
            DBUtil.close(rs);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return allAlertsDecorated;
    }


    public void updateSentEmailForAlertDecorated(List<AlertDecoratedWeb> listAlert) throws SQLException{

        StringBuffer ids = new StringBuffer();

        for (AlertDecoratedWeb alertDecoratedWeb : listAlert) {
            ids.append(alertDecoratedWeb.getId());
            ids.append(",");
        }

        ids.deleteCharAt(ids.length()-1);

        String query = "update alert set email_sent = true where id in (" + ids + ")";


        ResultSet generatedKeys = null;
        try {
            connection = MysqlConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            int affectedRows = statement.executeUpdate(query);
            if (affectedRows == 0) {
                throw new SQLException("Creating event failed, no rows affected.");
            }

        } finally {
            DBUtil.close(generatedKeys);
            DBUtil.close(prepareStatement);
            DBUtil.close(connection);
        }

    }
}
