package br.uff.dam.collect_syslog;

import dao.ClientCassandra;
import controller.SyslogMessageHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.graylog2.syslog4j.server.SyslogServer;
import org.graylog2.syslog4j.server.SyslogServerConfigIF;
import org.graylog2.syslog4j.server.SyslogServerIF;
import org.graylog2.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
        
        Config.load();
        
        ClientCassandra.connect(Config.getProperty("cassandra.host"));
        
        SyslogServerConfigIF config = new TCPNetSyslogServerConfig(Config.getProperty("syslog.host"), Integer.valueOf(Config.getProperty("syslog.port")));
        config.setUseStructuredData(true);
        config.addEventHandler(new SyslogMessageHandler());
        SyslogServerIF syslogserver = SyslogServer.getInstance("tcp");
        syslogserver.initialize("tcp", config);
        syslogserver.run();
        
        
        //ClientCassandra.close();
        
    }
}
