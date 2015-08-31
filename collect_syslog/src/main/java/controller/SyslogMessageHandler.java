/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.SocketAddress;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graylog2.syslog4j.server.SyslogServerEventIF;
import org.graylog2.syslog4j.server.SyslogServerIF;
import org.graylog2.syslog4j.server.SyslogServerSessionEventHandlerIF;

public class SyslogMessageHandler implements SyslogServerSessionEventHandlerIF {

    @Override
    public void event(Object session, SyslogServerIF syslogServer,
            SocketAddress socketAddress, SyslogServerEventIF event) {

        Logger.getLogger(getClass().getSimpleName()).log(Level.FINE, event.getMessage());
        
        String host = socketAddress.toString().replace("/", "");
        host = host.split(":")[0];
        
        String message = event.getMessage();
        
        new EventHandler(message, host).run(); //dispara thread para tratar e salvar o evento

    }

    @Override
    public void exception(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception) {
        Logger.getLogger(getClass().getSimpleName()).log(Level.SEVERE, exception.getMessage());
    }

    @Override
    public Object sessionOpened(SyslogServerIF syslogServer, SocketAddress socketAddress) {
        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "sessionOpened()");
        return new Date();
    }

    @Override
    public void sessionClosed(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, boolean timeout) {
        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "sessionClosed() {0}", session);
    }

    @Override
    public void initialize(SyslogServerIF syslogServer) {
        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "initialize()");
    }

    @Override
    public void destroy(SyslogServerIF syslogServer) {
        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "destroy()");
    }
}