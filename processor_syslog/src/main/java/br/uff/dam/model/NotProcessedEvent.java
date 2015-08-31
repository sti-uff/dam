package br.uff.dam.model;

import org.springframework.data.cassandra.mapping.Table;

@Table(value="not_processed_events")
public class NotProcessedEvent extends BaseEvent {


}
