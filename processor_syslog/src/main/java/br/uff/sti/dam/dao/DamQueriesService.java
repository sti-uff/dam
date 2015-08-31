/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.sti.dam.dao;

import br.uff.dam.model.Event;
import br.uff.dam.model.NotProcessedEvent;
import br.uff.dam.model.ParsedErrorEvent;
import br.uff.dam.model.SelectErrorEvent;
import br.uff.dam.model.UnknowTypeEvent;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

/**
 *
 * @author 01245189158
 */
public class DamQueriesService {
    private Logger logger;
    CassandraOperations cassandraOperations = new CassandraTemplate(PersistenceBoxSpringCassandra.getInstance().getSession());
    public List<NotProcessedEvent> getActivities(){
        logger = Logger.getLogger("DamQueriesService.getActivities()");
        //logger.info("BUSCANDO REGISTROS PARA PARSER.......");
        long now = System.currentTimeMillis();
        List<NotProcessedEvent> list = cassandraOperations.select("select * from dam.not_processed_events limit 1000 ", NotProcessedEvent.class);
        long now2 = System.currentTimeMillis();
        logger.debug("Tempo de busca no cassandra: " + (now2 - now));
        logger.debug("Numero de registros recuperados por milisegundo no cassandra: " + (((double) list.size()) / (now2 - now)));
        logger.debug("RETORNANDO REGISTROS PARA PARSER.......");
        return list;
    }

    public List<NotProcessedEvent> getSelectAmputedSelectActivities(){
        long now = System.currentTimeMillis();
        List<NotProcessedEvent> list = cassandraOperations.select("select * from dam.events where query_type='9' limit 1000 ", NotProcessedEvent.class);
        long now2 = System.currentTimeMillis();
        //System.out.println("Tempo de busca no cassandra: " + (now2 - now));
        //System.out.println("Numero de registros recuperados por milisegundo no cassandra: " + (((double) list.size()) / (now2 - now)));
        return list;
    }

      public List<NotProcessedEvent> getSelectActivitiesByTimestamp(long timestamp){
        long now = System.currentTimeMillis();
        List<NotProcessedEvent> list = cassandraOperations.select("select * from dam.events where query_type='2' and query_timestamp="+timestamp+" limit 1000 ", NotProcessedEvent.class);
        long now2 = System.currentTimeMillis();
        //System.out.println("Tempo de busca no cassandra: " + (now2 - now));
        //System.out.println("Numero de registros recuperados por milisegundo no cassandra: " + (((double) list.size()) / (now2 - now)));
        return list;
    }

    public void update(List<NotProcessedEvent> listAntiga, List<Event> listaNova){
        logger = Logger.getLogger("DamQueriesService.update(List<NotProcessedEvent>, List<Event>)");
        long now = System.currentTimeMillis();
        cassandraOperations.insert(listaNova);
        long now2 = System.currentTimeMillis();
        logger.debug("Tempo de insercao no cassandra: " + (now2 - now));
        logger.debug("Numero de registros inseridos por milisegundo no cassandra: " + (((double) listaNova.size()) / (now2 - now)));

        now = System.currentTimeMillis();
        cassandraOperations.delete(listAntiga);
        now2 = System.currentTimeMillis();
        logger.debug("Tempo de deletados no cassandra: " + (now2 - now));
        logger.debug("Numero de registros deletados por milisegundo no cassandra: " + (((double) listAntiga.size()) / (now2 - now)));

    }

    public void updateParsedErrorEvent(ParsedErrorEvent parsedErrorEvent){
        logger = Logger.getLogger("DamQueriesService.updateGenericEvent(NotProcessedEvent,GenericEvent)");
        cassandraOperations.insert(parsedErrorEvent);
    }

    public void updateUnknowTypeEvent(UnknowTypeEvent unknowTypeEvent){
        logger = Logger.getLogger("DamQueriesService.updateGenericEvent(NotProcessedEvent,GenericEvent)");
        cassandraOperations.insert(unknowTypeEvent);
    }

    public void updateSelectErrorEvent(SelectErrorEvent selectErrorEvent){
        logger = Logger.getLogger("DamQueriesService.updateGenericEvent(NotProcessedEvent,GenericEvent)");
        cassandraOperations.insert(selectErrorEvent);
    }

}
