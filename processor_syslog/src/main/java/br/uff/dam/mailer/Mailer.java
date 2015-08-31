/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.mailer;

import br.uff.dam.mysql.model.AlertDecoratedWeb;
import br.uff.dam.model.Event;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author thiago
 */
public class Mailer {

    private static String HOST_NAME = "smtp.gmail.com";
    private static String USER_NAME = "cartaouff2all@gmail.com";
    private static String PASSWORD = "salacomar";
    private static String SEND_TO = "thiagon.oliver@gmail.com";

    public static void emailTesteNaoFormatado() {
        Email email = new SimpleEmail();
        email.setHostName(HOST_NAME);
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(USER_NAME, PASSWORD));
        email.setSSL(true);
        try {
            email.setFrom(USER_NAME);
            email.setMsg("This is a test mail ... :-)");
            email.addTo(SEND_TO);
            email.send();
        } catch (EmailException ex) {
            ex.printStackTrace();
        }

    }

    public static void sendAlertEmailHtml(AlertDecoratedWeb alertDecoratedWeb) {
        // Create the email message
        HtmlEmail email = new HtmlEmail();

        // embed the image and get the content id
        URL url;
        try {
            File arquivo = new File(System.getProperty("user.dir") + "/src/main/resources/images/logo_dam_transp.png");
//
            String cid = email.embed(arquivo, "DAM");


            email.setSmtpPort(465);
            email.setHostName(HOST_NAME);

            String[] emails = alertDecoratedWeb.getEmailList().split(",");

            for (String emailTo : emails) {
                email.addTo(emailTo);
            }

            email.setFrom(USER_NAME);
            email.setAuthenticator(new DefaultAuthenticator(USER_NAME, PASSWORD));
            email.setSSL(true);


            email.setSubject("["+ alertDecoratedWeb.getCriticalityDescription() + "] Alert - Attention ");
            email.setHtmlMsg(getHtmlTemplate(alertDecoratedWeb));

            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");

            // send the email
            email.send();
        } //        catch (MalformedURLException ex) {
        //            ex.printStackTrace();
        //        }
        catch (EmailException ex) {
            ex.printStackTrace();
        }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }




    }


    public static String getHtmlTemplate(AlertDecoratedWeb alertDecoratedWeb) {

        String dataFormatada = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(alertDecoratedWeb.getTimestampEvent().getTime()));
        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
                + "<title>Demystifying Email Design</title>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>"
                + "</head>"
                + "<body style=\"margin: 0; padding: 0;\">"
                + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"
                + "<tr>"
                + "<td align=\"center\" bgcolor=\"#ee4c50\" style=\"padding: 40px 0 30px 0;\">"
                + // "<img src="+ System.getProperty("user.dir")+"/src/main/resources/images/logo_dam_transp.png" + "alt=\"Creating Email Magic\" width=\"300\" height=\"230\" style=\"display: block;\" />"+
                "<img src=\"https://cdn1.iconfinder.com/data/icons/free-3d-glossy-interface-icon-set/64/Warning.png\" alt=\"Creating Email Magic\" width=\"64\" height=\"64\" style=\"display: block;\" />"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td width=\"260\" valign=\"top\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td align=\"center\">"
                + "<img src=\"https://cdn1.iconfinder.com/data/icons/free-large-boss-icon-set/128/Manager.png\" alt=\"\" width=\"128\" height=\"128\" style=\"display: block;\" align=\"middle\" />"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"padding: 25px 0 0 0;\" align=\"center\">"
                + alertDecoratedWeb.getUser()
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "<td style=\"font-size: 0; line-height: 0;\" width=\"20\">"
                + "&nbsp;"
                + "</td>"
                + "<td width=\"260\" valign=\"top\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td align=\"center\">"
                + "<img src=\"https://cdn1.iconfinder.com/data/icons/aquanox/calendar.png\" alt=\"\" width=\"128\" height=\"128\" style=\"display: block;\" align=\"middle\" />"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"padding: 25px 0 0 0;\" align=\"center\">"
                + alertDecoratedWeb.getEvent()
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "<td width=\"260\" valign=\"top\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td align=\"center\">"
                + "<img src=\"https://cdn1.iconfinder.com/data/icons/crystalproject/128x128/apps/browser.png\" alt=\"\" width=\"128\" height=\"128\" style=\"display: block;\" align=\"middle\" />"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"padding: 25px 0 0 0;\" align=\"center\">"
                + alertDecoratedWeb.getClientAddress()
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\">"
                + "As seguintes regras foram violadas no dia: " + dataFormatada + "<br/> <b>"
                + "Regra: " + alertDecoratedWeb.getRuleId() + "; Usuários: " + alertDecoratedWeb.getUserRules() + "; Tipos de Eventos: " + alertDecoratedWeb.getEventRules() + "; IPs: " + alertDecoratedWeb.getIpList()
                + "</b>"
                + "<br/>"
                + alertDecoratedWeb.getQuery()
                + "<br/>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</body>"
                + "</html>";



        return html;
    }

    public static void main(String[] args) {
//        emailTesteNaoFormatado();
        System.out.println("Teste");
        System.out.println("Teste");
        System.out.println("Teste");
//        sendAlertEmailHtml("IDUFF", "SELECT", "200.20.0.14", new Date().toString(), null, "");


        AlertDecoratedWeb alert = new AlertDecoratedWeb();
        alert.setClientAddress("200.20.1.234");
        alert.setCriticalityDescription("NORMAL");
        alert.setEmailList("thiagon.oliver@gmail, leandrocicco@gmail.com,thiagodiogo@gmail.com");
        alert.setEvent("SELECT");
        alert.setEventRules("SELECT");
        alert.setId(10l);
        alert.setIpList("192.168.100.12");
        alert.setQuery("SELECT * FROM IDUFF_PRODUCAO.ALUNO");
        alert.setTimestampEvent(new Date());
        alert.setUser("IDUFF_PRODUCAO");
        alert.setUserRules("TESTE");
        sendAlertEmailHtml(alert);
        emailTesteNaoFormatado();



        System.out.println("Teste");
    }
}
