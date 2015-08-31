/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.model;

import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author cicco
 */

@Entity
//@Table(name="eventtype") //hsqldb puts '_' character in table names of camelCase named classes: EventType => event_type
public class EventTypeWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String regexPattern;
    
    @OneToMany(mappedBy = "eventType")
    private Set<EventWeb> events;

    @OneToMany(mappedBy = "eventType")
    private Set<RuleEventTypeWeb> rulesEventTypes;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description.toUpperCase();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegexPattern() {
        return regexPattern;
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }

    public Set<EventWeb> getEvents() {
        return events;
    }

    public void setEvents(Set<EventWeb> events) {
        this.events = events;
    }

    public Set<RuleEventTypeWeb> getRulesEventTypes() {
        return rulesEventTypes;
    }

    public void setRulesEventTypes(Set<RuleEventTypeWeb> rulesEventTypes) {
        this.rulesEventTypes = rulesEventTypes;
    }

}
