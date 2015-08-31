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
public class Event {
    
  private Date timestamp;
  private Date query_timestamp ;
  private String client_addr;
  private String server_addr;
  private long tcp_seq_number;
  //private String columns;
  private Date connection_start_timestamp;
  //private long connection_web_id;
  private double db_id;
  private String db_user_name;
  //private Date end_limit_timestamp;
  //private String event_type ;
  private String query_text;
  private int query_type ;
  private String tables ;

    public Event() {
        this.timestamp = new Date();
        this.query_timestamp = new Date();
        this.client_addr = "";
        this.server_addr = "";
        this.tcp_seq_number = new Date().getTime();
        //this.columns = "";
        this.connection_start_timestamp = new Date();
        //this.connection_web_id = new Date().getTime();
        this.db_id = 0;
        this.db_user_name = "";
        //this.end_limit_timestamp = new Date();
        //this.event_type = "";
        this.query_text = "";
        this.query_type = 0;
        this.tables = "";
    }
  
    public Event(Date timestamp, Date query_timestamp, String client_addr, String server_addr, long tcp_seq_number, String columns, Date connection_start_timestamp, long connection_web_id, double db_id, String db_user_name, Date end_limit_timestamp, String event_type, String query_text, int query_type, String tables) {
        this.timestamp = timestamp;
        this.query_timestamp = query_timestamp;
        this.client_addr = client_addr;
        this.server_addr = server_addr;
        this.tcp_seq_number = tcp_seq_number;
        //this.columns = columns;
        this.connection_start_timestamp = connection_start_timestamp;
        //this.connection_web_id = connection_web_id;
        this.db_id = db_id;
        this.db_user_name = db_user_name;
        //this.end_limit_timestamp = end_limit_timestamp;
        //this.event_type = event_type;
        this.query_text = query_text;
        this.query_type = query_type;
        this.tables = tables;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getQuery_timestamp() {
        return query_timestamp;
    }

    public void setQuery_timestamp(Date query_timestamp) {
        this.query_timestamp = query_timestamp;
    }

    public String getClient_addr() {
        return client_addr;
    }

    public void setClient_addr(String client_addr) {
        this.client_addr = client_addr;
    }

    public String getServer_addr() {
        return server_addr;
    }

    public void setServer_addr(String server_addr) {
        this.server_addr = server_addr;
    }

    public long getTcp_seq_number() {
        return tcp_seq_number;
    }

    public void setTcp_seq_number(long tcp_seq_number) {
        this.tcp_seq_number = tcp_seq_number;
    }

//    public String getColumns() {
//        return columns;
//    }
//
//    public void setColumns(String columns) {
//        this.columns = columns;
//    }

    public Date getConnection_start_timestamp() {
        return connection_start_timestamp;
    }

    public void setConnection_start_timestamp(Date connection_start_timestamp) {
        this.connection_start_timestamp = connection_start_timestamp;
    }

//    public long getConnection_web_id() {
//        return connection_web_id;
//    }
//
//    public void setConnection_web_id(long connection_web_id) {
//        this.connection_web_id = connection_web_id;
//    }

    public double getDb_id() {
        return db_id;
    }

    public void setDb_id(double db_id) {
        this.db_id = db_id;
    }

    public String getDb_user_name() {
        return db_user_name;
    }

    public void setDb_user_name(String db_user_name) {
        this.db_user_name = db_user_name;
    }

//    public Date getEnd_limit_timestamp() {
//        return end_limit_timestamp;
//    }
//
//    public void setEnd_limit_timestamp(Date end_limit_timestamp) {
//        this.end_limit_timestamp = end_limit_timestamp;
//    }

//    public String getEvent_type() {
//        return event_type;
//    }
//
//    public void setEvent_type(String event_type) {
//        this.event_type = event_type;
//    }

    public String getQuery_text() {
        return query_text;
    }

    public void setQuery_text(String query_text) {
        this.query_text = query_text;
    }

    public int getQuery_type() {
        return query_type;
    }

    public void setQuery_type(int query_type) {
        this.query_type = query_type;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    
    
}
