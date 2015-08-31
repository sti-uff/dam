/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.quartz.jobs;

import br.uff.dam.mysql.service.EventTypeService;
import br.uff.dam.parser.service.SQLParser;
import br.uff.dam.model.Event;
import br.uff.dam.model.NotProcessedEvent;
import br.uff.dam.model.ParsedErrorEvent;
import br.uff.dam.model.SelectErrorEvent;
import br.uff.dam.model.UnknowTypeEvent;
import br.uff.sti.dam.dao.DamQueriesService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 *
 * @author thiago
 */
public class JobParserNotProcessedEvents implements Job {

    private Logger logger;
    private List<String> listAllEventTypes;
    private EventTypeService eventTypeService = new EventTypeService();

    @Override
    public void execute(JobExecutionContext jec) {

        listAllEventTypes = eventTypeService.getStringAllEventTypes();

        logger = Logger.getLogger("JobParserNotProcessedEvents");
        try {
            logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            logger.info("INICIANDO JOB DE PARSER");
            logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            logger.info("Quantidade de Tipos de eventos no mysql: " + listAllEventTypes.size());

            DamQueriesService damQueriesService = new DamQueriesService();
            SQLParser sqlParser = new SQLParser();
            boolean terminou = false;
            while (!terminou) {
                List<NotProcessedEvent> list = damQueriesService.getActivities();

                if (!list.isEmpty()) {
                    List<Event> listaEventsNova = new ArrayList();
                    for (NotProcessedEvent notProcessedEvent : list) {

                        Event eventParsed = new Event();
                        eventParsed.setClientAddr(notProcessedEvent.getClientAddr());
                        eventParsed.setConnectionStartTimestamp(notProcessedEvent.getConnectionStartTimestamp());
                        if (notProcessedEvent.getDbId() != null) {
                            eventParsed.setDbId(notProcessedEvent.getDbId());
                        }
                        eventParsed.setDbUserName(notProcessedEvent.getDbUserName());
                        eventParsed.setEndLimitTimestamp(notProcessedEvent.getEndLimitTimestamp());
                        eventParsed.setQueryText(notProcessedEvent.getQueryText());
                        eventParsed.setQueryTimestamp(notProcessedEvent.getQueryTimestamp());
                        eventParsed.setServerAddr(notProcessedEvent.getServerAddr());
                        eventParsed.setTcpSequenceNumber(notProcessedEvent.getTcpSequenceNumber());
                        eventParsed.setTimestamp(new Date().getTime());
                        eventParsed.setConnectionWebId(notProcessedEvent.getConnectionWebId());

                        sqlParser.checkdEventParserd(notProcessedEvent);

                        if (notProcessedEvent.getQueryType() == SQLParser.PARSER_ERROR) {
                            ParsedErrorEvent parsedErrorEvent = new ParsedErrorEvent();
                            parsedErrorEvent.setClientAddr(notProcessedEvent.getClientAddr());
                            parsedErrorEvent.setConnectionStartTimestamp(notProcessedEvent.getConnectionStartTimestamp());
                            if (notProcessedEvent.getDbId() != null) {
                                parsedErrorEvent.setDbId(notProcessedEvent.getDbId());
                            }
                            parsedErrorEvent.setDbUserName(notProcessedEvent.getDbUserName());
                            parsedErrorEvent.setEndLimitTimestamp(notProcessedEvent.getEndLimitTimestamp());
                            parsedErrorEvent.setQueryText(notProcessedEvent.getQueryText());
                            parsedErrorEvent.setQueryTimestamp(notProcessedEvent.getQueryTimestamp());
                            parsedErrorEvent.setServerAddr(notProcessedEvent.getServerAddr());
                            parsedErrorEvent.setTcpSequenceNumber(notProcessedEvent.getTcpSequenceNumber());
                            parsedErrorEvent.setTimestamp(new Date().getTime());
                            parsedErrorEvent.setConnectionWebId(notProcessedEvent.getConnectionWebId());
                            damQueriesService.updateParsedErrorEvent(parsedErrorEvent);
                        } else if (notProcessedEvent.getQueryType() == SQLParser.UNKNOW_TYPE) {
                            UnknowTypeEvent unknowTypeEvent = new UnknowTypeEvent();
                            unknowTypeEvent.setClientAddr(notProcessedEvent.getClientAddr());
                            unknowTypeEvent.setConnectionStartTimestamp(notProcessedEvent.getConnectionStartTimestamp());
                            if (notProcessedEvent.getDbId() != null) {
                                unknowTypeEvent.setDbId(notProcessedEvent.getDbId());
                            }
                            unknowTypeEvent.setDbUserName(notProcessedEvent.getDbUserName());
                            unknowTypeEvent.setEndLimitTimestamp(notProcessedEvent.getEndLimitTimestamp());
                            unknowTypeEvent.setQueryText(notProcessedEvent.getQueryText());
                            unknowTypeEvent.setQueryTimestamp(notProcessedEvent.getQueryTimestamp());
                            unknowTypeEvent.setServerAddr(notProcessedEvent.getServerAddr());
                            unknowTypeEvent.setTcpSequenceNumber(notProcessedEvent.getTcpSequenceNumber());
                            unknowTypeEvent.setTimestamp(new Date().getTime());
                            unknowTypeEvent.setConnectionWebId(notProcessedEvent.getConnectionWebId());
                            damQueriesService.updateUnknowTypeEvent(unknowTypeEvent);
                        } else if (notProcessedEvent.getQueryType() == SQLParser.SELECT_FALTANDO_PEDACO) {
                            SelectErrorEvent selectErrorEvent = new SelectErrorEvent();
                            selectErrorEvent.setClientAddr(notProcessedEvent.getClientAddr());
                            selectErrorEvent.setConnectionStartTimestamp(notProcessedEvent.getConnectionStartTimestamp());
                            if (notProcessedEvent.getDbId() != null) {
                                selectErrorEvent.setDbId(notProcessedEvent.getDbId());
                            }
                            selectErrorEvent.setDbUserName(notProcessedEvent.getDbUserName());
                            selectErrorEvent.setEndLimitTimestamp(notProcessedEvent.getEndLimitTimestamp());
                            selectErrorEvent.setQueryText(notProcessedEvent.getQueryText());
                            selectErrorEvent.setQueryTimestamp(notProcessedEvent.getQueryTimestamp());
                            selectErrorEvent.setServerAddr(notProcessedEvent.getServerAddr());
                            selectErrorEvent.setTcpSequenceNumber(notProcessedEvent.getTcpSequenceNumber());
                            selectErrorEvent.setTimestamp(new Date().getTime());
                            selectErrorEvent.setConnectionWebId(notProcessedEvent.getConnectionWebId());
                            damQueriesService.updateSelectErrorEvent(selectErrorEvent);
                        }

                        for (String eventTypeName : listAllEventTypes) {
                            if (notProcessedEvent.getQueryText().toUpperCase().trim().startsWith(eventTypeName.toUpperCase())) {
                                eventParsed.setEventType(eventTypeName.toUpperCase());
                                break;
                            }
                        }

                        listaEventsNova.add(eventParsed);


                    }
                    if (!listaEventsNova.isEmpty()) {
                        damQueriesService.update(list, listaEventsNova);
                    }
                } else {
                    terminou = true;
                }
            }
            logger.info("TERMINOU O PROCESSAMENTO");
        } catch (Exception e) {
            logger.error("ERRROOOOO: " + e.getMessage());
            e.printStackTrace();
        }

        System.gc();

    }
}
