package br.uff.dam.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.Table;

@Table(value="checked_events")
public class CheckedEvents extends BaseEvent {

    @Column(value = "generated_alert")
    private Boolean generatedAlert;
   

    public CheckedEvents(){
        
    }


    /**
     * @return the generatedAlert
     */
    public Boolean getGeneratedAlert() {
        return generatedAlert;
    }

    /**
     * @param generatedAlert the generatedAlert to set
     */
    public void setGeneratedAlert(Boolean generatedAlert) {
        this.generatedAlert = generatedAlert;
    }

}
