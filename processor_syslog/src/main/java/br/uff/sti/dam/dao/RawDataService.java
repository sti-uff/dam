/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.dam.dao;

import br.uff.dam.model.GenerateNewLogonRawData;
import br.uff.dam.model.InProcessingRawData;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

public class RawDataService {

    private CassandraOperations cassandraOperations;
    private static RawDataService rawDataService;
    private Logger logger;
    private TimestampManager timestampManager;
    private boolean containsDataInProcessing;

    public static RawDataService getInstance() {
        if (rawDataService == null) {
            rawDataService = new RawDataService();
        }
        return rawDataService;
    }

    private RawDataService() {
        cassandraOperations = new CassandraTemplate(PersistenceBoxSpringCassandra.getInstance().getSession());
        timestampManager = new TimestampManager();
        containsDataInProcessing = true;
    }


    public void createGenerateNewLogonRawData(InProcessingRawData inProcessingRawData){
        GenerateNewLogonRawData generateNewLogonRawData = new GenerateNewLogonRawData();
        generateNewLogonRawData.setDestinationIPAddress(inProcessingRawData.getDestinationIPAddress());
        generateNewLogonRawData.setRawData(ByteBuffer.wrap(inProcessingRawData.getRawDataInBytes().clone()));
        generateNewLogonRawData.setRawDataTimestamp(inProcessingRawData.getRawDataTimestamp());
        generateNewLogonRawData.setSourceIPAddress(inProcessingRawData.getSourceIPAddress());
        generateNewLogonRawData.setTcpAckNumber(inProcessingRawData.getTcpAckNumber());
        generateNewLogonRawData.setTcpSequenceNumber(inProcessingRawData.getTcpSequenceNumber());
        generateNewLogonRawData.setId(inProcessingRawData.getId());
        System.out.println("Gerando generatenewlogonrawdata");
        cassandraOperations.insert(generateNewLogonRawData);
        System.out.println("gerou generatenewlogonrawdata");

    }
}
