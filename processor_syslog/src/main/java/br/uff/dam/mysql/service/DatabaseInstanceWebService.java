/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.service;

import br.uff.dam.mysql.dao.DatabaseInstanceWebDAO;
import br.uff.dam.mysql.model.DatabaseInstanceWeb;
import java.sql.SQLException;

/**
 *
 * @author 01245189158
 */
public class DatabaseInstanceWebService {
    private DatabaseInstanceWebDAO databaseInstanceWebDAO = new DatabaseInstanceWebDAO();

    public DatabaseInstanceWeb getDatabaseInstanceWeb(String AUTH_INSTANCENAME, String ip) throws SQLException{
        return databaseInstanceWebDAO.getDatabaseInstanceWeb(AUTH_INSTANCENAME, ip);
    }
}
