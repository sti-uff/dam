/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.quartz.jobs;

import br.uff.dam.mysql.model.AlertWeb;
import br.uff.dam.mysql.model.EventTypeWeb;
import br.uff.dam.mysql.model.EventWeb;
import br.uff.dam.mysql.model.RuleWeb;
import br.uff.dam.mysql.model.StatisticWeb;
import br.uff.dam.mysql.service.AlertService;
import br.uff.dam.mysql.service.EventService;
import br.uff.dam.mysql.service.EventTypeService;
import br.uff.dam.mysql.service.RuleService;
import br.uff.dam.mysql.service.StatisticService;
import br.uff.sti.dam.dao.PersistenceBoxSpringCassandra;
import br.uff.dam.model.CheckedEvents;
import br.uff.dam.model.Event;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

/**
 *
 * @author thiago
 */
@DisallowConcurrentExecution
public class CheckEventsForAlertJob implements Job {

    private Logger logger;
    private AlertService alertService = new AlertService();
    private EventService eventService = new EventService();
    private EventTypeService eventTypeService = new EventTypeService();
    private StatisticService statisticService = new StatisticService();

    @Override
    public void execute(JobExecutionContext jec) {

        long tempototal1 = System.currentTimeMillis();
        Map<String, EventTypeWeb> mapEventType = eventTypeService.getMapEventTypes();

        //Busca as regras em memória a toda momento que esse job é executado
        logger = Logger.getLogger("CheckEventsForAlertJob");
        try {
            logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            logger.info("INICIANDO JOB DE ALERT");
            logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");


            CassandraOperations cassandraOperations = new CassandraTemplate(PersistenceBoxSpringCassandra.getInstance().getSession());

            List<Event> listEvent = cassandraOperations.select("select * from dam.events limit 1000", Event.class);

            logger.info("QTD DE EVENTOS QUE SERÃO CHECADOS JOB DE ALERT: " + listEvent.size());

            Integer i = 0;
            if (!listEvent.isEmpty()) {

                long now1 = System.currentTimeMillis();
                
                RuleService.createMapRules();

                List<CheckedEvents> processedEvents = new ArrayList<>();

                logger.info("Buscou do banco...");

                for (Event event : listEvent) {
                    try {

                        i++;

                        List<RuleWeb> rules = RuleService.rulesToGenerateAlert(event);

                        CheckedEvents eventChecked = new CheckedEvents();

                        if (!rules.isEmpty()) {

                            //Tem que verificar se alguma das regras é whiteList
                            //Se for não não deve gerar alerta
                            boolean passWhiteList = false;
                            for (RuleWeb rule : rules) {
                                if (rule.getType().equals(RuleWeb.RULE_TYPE_ACCEPT)) {
                                    passWhiteList = true;
                                }
                            }

                            if (!passWhiteList) {
                                eventChecked.setGeneratedAlert(true);
                                for (RuleWeb rule : rules) {


                                    //Cria o evento na Web
                                    EventWeb eventWeb = new EventWeb();
                                    if (event.getEventType() != null) {
                                        eventWeb.setEventTypeId(mapEventType.get(event.getEventType().toUpperCase()).getId());
                                    }
                                    eventWeb.setText(event.getQueryText());
                                    eventWeb.setTimestamp(new Date(event.getQueryTimestamp()));
                                    eventWeb.setClientAddress(event.getClientAddr());
                                    eventWeb.setServerAddress(event.getServerAddr());
                                    eventWeb.setConnectionWebId(event.getConnectionWebId());
                                    eventWeb.setTimestampInMiliseconds(event.getQueryTimestamp());
                                    eventWeb.setTables(event.getTables());
                                    eventWeb.setColumns(event.getColumns());
                                    eventService.createEvent(eventWeb);


                                    //Cria o alerta na Web
                                    AlertWeb alertWeb = new AlertWeb();
                                    alertWeb.setEventId(eventWeb.getId());
                                    alertWeb.setRuleId(rule.getId());
                                    alertWeb.setTimestamp(new Date());
                                    alertWeb.setCriticalityId(rule.getCriticalityId());
                                    alertService.createAlert(alertWeb);

                                    //Mailer.sendAlertEmailHtml(event, rule);
                                    //Thread.sleep(3000);
                                }
                            } else {
                                eventChecked.setGeneratedAlert(false);
                            }

                        } else {
                            eventChecked.setGeneratedAlert(false);
                        }


                        eventChecked.setClientAddr(event.getClientAddr());
                        eventChecked.setConnectionStartTimestamp(event.getConnectionStartTimestamp());
                        if (event.getDbId() != null) {
                            eventChecked.setDbId(event.getDbId());
                        }

                        eventChecked.setDbUserName(event.getDbUserName());
                        eventChecked.setEndLimitTimestamp(event.getEndLimitTimestamp());
                        eventChecked.setQueryText(event.getQueryText());
                        eventChecked.setQueryType(event.getQueryType());
                        eventChecked.setServerAddr(event.getServerAddr());
                        eventChecked.setTcpSequenceNumber(event.getTcpSequenceNumber());
                        eventChecked.setQueryTimestamp(event.getQueryTimestamp());
                        eventChecked.setTimestamp(new Date().getTime());
                        eventChecked.setConnectionWebId(event.getConnectionWebId());

                        processedEvents.add(eventChecked);

                    } catch (Exception e) {
                        logger.error("Erro: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                cassandraOperations.insert(processedEvents);
                cassandraOperations.delete(listEvent);

                statisticService.createStatistic(StatisticWeb.NUMBER_EVENTS_ALERT_CHECKED, i.doubleValue());

                long now2 = System.currentTimeMillis();

                logger.info("Numero de eventos checados por milisegundo: "+(((double) listEvent.size())/(now2-now1)));
                logger.info("Numero de eventos checados criados por milisegundo: "+(((double) processedEvents.size())/(now2-now1)));


                logger.info("Terminou!!!!");
            } else {
                logger.info("Nenhum registro na tabela de eventos para checar por alertas");
            }


        } catch (Exception e) {
            logger.error("Erro: " + e.getMessage());
            e.printStackTrace();
        }

        long tempototal2 = System.currentTimeMillis();
        logger.info("Tempo de processamento checkeventsforalertjob (sem o gc) em milisegundos: "+(tempototal2 - tempototal1));
        long now1 = System.currentTimeMillis();
        System.gc();
        long now2 = System.currentTimeMillis();
        logger.info("Tempo de limpeza (system.gc) em milisegundos : "+(now2-now1));



    }
}
