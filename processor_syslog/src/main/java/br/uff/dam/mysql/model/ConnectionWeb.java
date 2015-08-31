/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author cicco
 */

@Entity
public class ConnectionWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String clientAddress;

    private String serverAddress;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date connectionStart;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date connectionEnd;

    private Long databaseUserId;

    private Long databaseInstanceId;
    
    @OneToMany(mappedBy = "connection")
    private Set<EventWeb> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    
    public Date getConnectionStart() {
        return connectionStart;
    }

    public void setConnectionStart(Date connectionStart) {
        this.connectionStart = connectionStart;
    }

    public Date getConnectionEnd() {
        return connectionEnd;
    }

    public void setConnectionEnd(Date connectionEnd) {
        this.connectionEnd = connectionEnd;
    }


    
    public Set<EventWeb> getEvents() {
        return events;
    }

    public void setEvents(Set<EventWeb> events) {
        this.events = events;
    }

    /**
     * @return the serverAddress
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * @param serverAddress the serverAddress to set
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * @return the databaseUserId
     */
    public Long getDatabaseUserId() {
        return databaseUserId;
    }

    /**
     * @param databaseUserId the databaseUserId to set
     */
    public void setDatabaseUserId(Long databaseUserId) {
        this.databaseUserId = databaseUserId;
    }

    /**
     * @return the databaseInstance
     */
    public Long getDatabaseInstanceId() {
        return databaseInstanceId;
    }

    /**
     * @param databaseInstance the databaseInstance to set
     */
    public void setDatabaseInstanceId(Long databaseInstanceId) {
        this.databaseInstanceId = databaseInstanceId;
    }
    
}
