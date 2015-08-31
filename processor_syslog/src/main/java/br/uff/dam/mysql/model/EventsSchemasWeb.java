/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.model;

import javax.persistence.*;

/**
 *
 * @author cicco
 */

@Entity
public class EventsSchemasWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private EventWeb event;
    
    @ManyToOne
    private SchemaWeb schema;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventWeb getEvent() {
        return event;
    }

    public void setEvent(EventWeb event) {
        this.event = event;
    }

    public SchemaWeb getSchema() {
        return schema;
    }

    public void setSchema(SchemaWeb schema) {
        this.schema = schema;
    }

}
