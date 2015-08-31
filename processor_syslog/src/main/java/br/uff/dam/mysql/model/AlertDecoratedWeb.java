/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.model;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author leandrodecicco
 */

@Entity
public class AlertDecoratedWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long ruleId;

    private String eventRules;

    private String userRules;

    private String event;

    private String clientAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampEvent;

    private String ipList;

    private String emailList;

    private String query;

    private String user;

    private String criticalityDescription;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the eventRules
     */
    public String getEventRules() {
        return eventRules;
    }

    /**
     * @param eventRules the eventRules to set
     */
    public void setEventRules(String eventRules) {
        this.eventRules = eventRules;
    }

    /**
     * @return the userRules
     */
    public String getUserRules() {
        return userRules;
    }

    /**
     * @param userRules the userRules to set
     */
    public void setUserRules(String userRules) {
        this.userRules = userRules;
    }

    /**
     * @return the event
     */
    public String getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * @return the clientAddress
     */
    public String getClientAddress() {
        return clientAddress;
    }

    /**
     * @param clientAddress the clientAddress to set
     */
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    /**
     * @return the timestampEvent
     */
    public Date getTimestampEvent() {
        return timestampEvent;
    }

    /**
     * @param timestampEvent the timestampEvent to set
     */
    public void setTimestampEvent(Date timestampEvent) {
        this.timestampEvent = timestampEvent;
    }

    /**
     * @return the ipList
     */
    public String getIpList() {
        return ipList;
    }

    /**
     * @param ipList the ipList to set
     */
    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    /**
     * @return the emailList
     */
    public String getEmailList() {
        return emailList;
    }

    /**
     * @param emailList the emailList to set
     */
    public void setEmailList(String emailList) {
        this.emailList = emailList;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the criticalityDescription
     */
    public String getCriticalityDescription() {
        return criticalityDescription;
    }

    /**
     * @param criticalityDescription the criticalityDescription to set
     */
    public void setCriticalityDescription(String criticalityDescription) {
        this.criticalityDescription = criticalityDescription;
    }

    /**
     * @return the ruleId
     */
    public Long getRuleId() {
        return ruleId;
    }

    /**
     * @param ruleId the ruleId to set
     */
    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }
    


    
}
