/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.mysql.service;

import br.uff.dam.mysql.dao.DatabaseUserWebDAO;
import br.uff.dam.mysql.model.DatabaseUserWeb;
import br.uff.dam.model.InProcessingRawData;
import java.sql.SQLException;

/**
 *
 * @author 01245189158
 */
public class DatabaseUserWebService {
    private DatabaseUserWebDAO databaseUserWebDAO = new DatabaseUserWebDAO();

    public DatabaseUserWeb getDatabaseUserWeb(String name, InProcessingRawData inProcessingRawData) throws SQLException{
        return databaseUserWebDAO.getDatabaseUserWeb(name, inProcessingRawData);
    }
}
