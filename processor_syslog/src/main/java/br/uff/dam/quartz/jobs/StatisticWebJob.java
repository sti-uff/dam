/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.quartz.jobs;

import br.uff.dam.mysql.model.StatisticWeb;
import br.uff.dam.mysql.service.AlertService;
import br.uff.dam.mysql.service.EventService;
import br.uff.dam.mysql.service.EventTypeService;
import br.uff.dam.mysql.service.StatisticService;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;


/**
 *
 * @author thiago
 */
public class StatisticWebJob implements Job {

    private Logger logger;
    private AlertService alertService = new AlertService();
    private EventService eventService = new EventService();
    private EventTypeService eventTypeService = new EventTypeService();
    private StatisticService statisticService = new StatisticService();

    @Override
    public void execute(JobExecutionContext jec) {


        Long numberOfPersistedEvents = statisticService.eraseStatisticNumberOfPersistedEvents();

        try{
            statisticService.createStatistic(StatisticWeb.NUMBER_OF_PERSISTED_EVENTS_STATISTIC_TYPE_ID, numberOfPersistedEvents.doubleValue());
            statisticService.updateStatistic(StatisticWeb.NUMBER_CONNECTION_SPACE_MEMORY_ACTIVE_STATISTIC_TYPE_ID, StatisticWeb.NUMBER_CONNECTION_SPACE_MEMORY_ACTIVE.doubleValue());
            statisticService.updateStatistic(StatisticWeb.NUMBER_QUERY_SPACE_MEMORY_ACTIVE_STATISTIC_TYPE_ID, StatisticWeb.NUMBER_QUERY_SPACE_MEMORY_ACTIVE.doubleValue());
        } catch(SQLException e){
            
        }

        


    }
}
