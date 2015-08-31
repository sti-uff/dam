package br.uff.dam.model;

import java.io.Serializable;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;

public class BaseEvent implements Serializable {

    private static final long serialVersionUID = 3L;
    @PrimaryKey(value = "timestamp")
    private long timestamp;
    @Column(value = "query_timestamp")
    private long queryTimestamp;
    @Column(value = "query_type")
    private Integer queryType;
    @Column(value = "client_addr")
    private String clientAddr;
    @Column(value = "server_addr")
    private String serverAddr;
    @Column(value = "tcp_seq_number")
    private long tcpSequenceNumber;
    @Column(value = "query_text")
    private String queryText;
    @Column(value = "connection_start_timestamp")
    private long connectionStartTimestamp;
    @Column(value = "connection_web_id")
    private long connectionWebId;
    @Column(value = "end_limit_timestamp")
    private long endLimitTimestamp;
    @Column(value = "db_user_name")
    private String dbUserName;
    @Column(value = "db_id")
    private String dbId;
    @Column(value = "event_type")
    private String eventType;
    @Column(value = "tables")
    private String tables;
    @Column(value = "columns")
    private String columns;



    public BaseEvent(){

    }

    /**
     * @return the queryText
     */
    public String getQueryText() {
        return queryText;
    }

    /**
     * @param queryText the queryText to set
     */
    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    /**
     * @return the queryTimestamp
     */
    public long getQueryTimestamp() {
        return queryTimestamp;
    }

    /**
     * @param queryTimestamp the queryTimestamp to set
     */
    public void setQueryTimestamp(long queryTimestamp) {
        this.queryTimestamp = queryTimestamp;
    }

    /**
     * @return the serverAddr
     */
    public String getServerAddr() {
        return serverAddr;
    }

    /**
     * @param serverAddr the serverAddr to set
     */
    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    /**
     * @return the clientAddr
     */
    public String getClientAddr() {
        return clientAddr;
    }

    /**
     * @param clientAddr the clientAddr to set
     */
    public void setClientAddr(String clientAddr) {
        this.clientAddr = clientAddr;
    }

    /**
     * @return the connectionStartTimestamp
     */
    public long getConnectionStartTimestamp() {
        return connectionStartTimestamp;
    }

    /**
     * @param connectionStartTimestamp the connectionStartTimestamp to set
     */
    public void setConnectionStartTimestamp(long connectionStartTimestamp) {
        this.connectionStartTimestamp = connectionStartTimestamp;
    }

    /**
     * @return the endLimitTimestamp
     */
    public long getEndLimitTimestamp() {
        return endLimitTimestamp;
    }

    /**
     * @param endLimitTimestamp the endLimitTimestamp to set
     */
    public void setEndLimitTimestamp(long endLimitTimestamp) {
        this.endLimitTimestamp = endLimitTimestamp;
    }

    /**
     * @return the dbUserName
     */
    public String getDbUserName() {
        return dbUserName;
    }

    /**
     * @param dbUserName the dbUserName to set
     */
    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    /**
     * @return the dbId
     */
    public String getDbId() {
        return dbId;
    }

    /**
     * @param dbId the dbId to set
     */
    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    /**
     * @return the queryType
     */
    public Integer getQueryType() {
        return queryType;
    }

    /**
     * @param queryType the queryType to set
     */
    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    /**
     * @return the tcpSequenceNumber
     */
    public long getTcpSequenceNumber() {
        return tcpSequenceNumber;
    }

    /**
     * @param tcpSequenceNumber the tcpSequenceNumber to set
     */
    public void setTcpSequenceNumber(long tcpSequenceNumber) {
        this.tcpSequenceNumber = tcpSequenceNumber;
    }


    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the tables
     */
    public String getTables() {
        return tables;
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(String tables) {
        this.tables = tables;
    }

    /**
     * @return the columns
     */
    public String getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(String columns) {
        this.columns = columns;
    }

    /**
     * @return the connectionWebId
     */
    public long getConnectionWebId() {
        return connectionWebId;
    }

    /**
     * @param connectionWebId the connectionWebId to set
     */
    public void setConnectionWebId(long connectionWebId) {
        this.connectionWebId = connectionWebId;
    }
}
