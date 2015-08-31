/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author victor
 */
public class Connection_Cassandra {

    private String connection_id;
    private Date connection_timestamp;
    private String db_id;
    private String db_user_name;
    private Date end_limit_timestamp;

    public String getConnection_id() {
        return connection_id;
    }

    public void setConnection_id(String connection_id) {
        this.connection_id = connection_id;
    }

    public Date getConnection_timestamp() {
        return connection_timestamp;
    }

    public void setConnection_timestamp(Date connection_timestamp) {
        this.connection_timestamp = connection_timestamp;
    }

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getDb_user_name() {
        return db_user_name;
    }

    public void setDb_user_name(String db_user_name) {
        this.db_user_name = db_user_name;
    }

    public Date getEnd_limit_timestamp() {
        return end_limit_timestamp;
    }

    public void setEnd_limit_timestamp(Date end_limit_timestamp) {
        this.end_limit_timestamp = end_limit_timestamp;
    }
}
