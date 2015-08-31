/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.service;

import br.uff.dam.mysql.dao.EventTypeDAO;
import br.uff.dam.mysql.model.EventTypeWeb;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author thiago
 */
public class EventTypeService {

    private EventTypeDAO eventTypeDAO = new EventTypeDAO();
    private Logger logger;

    public Map<String, EventTypeWeb> getMapEventTypes(){
        logger = Logger.getLogger("EventTypeService.getMapEventTypes()");
        Map<String, EventTypeWeb> mapEventType = new HashMap<>();
        try {
            mapEventType = eventTypeDAO.getMapAllEventTypes();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return mapEventType;
    }

    public List<String> getStringAllEventTypes(){
        logger = Logger.getLogger("EventTypeService.getMapEventTypes()");
        List<String> allEventTypes = new ArrayList<>();
        try {
            allEventTypes = eventTypeDAO.getListAllEventTypes();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return allEventTypes;
    }

}
