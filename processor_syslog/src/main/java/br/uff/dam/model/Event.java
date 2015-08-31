package br.uff.dam.model;

import org.springframework.data.cassandra.mapping.Table;

@Table(value="events")
public class Event extends BaseEvent {

    
}
