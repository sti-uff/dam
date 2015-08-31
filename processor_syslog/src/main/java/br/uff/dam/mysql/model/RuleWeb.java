package br.uff.dam.mysql.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author cicco
 */
public class RuleWeb {

    public static final Character RULE_TYPE_ACCEPT = 'A';
    public static final Character RULE_TYPE_REJECT = 'R';
    private Long id;
    private String name;
    private Character type;
    private Integer priority;
    private Boolean active;
    private String ipList;
    private String emailList;
    private Set<RuleEventTypeWeb> rulesEventTypes;
    private Set<RuleDatabaseUserWeb> rulesDatabaseUsers;
    private String criticalityDescription;
    private Long criticalityId;
    private Set<AlertWeb> alerts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getIpList() {
        return ipList;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    public String getEmailList() {
        return emailList;
    }

    public void setEmailList(String emailList) {
        this.emailList = emailList;
    }

    public Set<RuleEventTypeWeb> getRulesEventTypes() {
        return rulesEventTypes;
    }

    public void setRulesEventTypes(Set<RuleEventTypeWeb> rulesEventTypes) {
        this.rulesEventTypes = rulesEventTypes;
    }

    public Set<EventTypeWeb> getEventTypes() {
        Set<EventTypeWeb> eventTypes = new LinkedHashSet<EventTypeWeb>();
        if (this.getRulesEventTypes() != null) {
            for (RuleEventTypeWeb ret : this.getRulesEventTypes()) {
                eventTypes.add(ret.getEventType());
            }
        }
        return eventTypes;
    }

    //TO-DO
    //testar usar isso para o bind da collection como alternativa
//    public void setEventTypes(Set<EventType> eventTypes) {
//        Set<RuleEventType> newRuleEventTypes = new LinkedHashSet<RuleEventType>();
//        for (EventType et : eventTypes) {
//            newRuleEventTypes.add(new RuleEventType(this, et));
//        }
//        this.rulesEventTypes = newRuleEventTypes;
//    }
    public Set<RuleDatabaseUserWeb> getRulesDatabaseUsers() {
        return rulesDatabaseUsers;
    }

    public void setRulesDatabaseUsers(Set<RuleDatabaseUserWeb> rulesDatabaseUsers) {
        this.rulesDatabaseUsers = rulesDatabaseUsers;
    }

    public Set<DatabaseUserWeb> getDatabaseUsers() {
        Set<DatabaseUserWeb> databaseUsers = new LinkedHashSet<DatabaseUserWeb>();
        if (this.getRulesDatabaseUsers() != null) {
            for (RuleDatabaseUserWeb rdu : this.getRulesDatabaseUsers()) {
                databaseUsers.add(rdu.getDatabaseUser());
            }
        }
        return databaseUsers;
    }

    public String getCriticalityDescription() {
        return criticalityDescription;
    }

    public void setCriticalityDescription(String criticality) {
        this.criticalityDescription = criticality;
    }

    public Set<AlertWeb> getAlerts() {
        return alerts;
    }

    public void setAlerts(Set<AlertWeb> alerts) {
        this.alerts = alerts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 23 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 23 * hash + (this.priority != null ? this.priority.hashCode() : 0);
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
        final RuleWeb other = (RuleWeb) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.type != other.type && (this.type == null || !this.type.equals(other.type))) {
            return false;
        }
        if (this.priority != other.priority && (this.priority == null || !this.priority.equals(other.priority))) {
            return false;
        }
        return true;
    }

    public String toString() {


        String usuarios = new String();
        String tiposEventos = new String();

        if ((this.getRulesDatabaseUsers() == null) || (this.getRulesDatabaseUsers().isEmpty())) {
            usuarios = "*";
        } else {
            for (Iterator<RuleDatabaseUserWeb> it = this.getRulesDatabaseUsers().iterator(); it.hasNext();) {
                RuleDatabaseUserWeb databaseUser = it.next();
                usuarios = usuarios + "- " + databaseUser.getDatabaseUser().getName();
            }
        }

        if ((this.getRulesEventTypes() == null) || (this.getRulesEventTypes().isEmpty())) {
            tiposEventos = "*";
        } else {
            for (Iterator<RuleEventTypeWeb> it = this.getRulesEventTypes().iterator(); it.hasNext();) {
                RuleEventTypeWeb rulesEventType = it.next();
                tiposEventos = tiposEventos + " - " + rulesEventType.getEventType().getName();
            }
        }






        return "Regra: " + this.getId() + "; Usuários: " + usuarios + "; Eventos: " + tiposEventos + "; IPS: " + this.getIpList();
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
}
