/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.quartz.jobs;

import br.uff.dam.mailer.Mailer;
import br.uff.dam.mysql.model.AlertDecoratedWeb;
import br.uff.dam.mysql.service.AlertService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 *
 * @author thiago
 */

@DisallowConcurrentExecution
public class SendEmailAlertJob implements Job {

    private Logger logger;
    private AlertService alertService = new AlertService();

    @Override
   
    public void execute(JobExecutionContext jec) {

        logger = Logger.getLogger("SendEmailAlertJob");

        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        logger.info("INICIANDO JOB DE ENVIO DE EMAILS DE ALERTAS");
        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        try {
            List<AlertDecoratedWeb> listAlert = new ArrayList<>();
            listAlert = alertService.getAlertsDecoratedNotEmailSent();

            logger.info("Quantidade de alertas encontrados para envio de email: "+ listAlert.size());

            for (AlertDecoratedWeb alertDecoratedWeb : listAlert) {
                Mailer.sendAlertEmailHtml(alertDecoratedWeb);
                Thread.sleep(1200);
            }

            alertService.updateSentEmailForListAlertDecorated(listAlert);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }



    }
}
