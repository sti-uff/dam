package controller;

import br.uff.dam.collect_syslog.Config;
import java.util.Date;
import model.Connection_Cassandra;
import model.Event;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author victor
 */
public class Parser {

    private static String[] msg;

    public static Event msgToEvent(String s, String host) {
        Event e = new Event();
        msg = s.replaceAll("\"", "").split(" ");

        e.setServer_addr(host);
        e.setClient_addr(getValue(Config.getProperty("userevent.Client_addr")));
        e.setDb_user_name(getValue(Config.getProperty("userevent.Db_user_name")));
        e.setDb_id(Double.valueOf(getValue(Config.getProperty("userevent.Db_id"))));
        e.setTables(getValue(Config.getProperty("userevent.Tables")));
        setType(e);

        return e;
    }

    private static String getValue(String param) {
        for (int i = 0; i < msg.length; i++) {
            if (msg[i].contains(param)) {
                return msg[i + 1];
            }
        }
        return "";
    }

    private static void setType(Event e) {
        String action = getValue(Config.getProperty("log.user"));
        /*ALTER, AUDIT, COMMENT, DELETE, GRANT, INDEX, INSERT, LOCK, RENAME, SELECT, UPDATE, REFERENCES, and EXECUTE*/

        if (action.compareTo("S---------------") == 0) { //ALTER
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.ALTER")));
            e.setQuery_text("ALTER");
        }
        if (action.compareTo("-S--------------") == 0) { //AUDIT
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.AUDIT")));
            e.setQuery_text("AUDIT");
        }
        if (action.compareTo("--S-------------") == 0) { //COMMENT
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.COMMENT")));
            e.setQuery_text("COMMENT");
        }
        if (action.compareTo("---S------------") == 0) { //DELETE
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.DELETE")));
            e.setQuery_text("DELETE");
        }
        if (action.compareTo("----S-----------") == 0) { //GRANT
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.GRANT")));
            e.setQuery_text("GRANT");
        }
        if (action.compareTo("-----S----------") == 0) { //INDEX
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.INDEX")));
            e.setQuery_text("INDEX");
        }
        if (action.compareTo("------S---------") == 0) { //INSERT
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.INSERT")));
            e.setQuery_text("INSERT");
        }
        if (action.compareTo("-------S--------") == 0) { //LOCK
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.LOCK")));
            e.setQuery_text("LOCK");
        }
        if (action.compareTo("--------S-------") == 0) { //RENAME
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.RENAME")));
            e.setQuery_text("RENAME");
        }
        if (action.compareTo("---------S------") == 0) { //SELECT
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.SELECT")));
            e.setQuery_text("SELECT");
        }
        if (action.compareTo("----------S-----") == 0) { //UPDATE
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.UPDATE")));
            e.setQuery_text("UPDATE");
        }
        if (action.compareTo("-----------S----") == 0) { //REFERENCES
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.REFERENCES")));
            e.setQuery_text("REFERENCES");
        }
        if (action.compareTo("------------S---") == 0) { //EXECUTE
            e.setQuery_type(Integer.valueOf(Config.getProperty("user.queryType.EXECUTE")));
            e.setQuery_text("EXECUTE");
        }

    }

    public static Connection_Cassandra msgToConnection(String s) {
        Connection_Cassandra e = new Connection_Cassandra();
        msg = s.replaceAll("\"", "").split(" ");

        e.setConnection_id(getValue(Config.getProperty("userevent.Connection_id")));
        e.setDb_user_name(getValue(Config.getProperty("userevent.Db_user_name")));
        e.setDb_id(getValue(Config.getProperty("userevent.Db_id")));
        e.setConnection_timestamp(new Date());
        e.setEnd_limit_timestamp(new Date());

        return e;
    }

    public static Event msgToEventSysdba(String message, String host) {
        Event e = new Event();
        msg = message.split("\'");

        e.setServer_addr(host);
        e.setClient_addr(getValue(Config.getProperty("sysdbaevent.Client_addr")));
        e.setDb_user_name(getValue(Config.getProperty("sysdbaevent.Db_user_name")));
        e.setDb_id(Double.valueOf(getValue(Config.getProperty("sysdbaevent.Db_id"))));
        e.setTables("");
        e.setQuery_text(getValue(Config.getProperty("sysdbaevent.Query_text")));

        setTypeSysdba(e);

        return e;
    }

    private static void setTypeSysdba(Event e) {
        String action = getValue(Config.getProperty("sysdbaevent.Query_text")).toUpperCase();

        String[] actions = Config.getProperty("sysdba.actions").replace(" ", "").split(",");

        for (String act : actions) {
            if (action.contains(act)) { 
                e.setQuery_type(Integer.valueOf(Config.getProperty("sysdba."+act)));
                break;
            }
        }
    }
}
