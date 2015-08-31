package br.uff.dam.mysql.dao;

import br.uff.dam.mysql.connection.DBUtil;
import br.uff.dam.mysql.connection.MysqlConnectionFactory;
import br.uff.dam.mysql.model.StatisticWeb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StatisticDAO {

    private Connection connection;
    private PreparedStatement statement;

    public StatisticDAO() {
    }

    public void insertStatistic(StatisticWeb statistic) throws SQLException {
        String query = "insert into statistic_web (statistic_type_id, value, created_at, updated_at) values (?, ?, ?, ?)";
        try {
            connection = MysqlConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, statistic.getStatisticTypeId());
            statement.setDouble(2, statistic.getValue());
            statement.setTimestamp(3, new java.sql.Timestamp(statistic.getCreatedAt().getTime()));
            statement.setTimestamp(4, new java.sql.Timestamp(statistic.getUpdatedAt().getTime()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating statistic failed, no rows affected.");
            }

        } finally {
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
    }

    public void updateStatistic(StatisticWeb statistic) throws SQLException{
        String query = "update statistic_web set value = ? where  statistic_type_id = ?";
        try {
            connection = MysqlConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, statistic.getValue());
            statement.setLong(2, statistic.getStatisticTypeId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                insertStatistic(statistic);
            }

        } finally {
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
    }
}
