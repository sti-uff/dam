/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.dam.dao;

import br.uff.dam.model.ProcessCounter;
import java.util.List;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

/**
 *
 * @author 01245189158
 */
public class TimestampManager {

    CassandraOperations cassandraOps;

    public TimestampManager() {
        cassandraOps = new CassandraTemplate(PersistenceBoxSpringCassandra.getInstance().getSession());
        if (getCounterIdBD() == 0) {
            updateCounterIdBD();
        }

    }

    public Long getCounterIdBD() {
        List<ProcessCounter> counterIdList = cassandraOps.select("select * from dam.process_counters where process_name = 'processing'", ProcessCounter.class);

        return counterIdList.get(0).getCounter();
    }

    public Long getCollectCounterIdBD() {
        List<ProcessCounter> counterIdList = cassandraOps.select("select * from dam.process_counters where process_name = 'collect'", ProcessCounter.class);
        if (counterIdList.isEmpty()) {
            return 0l;
        } else {
            return counterIdList.get(0).getCounter();
        }
    }

    public void updateCounterIdBD() {
        cassandraOps.execute("update dam.process_counters set counter = counter + 1 where process_name = 'processing';");

    }
}
