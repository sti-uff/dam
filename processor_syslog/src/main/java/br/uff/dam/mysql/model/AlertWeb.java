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
public class AlertWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private Long eventId;

    private Long criticalityId;

    private Long ruleId;

    private Boolean emailSent;

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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the eventId
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * @return the criticalityId
     */
    public Long getCriticalityId() {
        return criticalityId;
    }

    /**
     * @param criticalityId the criticalityId to set
     */
    public void setCriticalityId(Long criticalityId) {
        this.criticalityId = criticalityId;
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

    /**
     * @return the sentEmail
     */
    public Boolean getEmailSent() {
        return emailSent;
    }

    /**
     * @param sentEmail the sentEmail to set
     */
    public void setEmailSent(Boolean emailSent) {
        this.emailSent = emailSent;
    }

    
}
