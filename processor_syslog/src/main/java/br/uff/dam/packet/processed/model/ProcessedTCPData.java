/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.packet.processed.model;

import br.uff.dam.model.InProcessingRawData;
import java.io.Serializable;

/**
 *
 * @author 01245189158
 */
public class ProcessedTCPData implements Serializable{

    private static long serialVersionUID = 1L;
    
    private String sourceIPAddress;
    private String destinationIPAddress;
    private long timestamp;
    private long tcpSequenceNumber;
    private long tcpAckNumber;
    private InProcessingRawData inProcessingRawData;

    public ProcessedTCPData(String sourceIPAddress, String destinationIPAddress, 
            long timestamp, long tcpSequenceNumber, long tcpAckNumber, InProcessingRawData inProcessingRawData){
        this.sourceIPAddress = sourceIPAddress;
        this.destinationIPAddress = destinationIPAddress;
        this.timestamp = timestamp;
        this.tcpSequenceNumber = tcpSequenceNumber;
        this.tcpAckNumber = tcpAckNumber;
        this.inProcessingRawData = inProcessingRawData;
    }

    /**
     * @return the sourceIPAddress
     */
    public String getSourceIPAddress() {
        return sourceIPAddress;
    }

    /**
     * @return the destinationIPAddress
     */
    public String getDestinationIPAddress() {
        return destinationIPAddress;
    }


    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the tcpSequenceNumber
     */
    public long getTcpSequenceNumber() {
        return tcpSequenceNumber;
    }

    /**
     * @return the tcpAckNumber
     */
    public long getTcpAckNumber() {
        return tcpAckNumber;
    }


    public String[] getConnectionIDs(){
        String str[] = new String[2];
        str[0] = getSourceIPAddress()+"_"+getDestinationIPAddress();
        str[1] = getDestinationIPAddress()+"_"+getSourceIPAddress();

        return str;
    }

    /**
     * @return the inProcessingRawData
     */
    public InProcessingRawData getInProcessingRawData() {
        return inProcessingRawData;
    }


}
