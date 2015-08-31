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
public class DatabaseInstanceWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ip;

    private String name;
    
    @OneToMany(mappedBy = "databaseInstance")
    private Set<ConnectionWeb> connections;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
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

}
