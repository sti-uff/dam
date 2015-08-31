/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 *
 * @author 01245189158
 */
@Table(value="connections")
public class Connection implements Serializable {

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

    private String serverAddr;
    private String clientAddr;
    private List<InProcessingRawData> inProcessingRawDatas;

    
    private static final long TIMESTAMP_RANGE = 60000;

    public Connection(String serverAddress, String clientAddress) {
        this.serverAddr = serverAddress;
        this.clientAddr = clientAddress;
        this.connectionId = serverAddr + "_" + clientAddr;
        endLimitTimestamp = Long.MAX_VALUE;
        inProcessingRawDatas = new ArrayList();
    }

    public void addInProcessingRawData(InProcessingRawData inProcessingRawData){
        getInProcessingRawDatas().add(inProcessingRawData);
    }

    public void clearInProcessingRawDatas(){
        getInProcessingRawDatas().clear();
    }

    /**
     * @return the connectionStartTimestamp
     */
    public long getConnectionTimestamp() {
        return connectionTimestamp;
    }

    /**
     * @param connectionStartTimestamp the connectionStartTimestamp to set
     */
    public void setConnectionTimestamp(long connectionTimestamp) {
        this.connectionTimestamp = connectionTimestamp;
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
    public String getDbId() {
        return dbId;
    }

    /**
     * @param dbId the dbId to set
     */
    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

//    public String getConnectionAbsoluteID() {
////        return serverIPAddress+":"+serverPort+"_"+clientIPAddress+":"+clientPort+"_"+getStartTimestamp();
//        return serverAddr + clientAddr + "_" + connectionStartTimestamp;
//    }

    public String getConnectionID() {
        return connectionId;
    }

    public boolean belongsToTimeConnection(long timestamp) {
        if ((timestamp + TIMESTAMP_RANGE) >= connectionTimestamp && (timestamp - TIMESTAMP_RANGE) <= getEndLimitTimestamp()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the inProcessingRawDatas
     */
    public List<InProcessingRawData> getInProcessingRawDatas() {
        return inProcessingRawDatas;
    }
}
