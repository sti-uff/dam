/*
 * To change this template, choose Tools | Templates
 * and open the template in the edieventtor.
 */
package br.uff.dam.mysql.service;

import br.uff.dam.mysql.dao.RuleDAO;
import br.uff.dam.mysql.model.DatabaseUserWeb;
import br.uff.dam.mysql.model.EventTypeWeb;
import br.uff.dam.mysql.model.RuleWeb;
import br.uff.dam.mysql.model.RuleDatabaseUserWeb;
import br.uff.dam.mysql.model.RuleEventTypeWeb;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.net.util.SubnetUtils;
import br.uff.dam.model.Event;
import org.apache.log4j.Logger;

/**
 *
 * @author thiago
 */
public class RuleService {

    private static List<RuleWeb> listRules = new ArrayList<>();
    private static Map<String, Set<RuleWeb>> map = new HashMap<>();
    private static RuleDAO ruleDao = new RuleDAO();
    private Logger logger;

    public static void main(String[] args) {
//        criarRegrasParaTeste(listRules);
//        createMapRules();
//
//
//        EventType eventType1 = new EventType();
//        eventType1.setName("Select");
//
//        Event dam1 = new Event();
//        dam1.setClientAddr("192.168.1.10");
//        dam1.setDbUserName("Thiago");
//        dam1.setQueryText("Select");
//        dam1.setQueryType("Select");
//
//
//
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");
//        System.out.println("======== Testando se deve gerar alerta ===========");
//
//        List<Rule> teste = new ArrayList<>();
//        teste = rulesToGenerateAlert(dam1);
//
//        if (!teste.isEmpty()) {
//            System.out.println("====== As seguintes regras foram violadas ======");
//            for (Rule rule : teste) {
//                System.out.println(rule.toString());
//            }
//        }
//
//
//
//        Event dam2 = new Event();
//        dam2.setClientAddr("192.168.1.10");
//        dam2.setDbUserName("Raphael");
//        dam2.setQueryText("Drop");
//        dam2.setQueryType("Drop");
//
//        teste = rulesToGenerateAlert(dam2);
//
//        if (!teste.isEmpty()) {
//            System.out.println("====== As seguintes regras foram violadas ======");
//            for (Rule rule : teste) {
//                System.out.println(rule.toString());
//            }
//        }
    }

    public static List<RuleWeb> rulesToGenerateAlert(Event event) {

        Set<RuleWeb> possibleRulesGenerateAlerts = new HashSet<>();

        List<RuleWeb> rulesToGenerateAlert = new ArrayList<>();

//        String keyEvent = event.getDbUserName() + ";" + event.getQueryType();
        String keyEvent;
        String eventType = event.getEventType() == null ? "*" : event.getEventType().toUpperCase();
        if (event.getDbUserName() == null) {
            keyEvent = "*" + ";" + eventType;
        } else {
            keyEvent = event.getDbUserName() + ";" + eventType;
        }

        //System.out.println("Procurando regra para: " + keyEvent);

        // Testa a key principal
        if (map.containsKey(keyEvent.toUpperCase())) {
            //System.out.println("Achou uma regra para usuário e evento");
            //System.out.println("Procurando agora nas regras encontradas se o IP: " + event.getClientAddr() + " também cai na regra");
            possibleRulesGenerateAlerts.addAll(map.get(keyEvent.toUpperCase()));
        }


        //Verifica a variação para qualquer usuário
        String keyEventAnyUser = "*" + ";" + eventType;

        if (map.containsKey(keyEventAnyUser.toUpperCase())) {
            //System.out.println("Achou uma regra para QUALQUER USUÁRIO e um DETERMINADO evento");
            //System.out.println("Procurando agora nas regras encontradas se o IP: " + event.getClientAddr() + " também cai na regra");
            possibleRulesGenerateAlerts.addAll(map.get(keyEventAnyUser.toUpperCase()));

        }

        //Verifica a variação para qualquer Evento
        String keyAnyEventType = event.getDbUserName() + ";" + "*";

        if (map.containsKey(keyAnyEventType.toUpperCase())) {
            //System.out.println("Achou uma regra para QUALQUER USUÁRIO e um DETERMINADO evento");
            //System.out.println("Procurando agora nas regras encontradas se o IP: " + event.getClientAddr() + " também cai na regra");
            possibleRulesGenerateAlerts.addAll(map.get(keyAnyEventType.toUpperCase()));

        }

        //Verifica a variação para QUALQUER usuário e QUALQUEr Evento
        String keyAnyUserAndAnyEventType = "*" + ";" + "*";

        if (map.containsKey(keyAnyUserAndAnyEventType.toUpperCase())) {
            //System.out.println("Achou uma regra para QUALQUER USUÁRIO e um QUALQUER evento");
            //System.out.println("Procurando agora nas regras encontradas se o IP: " + event.getClientAddr() + " também cai na regra");
            possibleRulesGenerateAlerts.addAll(map.get(keyAnyUserAndAnyEventType.toUpperCase()));

        }

        for (RuleWeb rule : possibleRulesGenerateAlerts) {
            if (testIps(rule, event.getClientAddr())) {
                //System.out.println("Adicionou");
                rulesToGenerateAlert.add(rule);
            }


        }

        return rulesToGenerateAlert;

    }

