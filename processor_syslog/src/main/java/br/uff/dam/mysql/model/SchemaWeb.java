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
@Table(name = "database_schema")
public class SchemaWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "schema")
    private Set<EventsSchemasWeb> eventsSchemas;

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

    public Set<EventsSchemasWeb> getEventsSchemas() {
        return eventsSchemas;
    }

    public void setEventsSchemas(Set<EventsSchemasWeb> eventsSchemas) {
        this.eventsSchemas = eventsSchemas;
    }
    
}
