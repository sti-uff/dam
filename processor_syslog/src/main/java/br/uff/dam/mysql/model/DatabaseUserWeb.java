/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.model;

import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author cicco
 */

@Entity
public class DatabaseUserWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;

    private String databaseInstance;
        
    @OneToMany(mappedBy = "databaseUser")
    private Set<ConnectionWeb> connections;
    
    @OneToMany(mappedBy = "databaseUser")
    private Set<RuleDatabaseUserWeb> rulesDatabaseUserses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ConnectionWeb> getConnections() {
        return connections;
    }

    public void setConnections(Set<ConnectionWeb> connections) {
        this.connections = connections;
    }

    public Set<RuleDatabaseUserWeb> getRulesDatabaseUserses() {
        return rulesDatabaseUserses;
    }

    public void setRulesDatabaseUserses(Set<RuleDatabaseUserWeb> rulesDatabaseUserses) {
        this.rulesDatabaseUserses = rulesDatabaseUserses;
    }

    /**
     * @return the databaseInstance
     */
    public String getDatabaseInstance() {
        return databaseInstance;
    }

    /**
     * @param databaseInstance the databaseInstance to set
     */
    public void setDatabaseInstance(String databaseInstance) {
        this.databaseInstance = databaseInstance;
    }

}