    public static void criarRegrasParaTeste(List<RuleWeb> listaRegrasParaTeste) {

        //Criar usuários
        DatabaseUserWeb dbUser1 = new DatabaseUserWeb();
        dbUser1.setName("Thiago");


        DatabaseUserWeb dbUser2 = new DatabaseUserWeb();
        dbUser2.setName("Raphael");

        DatabaseUserWeb dbUser3 = new DatabaseUserWeb();
        dbUser3.setName("IDUFF_PRODUCAO");

        //Lembrar de criar uma regra que não tenha usuários, ou seja, qualquer usuário



        //Criar Eventos

        EventTypeWeb eventType1 = new EventTypeWeb();
        eventType1.setName("Select");

        EventTypeWeb eventType2 = new EventTypeWeb();
        eventType2.setName("Drop");


        // Cria as regras

        // R1
        RuleWeb rr1 = new RuleWeb();
        rr1.setId(1l);
        rr1.setActive(true);
        rr1.setIpList("192.168.1.10");

        RuleEventTypeWeb ret1 = new RuleEventTypeWeb();
        ret1.setEventType(eventType1);

        RuleEventTypeWeb ret2 = new RuleEventTypeWeb();
        ret2.setEventType(eventType2);

        Set<RuleEventTypeWeb> setRulesEventTypeR1 = new HashSet<>();
        setRulesEventTypeR1.add(ret1);
        setRulesEventTypeR1.add(ret2);


        RuleDatabaseUserWeb rdbu1 = new RuleDatabaseUserWeb();
        rdbu1.setDatabaseUser(dbUser1);

        Set<RuleDatabaseUserWeb> rdu1 = new HashSet<>();
        rdu1.add(rdbu1);
        rdbu1.setRule(rr1);

        rr1.setRulesEventTypes(setRulesEventTypeR1);
        rr1.setRulesDatabaseUsers(rdu1);



        // R2
        RuleWeb rr2 = new RuleWeb();
        rr2.setId(2l);
        rr2.setActive(true);
        rr2.setIpList("*");

        RuleEventTypeWeb ret3 = new RuleEventTypeWeb();
        ret3.setEventType(eventType2);


        Set<RuleEventTypeWeb> setRulesEventTypeR2 = new HashSet<>();
        setRulesEventTypeR2.add(ret3);

        RuleDatabaseUserWeb rdbu2 = new RuleDatabaseUserWeb();
        rdbu2.setDatabaseUser(dbUser2);

        Set<RuleDatabaseUserWeb> rdu2 = new HashSet<>();
        rdu2.add(rdbu2);
        rdbu2.setRule(rr2);

        rr2.setRulesEventTypes(setRulesEventTypeR2);
        rr2.setRulesDatabaseUsers(rdu2);



        // R3
        RuleWeb rr3 = new RuleWeb();
        rr3.setId(3l);
        rr3.setActive(true);
        rr3.setIpList("*");

        RuleEventTypeWeb ret4 = new RuleEventTypeWeb();
        ret4.setEventType(eventType2);


        Set<RuleEventTypeWeb> setRulesEventTypeR3 = new HashSet<>();
        setRulesEventTypeR3.add(ret4);


        rr3.setRulesEventTypes(setRulesEventTypeR3);


        // R4
        RuleWeb rr4 = new RuleWeb();
        rr4.setId(4l);
        rr4.setActive(true);
        rr4.setIpList("127.0.0.1");


        // R5
        RuleWeb rr5 = new RuleWeb();
        rr5.setId(5l);
        rr5.setActive(true);
        rr5.setIpList("123.0.0.1");

        RuleEventTypeWeb ret5 = new RuleEventTypeWeb();
        ret5.setEventType(eventType2);


        Set<RuleEventTypeWeb> setRulesEventTypeR5 = new HashSet<>();
        setRulesEventTypeR5.add(ret5);

        RuleDatabaseUserWeb rdbu5 = new RuleDatabaseUserWeb();
        rdbu5.setDatabaseUser(dbUser1);

        Set<RuleDatabaseUserWeb> rdu5 = new HashSet<>();
        rdu5.add(rdbu5);
        rdbu5.setRule(rr5);

        rr5.setRulesEventTypes(setRulesEventTypeR5);
        rr5.setRulesDatabaseUsers(rdu5);




        // R6
        RuleWeb rr6 = new RuleWeb();
        rr6.setId(6l);
        rr6.setActive(true);
        rr6.setIpList("*");

        RuleEventTypeWeb ret6 = new RuleEventTypeWeb();
        ret6.setEventType(eventType1);


        Set<RuleEventTypeWeb> setRulesEventTypeR6 = new HashSet<>();
        setRulesEventTypeR6.add(ret6);


        RuleDatabaseUserWeb rdbu6 = new RuleDatabaseUserWeb();
        rdbu6.setDatabaseUser(dbUser3);

        Set<RuleDatabaseUserWeb> rdu6 = new HashSet<>();
        rdu6.add(rdbu6);
        rdbu6.setRule(rr6);

        rr6.setRulesEventTypes(setRulesEventTypeR6);
        rr6.setRulesDatabaseUsers(rdu6);




//        listRules.add(rr1);
//        listRules.add(rr2);
//        listRules.add(rr3);
//        listRules.add(rr4);
//        listRules.add(rr5);
        listRules.add(rr6);

    }

