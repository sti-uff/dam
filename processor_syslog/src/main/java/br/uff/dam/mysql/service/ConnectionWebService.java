/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.service;

import br.uff.dam.mysql.dao.ConnectionWebDAO;
import br.uff.dam.mysql.model.ConnectionWeb;
import br.uff.dam.mysql.model.DatabaseInstanceWeb;
import br.uff.dam.mysql.model.DatabaseUserWeb;
import br.uff.dam.model.GenerateNewLogonRawData;
import br.uff.dam.model.InProcessingRawData;
import java.sql.SQLException;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author 01245189158
 */
public class ConnectionWebService {
    private ConnectionWebDAO connectionWebDAO = new ConnectionWebDAO();

    private DatabaseInstanceWebService databaseInstanceService = new DatabaseInstanceWebService();
    private DatabaseUserWebService databaseUserWebService = new DatabaseUserWebService();

    Logger logger;

    public void persistConnectionWeb(String clientAddr, String serverAddr, String userDbName){
        
    }

    public ConnectionWeb persistConnectionWeb(String clientAddr, String serverAddr, String userDbName, String databaseInstance, long startTime, InProcessingRawData inProcessingRawData){
        logger = Logger.getLogger("ConnectionWebService.persistConnectionWeb()");
        try{
            DatabaseUserWeb databaseUserWeb = databaseUserWebService.getDatabaseUserWeb(userDbName, inProcessingRawData);
            DatabaseInstanceWeb dataBaseInstanceWeb = databaseInstanceService.getDatabaseInstanceWeb(databaseInstance, serverAddr);
            ConnectionWeb connectionWeb = new ConnectionWeb();
            connectionWeb.setClientAddress(clientAddr);
            connectionWeb.setServerAddress(serverAddr);
            connectionWeb.setDatabaseInstanceId(dataBaseInstanceWeb.getId());
            connectionWeb.setDatabaseUserId(databaseUserWeb.getId());
            connectionWeb.setConnectionStart(new Date(startTime));
            connectionWeb = connectionWebDAO.insertConnectionWeb(connectionWeb);
            return connectionWeb;
        }catch(Exception e){
            logger.debug("Erro: "+e.getMessage());
        }
        return null;
    }

    public ConnectionWeb persistConnectionWeb(String clientAddr, String serverAddr, String userDbName, long startTime, InProcessingRawData inProcessingRawData){
        logger = Logger.getLogger("ConnectionWebService.persistConnectionWeb()");
        try{
            DatabaseUserWeb databaseUserWeb = databaseUserWebService.getDatabaseUserWeb(userDbName, inProcessingRawData);
            ConnectionWeb connectionWeb = new ConnectionWeb();
            connectionWeb.setClientAddress(clientAddr);
            connectionWeb.setServerAddress(serverAddr);
            connectionWeb.setDatabaseInstanceId(0l);
            connectionWeb.setDatabaseUserId(databaseUserWeb.getId());
            connectionWeb.setConnectionStart(new Date(startTime));
            connectionWeb = connectionWebDAO.insertConnectionWeb(connectionWeb);
            return connectionWeb;
        }catch(Exception e){
            logger.debug("Erro: "+e.getMessage());
        }
        return null;
    }

    public ConnectionWeb updateConnectionWeb(ConnectionWeb connectionWeb, String databaseInstance){
        try{
            DatabaseInstanceWeb dataBaseInstanceWeb = databaseInstanceService.getDatabaseInstanceWeb(databaseInstance, connectionWeb.getServerAddress());
            connectionWeb.setDatabaseInstanceId(dataBaseInstanceWeb.getId());
            connectionWebDAO.updateConnectionWeb(connectionWeb);
            return connectionWeb;
        }catch(Exception e){
            logger.debug("Erro: "+e.getMessage());
        }
        return null;
    }

    public void createConnectionWeb(ConnectionWeb connectionWeb) throws SQLException{
        connectionWebDAO.insertConnectionWeb(connectionWeb);
    }
}
