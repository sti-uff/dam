/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.dam.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 *
 * @author 01245189158
 */
public class PersistenceBoxSpringCassandra {

    private static PersistenceBoxSpringCassandra persistenceBox;
    private static Cluster cluster;
    private static Session session;

    public static PersistenceBoxSpringCassandra getInstance() {
        if (persistenceBox == null) {
            persistenceBox = new PersistenceBoxSpringCassandra();
        }
        return persistenceBox;
    }

    private PersistenceBoxSpringCassandra() {
        
        cluster = Cluster.builder().addContactPoints("10.0.3.121").addContactPoint("10.0.3.122").build();
        //cluster = Cluster.builder().addContactPoints("200.20.0.197").build();

        session = cluster.connect("dam");
    }

    public Session getSession() {
        return session;
    }
}
