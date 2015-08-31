/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.model;

import java.io.Serializable;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 *
 * @author 01245189158
 */
@Table(value="connections")
public class ConnectionCassandraModel implements Serializable{
    private static final long serialVersionUID = 3L;
    @PrimaryKey(value = "connection_id")
    private String connectionId;
    @Column(value = "connection_timestamp")
    private long connectionTimestamp;
    @Column(value = "db_user_name")
    private String dbUserName;
    @Column(value = "db_id")
    private String dbId;
    @Column(value = "end_limit_timestamp")
    private long endLimitTimestamp;

    /**
     * @return the connectionId
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * @param connectionId the connectionId to set
     */
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * @return the connectionTimestamp
     */
    public long getConnectionTimestamp() {
        return connectionTimestamp;
    }

    /**
     * @param connectionTimestamp the connectionTimestamp to set
     */
    public void setConnectionTimestamp(long connectionTimestamp) {
        this.connectionTimestamp = connectionTimestamp;
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
}
