/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Connection_Cassandra;
import model.Event;

/**
 *
 * @author gerente
 */
public class ClientCassandra {

    private static Cluster cluster;
    private static Session session;
    static final String table = "events";
    static final String keyspace = "dam";
    static final String insertEvent = "INSERT INTO " + keyspace + "." + table 
            + "(\"timestamp\", "
            + "query_timestamp, "
            + "client_addr, "
            + "server_addr, "
            + "tcp_seq_number, "
            //+ "columns, "
            + "connection_start_timestamp, "
            //+ "connection_web_id, "
            + "db_id, "
            + "db_user_name, "
            //+ "end_limit_timestamp, "
            //+ "event_type, "
            + "query_text, "
            + "query_type, "
            + "tables) "
            + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";   
    
    static final String insertConnection = "INSERT INTO " + keyspace + ".connections"  
            + "(connection_id, "
            + "connection_timestamp, "
            + "db_id, "
            + "db_user_name)" 
            + "VALUES ( ?, ?, ?, ?);"; 
    
    static final String closeConnection = "INSERT INTO " + keyspace + ".connections"  
            + "(connection_id, "
            + "db_id, "
            + "db_user_name, "
            + "end_limit_timestamp)" 
            + "VALUES ( ?, ?, ?, ?);";
    
    private static Metadata metadata;

    public static void connect(String node) {
        cluster = Cluster.builder()
                .addContactPoint(node).build();

        metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n",
                metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }

        session = cluster.connect();
    }

    public static void close() {
        cluster.close();
    }
   
    public static void saveNewEvent(Event evento) {
        PreparedStatement ps = session.prepare(insertEvent);
        BatchStatement batch = new BatchStatement();
        
            batch.add(ps.bind(
                    evento.getTimestamp().getTime(),
                    evento.getQuery_timestamp().getTime(),
                    evento.getClient_addr(),
                    evento.getServer_addr(),
                    evento.getTcp_seq_number(),
                    //evento.getColumns(),
                    evento.getConnection_start_timestamp().getTime(),
                    //evento.getConnection_web_id(),
                    evento.getDb_id(),
                    evento.getDb_user_name(),
                    //evento.getEnd_limit_timestamp().getTime(),
                    //evento.getEvent_type(),
                    evento.getQuery_text(),
                    evento.getQuery_type(),
                    evento.getTables()
                    ));
        
        Futures.addCallback(session.executeAsync(batch), callback());
    }

    public static void saveNewConnection(Connection_Cassandra msg) {
        PreparedStatement ps = session.prepare(insertConnection);
        BatchStatement batch = new BatchStatement();
        
            batch.add(ps.bind(
                    msg.getConnection_id(),
                    msg.getConnection_timestamp().getTime(),
                    msg.getDb_id(),
                    msg.getDb_user_name()
                    ));
        
        Futures.addCallback(session.executeAsync(batch), callback());
 
       
    }
 
      public static void saveEndConnection(Connection_Cassandra msg) {
        PreparedStatement ps = session.prepare(closeConnection);
        BatchStatement batch = new BatchStatement();
        
            batch.add(ps.bind(
                    msg.getConnection_id(),
                    msg.getDb_id(),
                    msg.getDb_user_name(),
                    msg.getEnd_limit_timestamp().getTime()
                    ));
        
        Futures.addCallback(session.executeAsync(batch), callback());
    
    }
      
      private static FutureCallback<ResultSet> callback(){
          return new FutureCallback<ResultSet>(){

              public void onSuccess(ResultSet result) {
                  Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Salvo!!");
              }

              public void onFailure(Throwable t) {
                  Logger.getLogger(getClass().getSimpleName()).log(Level.SEVERE, "Não Salvou no Cassandra!!");
              }
              
          };
      }
    
}
