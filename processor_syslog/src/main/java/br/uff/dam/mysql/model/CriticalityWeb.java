package br.uff.dam.mysql.model;

import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author leandrodecicco
 */

@Entity
public class CriticalityWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer severity;

    private String description;

    @OneToMany(mappedBy = "criticality")
    private Set<AlertWeb> alerts;

    @OneToMany(mappedBy = "criticality")
    private Set<RuleWeb> rules;

    public CriticalityWeb(){

    }

    public CriticalityWeb(Long id, Integer severity, String description) {
        this.id = id;
        this.severity = severity;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AlertWeb> getAlerts() {
        return alerts;
    }

    public void setAlerts(Set<AlertWeb> alerts) {
        this.alerts = alerts;
    }

    public Set<RuleWeb> getRules() {
        return rules;
    }

    public void setRules(Set<RuleWeb> rules) {
        this.rules = rules;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.severity != null ? this.severity.hashCode() : 0);
        hash = 71 * hash + (this.description != null ? this.description.hashCode() : 0);
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
        final CriticalityWeb other = (CriticalityWeb) obj;
        if (this.severity != other.severity && (this.severity == null || !this.severity.equals(other.severity))) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        return true;
    }


}