/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.service;

import br.uff.dam.mysql.dao.StatisticDAO;
import br.uff.dam.mysql.model.StatisticWeb;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author thiago
 */
public class StatisticService {

    private StatisticDAO statisticDAO = new StatisticDAO();
    private Logger logger;

    public void createStatistic(StatisticWeb statistic) throws SQLException {
        logger = Logger.getLogger("StatisticService.createStatistic");
        statisticDAO.insertStatistic(statistic);
    }

    public void createStatistic(Long statisticType, Double value) throws SQLException {
        StatisticWeb statisticWeb = new StatisticWeb();
        statisticWeb.setCreatedAt(new Date());
        statisticWeb.setUpdatedAt(new Date());
        statisticWeb.setValue(value);
        statisticWeb.setStatisticTypeId(statisticType);

        logger = Logger.getLogger("StatisticService.createStatistic");
        statisticDAO.insertStatistic(statisticWeb);
    }

    public void updateStatistic(Long statisticType, Double value) throws SQLException {
        StatisticWeb statisticWeb = new StatisticWeb();
        statisticWeb.setCreatedAt(new Date());
        statisticWeb.setUpdatedAt(new Date());
        statisticWeb.setValue(value);
        statisticWeb.setStatisticTypeId(statisticType);

        logger = Logger.getLogger("StatisticService.updateStatistic");
        statisticDAO.updateStatistic(statisticWeb);
    }

    public synchronized void  updateStatisticNumberOfPersistedEventsInMemory(Double value){
        StatisticWeb.NUMBER_PERSITED_EVENTS = StatisticWeb.NUMBER_PERSITED_EVENTS + value.longValue();
    }

    public synchronized Long eraseStatisticNumberOfPersistedEvents(){
        Long aux = StatisticWeb.NUMBER_PERSITED_EVENTS;
        StatisticWeb.NUMBER_PERSITED_EVENTS = 0l;
        return aux;
    }

    
}
