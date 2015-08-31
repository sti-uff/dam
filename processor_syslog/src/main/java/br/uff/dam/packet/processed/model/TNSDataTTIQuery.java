/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.packet.processed.model;

import br.uff.dam.model.InProcessingRawData;

/**
 *
 * @author 01245189158
 */
public class TNSDataTTIQuery extends ProcessedTCPData{
    private String query;

    /**
     * @return the query
     */

    public TNSDataTTIQuery(String sourceIPAddress, String destinationIPAddress,
            long timestamp, long tcpSequenceNumber, long tcpAckNumber,  String query, InProcessingRawData inProcessingRawData){

        super(sourceIPAddress, destinationIPAddress, timestamp, tcpSequenceNumber,
                tcpAckNumber, inProcessingRawData);
        this.query = query;

    }

    public String getQuery() {
        return query;
    }

    public String getConnectionID(){
        return getDestinationIPAddress()+"_"+getSourceIPAddress();
    }
}