    public static void createMapRules() {

        map = new HashMap<>();

        List<RuleWeb> allRules = new ArrayList<>();
        try {
            allRules = ruleDao.getAllRulesActivated();
            for (RuleWeb rule : allRules) {
                //System.out.println("Regra carregada: " + rule.toString());
            }
        } catch (SQLException ex) {
            System.out.println("ERROROOOO: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }

        for (RuleWeb rule : allRules) {
            if ((rule.getRulesDatabaseUsers() != null) && (!rule.getRulesDatabaseUsers().isEmpty())) {
                for (Iterator<RuleDatabaseUserWeb> iteratorUsers = rule.getRulesDatabaseUsers().iterator(); iteratorUsers.hasNext();) {
                    RuleDatabaseUserWeb ruleDatabaseUser = iteratorUsers.next();

                    if ((rule.getRulesEventTypes() == null) || (rule.getRulesEventTypes().isEmpty())) {
                        String keyMap = ruleDatabaseUser.getDatabaseUser().getName() + ";*";
                        insertRuleInMap(keyMap, rule);
                    } else {
                        for (Iterator<RuleEventTypeWeb> iteratorEvents = rule.getRulesEventTypes().iterator(); iteratorEvents.hasNext();) {
                            RuleEventTypeWeb ruleEventType = iteratorEvents.next();

                            String keyMap = ruleDatabaseUser.getDatabaseUser().getName() + ";" + ruleEventType.getEventType().getName();
                            insertRuleInMap(keyMap, rule);
                        }
                    }
                }
            } else {
                if ((rule.getRulesEventTypes() != null) && (!rule.getRulesEventTypes().isEmpty())) {
                    for (Iterator<RuleEventTypeWeb> iteratorEvents = rule.getRulesEventTypes().iterator(); iteratorEvents.hasNext();) {
                        RuleEventTypeWeb RuleEventType = iteratorEvents.next();
                        String keyMap = "*;" + RuleEventType.getEventType().getName();
                        insertRuleInMap(keyMap, rule);
                    }
                } else {
                    String keyMap = "*;*";
                   insertRuleInMap(keyMap, rule);
                }
            }
        }

        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            Object key = thisEntry.getKey();
            Set<RuleWeb> value = (Set<RuleWeb>) thisEntry.getValue();

            //System.out.print("key: " + key);

            for (Iterator<RuleWeb> it = value.iterator(); it.hasNext();) {
                RuleWeb rule = it.next();
                //System.out.print(" ----- Regras: " + rule.getId());
            }

            //System.out.println("");

        }
    }

    private static boolean testIps(RuleWeb rule, String ip) {

        String[] ipsArray = rule.getIpList().split(",");

        for (String ipRule : ipsArray) {
            String ipSemPorta = ip.split(":")[0];
            if (ipRule.equals("*")) {
                //System.out.println("Bateu IP no ****");
                return true;
            } else {
                if (ipRule.contains("/")) {
                    //System.out.println("Testando subnet");
                    return testIpInSubnet(ipSemPorta);
                } else {
                    //System.out.println("Testando se o IP do evento: " + ipSemPorta + " é o mesmo que o IP da regra: " + ipRule);
                    //System.out.println(ipSemPorta.equals(ipRule));
                    return ipSemPorta.equals(ipRule);
                }
            }

        }

        return false;


    }

    public static boolean testIpInSubnet(String ip) {
        SubnetUtils utils = new SubnetUtils(ip);
        //System.out.println("lower address: " + utils.getInfo().getLowAddress());
        //System.out.println("higher address: " + utils.getInfo().getHighAddress());
        //System.out.println(ip + " is in range? :" + utils.getInfo().isInRange(ip));

        return utils.getInfo().isInRange(ip);
    }

    public static void insertRuleInMap(String keyMap, RuleWeb rule) {
        if (map.containsKey(keyMap)) {
            map.get(keyMap.toUpperCase()).add(rule);
        } else {
            Set<RuleWeb> setRules = new HashSet<>();
            setRules.add(rule);
            map.put(keyMap.toUpperCase(), setRules);
        }
    }
}
