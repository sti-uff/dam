/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author 01245189158
 */
public class DamConnectionModel implements Serializable{
    private static final long serialVersionUID = 3L;
    @Id
    @Column(name = "connection_start_timestamp")
    @Temporal(javax.persistence.TemporalType.DATE)
    private long connectionStartTimestamp;
    @Id
    @Column(name = "server_addr")
    private String serverAddr;
    @Id
    @Column(name = "client_addr")
    private String clientAddr;
    @Column(name = "end_limit_timestamp")
    @Temporal(javax.persistence.TemporalType.DATE)
    private long endLimitTimestamp;
    @Column(name = "db_user_name")
    private String dbUserName;
    @Column(name = "db_id")
    private Double dbId;

    public DamConnectionModel(){
        
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
    public Double getDbId() {
        return dbId;
    }

    /**
     * @param dbId the dbId to set
     */
    public void setDbId(Double dbId) {
        this.dbId = dbId;
    }

}
