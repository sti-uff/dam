package br.uff.dam.model;

import java.nio.ByteBuffer;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;


public class BaseRawDataModel {

    private static final long serialVersionUID = 3L;
    
    @PrimaryKey(value="id")
    private long id;

    @Column(value="rawdata_timestamp")
    private long rawDataTimestamp;

    @Column(value = "src_addr")
    private String sourceIPAddress;
    
    @Column(value = "dest_addr")
    private String DestinationIPAddress;
    
    @Column(value = "tcp_seq_number")
    private long tcpSequenceNumber;

    @Column(value = "tcp_ack_number")
    private long tcpAckNumber;

    @Column(value = "raw_data")
    private ByteBuffer rawData;

    private byte[] rawDataInBytes;


    public BaseRawDataModel(){
        
    }

    /**
     * @return the rawDataTimestamp
     */
    public long getRawDataTimestamp() {
        return rawDataTimestamp;
    }

    /**
     * @param rawDataTimestamp the rawDataTimestamp to set
     */
    public void setRawDataTimestamp(long rawDataTimestamp) {
        this.rawDataTimestamp = rawDataTimestamp;
    }

//    /**
//     * @return the rawData
//     */
//    public ByteBuffer getRawData() {
//        return rawData;
//    }

    /**
     * @param rawData the rawData to set
     */
    public void setRawData(ByteBuffer rawData) {
        this.rawData = rawData;
    }


    /**
     * @return the tcpAckNumber
     */
    public long getTcpAckNumber() {
        return tcpAckNumber;
    }

    public void setTcpAckNumber(long tcpAckNumber) {
        this.tcpAckNumber = tcpAckNumber;
    }

    /**
     * @return the sourceIPAddress
     */
    public String getSourceIPAddress() {
        return sourceIPAddress;
    }

    /**
     * @param sourceIPAddress the sourceIPAddress to set
     */
    public void setSourceIPAddress(String sourceIPAddress) {
        this.sourceIPAddress = sourceIPAddress;
    }

    /**
     * @return the DestinationIPAddress
     */
    public String getDestinationIPAddress() {
        return DestinationIPAddress;
    }

    /**
     * @param DestinationIPAddress the DestinationIPAddress to set
     */
    public void setDestinationIPAddress(String DestinationIPAddress) {
        this.DestinationIPAddress = DestinationIPAddress;
    }

    /**
     * @return the tcpSequenceNumber
     */
    public long getTcpSequenceNumber() {
        return tcpSequenceNumber;
    }

    /**
     * @param tcpSequenceNumber the tcpSequenceNumber to set
     */
    public void setTcpSequenceNumber(long tcpSequenceNumber) {
        this.tcpSequenceNumber = tcpSequenceNumber;
    }

    public byte[] getRawDataInBytes(){
        ByteBuffer buffer = rawData.duplicate();

        // Retrieve bytes between the position and limit
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes, 0, bytes.length);
        rawDataInBytes = bytes;
        return rawDataInBytes;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    
}
