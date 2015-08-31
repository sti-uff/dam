/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.model;

import org.springframework.data.cassandra.mapping.Table;

/**
 *
 * @author 01245189158
 */
@Table(value="parsed_error_events")
public class ParsedErrorEvent extends BaseEvent {

}
