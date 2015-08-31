/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.service;

import br.uff.dam.mysql.dao.EventDAO;
import br.uff.dam.mysql.model.EventWeb;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author thiago
 */
public class EventService {

    private EventDAO eventDAO = new EventDAO();
    private Logger logger;

    public void createEvent(EventWeb event) throws SQLException {
        logger = Logger.getLogger("EventService.createEvent()");
        eventDAO.insertEvent(event);
    }
}

