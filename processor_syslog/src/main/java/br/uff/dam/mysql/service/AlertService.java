/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mysql.service;

import br.uff.dam.mysql.dao.AlertDAO;
import br.uff.dam.mysql.model.AlertDecoratedWeb;
import br.uff.dam.mysql.model.AlertWeb;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author thiago
 */
public class AlertService {

    private AlertDAO alertDAO = new AlertDAO();
    private Logger logger;

    public void createAlert(AlertWeb alertWeb) throws SQLException {
        logger = Logger.getLogger("AlertService.createAlert()");
        alertDAO.insertAlert(alertWeb);
    }

    public List<AlertDecoratedWeb> getAlertsDecoratedNotEmailSent() throws SQLException {
        logger = Logger.getLogger("AlertService.getAlertsDecoratedNotEmailSent()");
        return alertDAO.getAlertsDecoratedNotEmailSent();
    }

    public void updateSentEmailForListAlertDecorated(List<AlertDecoratedWeb> listAlert) throws SQLException {
        logger = Logger.getLogger("AlertService.getAlertsDecoratedNotEmailSent()");
        if (!listAlert.isEmpty()) {
            alertDAO.updateSentEmailForAlertDecorated(listAlert);
        }
    }
}
