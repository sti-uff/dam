package br.uff.dam.mysql.model;

import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author cicco
 */

@Entity
public class RuleDatabaseUserWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private RuleWeb rule;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private DatabaseUserWeb databaseUser;

    public RuleDatabaseUserWeb() {

    }

    public RuleDatabaseUserWeb(RuleWeb rule, DatabaseUserWeb databaseUser) {
        this.rule = rule;
        this.databaseUser = databaseUser;
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

    public DatabaseUserWeb getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(DatabaseUserWeb databaseUser) {
        this.databaseUser = databaseUser;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.rule != null ? this.rule.hashCode() : 0);
        hash = 67 * hash + (this.databaseUser != null ? this.databaseUser.hashCode() : 0);
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
        final RuleDatabaseUserWeb other = (RuleDatabaseUserWeb) obj;
        if (this.rule != other.rule && (this.rule == null || !this.rule.equals(other.rule))) {
            return false;
        }
        if (this.databaseUser != other.databaseUser && (this.databaseUser == null || !this.databaseUser.equals(other.databaseUser))) {
            return false;
        }
        return true;
    }


}