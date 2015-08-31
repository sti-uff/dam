/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import br.uff.dam.collect_syslog.Config;
import dao.ClientCassandra;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victor
 */
public class EventHandler implements Runnable{
    
    String message;
    String host;

    public EventHandler(String message, String host) {
        this.message = message;
        this.host = host;
    }

    public void run() {
        if (message.contains(Config.getProperty("log.user"))) {    //ação do usuário
            ClientCassandra.saveNewEvent(Parser.msgToEvent(message, host));
        } else {
            if (message.contains(Config.getProperty("log.loggin"))) {    //Login
                ClientCassandra.saveNewConnection(Parser.msgToConnection(message));
            } else {
                if (message.contains(Config.getProperty("log.logout"))) {    //LOGOFF
                    ClientCassandra.saveEndConnection(Parser.msgToConnection(message));
                } else {
                    if (message.contains(Config.getProperty("log.sysdba"))) {    //SYSDBA
                        ClientCassandra.saveNewEvent(Parser.msgToEventSysdba(message, host));
                    } else {
                        Logger.getLogger(getClass().getSimpleName()).log(Level.WARNING, "Log Desconhecido");
                    }
                }
            }
        }
    }
    
    
    
}
