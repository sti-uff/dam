/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.dao;

import br.uff.dam.mysql.connection.DBUtil;
import br.uff.dam.mysql.connection.MysqlConnectionFactory;
import br.uff.dam.mysql.model.DatabaseUserWeb;
import br.uff.dam.mysql.model.EventTypeWeb;
import br.uff.dam.mysql.model.RuleWeb;
import br.uff.dam.mysql.model.RuleDatabaseUserWeb;
import br.uff.dam.mysql.model.RuleEventTypeWeb;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author thiago
 */
public class RuleDAO {

    private Connection connection;
    private Statement statement;
    private Logger logger;

    public List<RuleWeb> getAllRulesActivated() throws SQLException {
        List<RuleWeb> allRules = new ArrayList<>();

        logger = Logger.getLogger("RuleDAO.getAllRules()");

        String query = "(select r.id as rule_id, r.email_list as email_list, r.ip_list as ip_list, r.type as rule_type, d.name as user, e.name as event, c.description as criticality, c.id criticality_id, rg.id as group_id "
                + " from rule r "
                + " left join rule_database_user ru on ru.rule_id = r.id "
                + " left join database_user d on d.id = ru.database_user_id "
                + " left join rule_event_type re on re.rule_id = r.id "
                + " left join event_type e on e.id = re.event_type_id"
                + " left join criticality c on c.id = r.criticality_id "
                + " left join rule_group rg on rg.rule_id = r.id "
                + " where r.active = 1 "
                + " order by r.id, d.name, e.name "
                + ") "
                + " union "
                + " (select r.id as rule_id, r.email_list as email_list, r.ip_list as ip_list, r.type as rule_type, d2.name as user, e.name as event, c.description as criticality, c.id as criticality_id, rg.id as group_id "
                + " from rule r "
                + " left join rule_event_type re on re.rule_id = r.id "
                + " left join event_type e on e.id = re.event_type_id "
                + " left join criticality c on c.id = r.criticality_id "
                + " left join rule_group rg on rg.rule_id = r.id "
                + " inner join database_group dbg on dbg.id = rg.group_id "
                + " inner join group_database_user gdbu on gdbu.group_id = dbg.id "
                + " inner join database_user d2 on d2.id = gdbu.database_user_id "
                + " where r.active = 1 "
                + " order by r.id, d2.name, e.name "
                + " ) ";

        ResultSet rs = null;
        try {

            connection = MysqlConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            Long currentIdRule = 0l;
            Long previousIdRule = 0l;
            Set<RuleEventTypeWeb> setRuleEventType = new HashSet<>();
            Set<RuleDatabaseUserWeb> setRuleDataBaseUser = new HashSet<>();

            Set<String> databaseUserSet = new HashSet<>();
            Set<String> eventTypeRulesSet = new HashSet<>();
            RuleWeb r = new RuleWeb();


            while (rs.next()) {
                // necessário a verificação abaixo para descartar linhas de regras que apontam para um usuário null porém possuem algum grupo de usuários associados
                if ((rs.getString("user") == null) && (rs.getString("group_id") != null)) {
                    continue;
                } else {
                    currentIdRule = rs.getLong("rule_id");

                    if (currentIdRule != previousIdRule) {
                        if (!rs.isFirst()) {

                            for (Iterator<String> it = databaseUserSet.iterator(); it.hasNext();) {
                                String string = it.next();
                                RuleDatabaseUserWeb ruleDatabaseUser = new RuleDatabaseUserWeb();
                                DatabaseUserWeb dataBaseUser = new DatabaseUserWeb();
                                dataBaseUser.setName(string);
                                ruleDatabaseUser.setDatabaseUser(dataBaseUser);
                                setRuleDataBaseUser.add(ruleDatabaseUser);
                            }

                            for (Iterator<String> it = eventTypeRulesSet.iterator(); it.hasNext();) {
                                String string = it.next();
                                RuleEventTypeWeb ruleEventType = new RuleEventTypeWeb();
                                EventTypeWeb eventType = new EventTypeWeb();
                                eventType.setName(string);
                                ruleEventType.setEventType(eventType);
                                setRuleEventType.add(ruleEventType);
                            }

                            r.setRulesDatabaseUsers(setRuleDataBaseUser);
                            r.setRulesEventTypes(setRuleEventType);

                        }

                        r = new RuleWeb();

                        databaseUserSet = new HashSet<>();
                        eventTypeRulesSet = new HashSet<>();

                        setRuleEventType = new HashSet<>();
                        setRuleDataBaseUser = new HashSet<>();

                        r.setId(currentIdRule);

                        if ((rs.getString("ip_list") == null) || (rs.getString("ip_list").isEmpty())) {
                            r.setIpList("*");
                        } else {
                            r.setIpList(rs.getString("ip_list"));
                        }

                        r.setEmailList(rs.getString("email_list"));
                        r.setType(rs.getString("rule_type").charAt(0));
                        r.setCriticalityDescription(rs.getString("criticality"));
                        r.setCriticalityId(rs.getLong("criticality_id"));
                        if (rs.getString("user") != null) {
                            databaseUserSet.add(rs.getString("user"));
                        }
                        if (rs.getString("event") != null) {
                            eventTypeRulesSet.add(rs.getString("event"));
                        }


                        allRules.add(r);

                    } else {
                        if (rs.getString("user") != null) {
                            databaseUserSet.add(rs.getString("user"));
                        }
                        if (rs.getString("event") != null) {
                            eventTypeRulesSet.add(rs.getString("event"));
                        }
                    }

                    previousIdRule = currentIdRule;

                }



            }

            for (Iterator<String> it = databaseUserSet.iterator(); it.hasNext();) {
                String string = it.next();
                RuleDatabaseUserWeb ruleDatabaseUser = new RuleDatabaseUserWeb();
                DatabaseUserWeb dataBaseUser = new DatabaseUserWeb();
                dataBaseUser.setName(string);
                ruleDatabaseUser.setDatabaseUser(dataBaseUser);
                setRuleDataBaseUser.add(ruleDatabaseUser);

            }


            for (Iterator<String> it = eventTypeRulesSet.iterator(); it.hasNext();) {
                String string = it.next();
                RuleEventTypeWeb ruleEventType = new RuleEventTypeWeb();
                EventTypeWeb eventType = new EventTypeWeb();
                eventType.setName(string);
                ruleEventType.setEventType(eventType);
                setRuleEventType.add(ruleEventType);
            }

            r.setRulesDatabaseUsers(setRuleDataBaseUser);
            r.setRulesEventTypes(setRuleEventType);

        } finally {
            DBUtil.close(rs);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return allRules;
    }
}
