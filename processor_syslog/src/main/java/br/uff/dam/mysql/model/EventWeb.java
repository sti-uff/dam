/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.model;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author thiagodiogo
 * @author leandrocicco
 */

@Entity
public class EventWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    private String text;
    
    private String tables;

    private String columns;

    @ManyToOne
    private Long eventTypeId;
    
    private String clientAddress;

    private String serverAddress;

    private Long connectionWebId;

    private Long timestampInMiliseconds;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the eventTypeId
     */
    public Long getEventTypeId() {
        return eventTypeId;
    }

    /**
     * @param eventTypeId the eventTypeId to set
     */
    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    /**
     * @return the clientAddr
     */
    public String getClientAddress() {
        return clientAddress;
    }

    /**
     * @param clientAddr the clientAddr to set
     */
    public void setClientAddress(String clientAddr) {
        this.clientAddress = clientAddr;
    }

    /**
     * @return the serverAddr
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * @param serverAddr the serverAddr to set
     */
    public void setServerAddress(String serverAddr) {
        this.serverAddress = serverAddr;
    }

    /**
     * @return the connectionWebId
     */
    public Long getConnectionWebId() {
        return connectionWebId;
    }

    /**
     * @param connectionWebId the connectionWebId to set
     */
    public void setConnectionWebId(Long connectionWebId) {
        this.connectionWebId = connectionWebId;
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
     * @return the timestampInMiliseconds
     */
    public Long getTimestampInMiliseconds() {
        return timestampInMiliseconds;
    }

    /**
     * @param timestampInMiliseconds the timestampInMiliseconds to set
     */
    public void setTimestampInMiliseconds(Long timestampInMiliseconds) {
        this.timestampInMiliseconds = timestampInMiliseconds;
    }


}
