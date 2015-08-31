/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.model;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author cicco
 */
@Entity
public class StatisticWeb {

    public static final Long NUMBER_PACKETS_NETWORK_RECEIVED = 0l;
    public static final Long NUMBER_PACKETS_NETWORK_DROPPED  = 1l;
    public static final Long NUMBER_EVENTS_ALERT_CHECKED  = 2l;
    public static final Long NUMBER_CONNECTION_SPACE_MEMORY_ACTIVE_STATISTIC_TYPE_ID  =3l;
    public static final Long NUMBER_QUERY_SPACE_MEMORY_ACTIVE_STATISTIC_TYPE_ID  =4l;
    public static final Long NUMBER_OF_PERSISTED_EVENTS_STATISTIC_TYPE_ID  =5l;
    public static final Long SPEED_PER_SECONDS_OF_EVENT_PERSISTENCE_STATISTIC_TYPE_ID  =6l;


    public static Long NUMBER_CONNECTION_SPACE_MEMORY_ACTIVE = 0l;
    public static Long NUMBER_PERSITED_EVENTS = 0l;
    public static Long NUMBER_QUERY_SPACE_MEMORY_ACTIVE = 0l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long statisticTypeId;

    private Double value;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the statisticTypeId
     */
    public Long getStatisticTypeId() {
        return statisticTypeId;
    }

    /**
     * @param statisticTypeId the statisticTypeId to set
     */
    public void setStatisticTypeId(Long statisticTypeId) {
        this.statisticTypeId = statisticTypeId;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
