package br.uff.dam.mysql.model;

import javax.persistence.*;

/**
 *
 * @author cicco
 */

@Entity
public class RuleEventTypeWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private RuleWeb rule;

    @ManyToOne
    private EventTypeWeb eventType;


    public RuleEventTypeWeb() {

    }

    public RuleEventTypeWeb(RuleWeb rule, EventTypeWeb eventType) {
        this.rule = rule;
        this.eventType = eventType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RuleWeb getRule() {
        return rule;
    }

    public void setRule(RuleWeb rule) {
        this.rule = rule;
    }

    public EventTypeWeb getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeWeb eventType) {
        this.eventType = eventType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.rule != null ? this.rule.hashCode() : 0);
        hash = 19 * hash + (this.eventType != null ? this.eventType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RuleEventTypeWeb other = (RuleEventTypeWeb) obj;
        if (this.rule != other.rule && (this.rule == null || !this.rule.equals(other.rule))) {
            return false;
        }
        if (this.eventType != other.eventType && (this.eventType == null || !this.eventType.equals(other.eventType))) {
            return false;
        }
        return true;
    }


}