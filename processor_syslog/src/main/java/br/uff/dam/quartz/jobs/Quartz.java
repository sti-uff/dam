/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.quartz.jobs;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author thiago
 */
public class Quartz {

    public static void main(String[] args) {
     
        startJobCheckEventsForAlert();
        startJobParserNotProcessedEvents();

    }

    public static void startJobCheckEventsForAlert() {
        JobDetail jobCheckEventsForAlert = JobBuilder.newJob(CheckEventsForAlertJob.class).withIdentity("jobCheckEventsForAlert", "group1").build();

        // Trigger the job to run on the next round minute
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerCheckEventsForAlert", "group1").withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(15).repeatForever()).build();


        try {
            // schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobCheckEventsForAlert, trigger);
        } catch (SchedulerException ex) {
            System.out.println("Erro: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }


    }


    public static void startJobStatisticsWeb() {
        JobDetail jobStatisticWeb = JobBuilder.newJob(StatisticWebJob.class).withIdentity("jobStatisticWeb", "group1").build();

        // Trigger the job to run on the next round minute
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerStatisticWeb", "group1").withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();


        try {
            // schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobStatisticWeb, trigger);
        } catch (SchedulerException ex) {
            System.out.println("Erro: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }


    }

    public static void startJobParserNotProcessedEvents() {
        JobDetail jobParserNotProcessedEvents = JobBuilder.newJob(JobParserNotProcessedEvents.class).withIdentity("jobParserNotProcessedEvents", "group1").build();

        // Trigger the job to run on the next round minute
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerParserNotProcessedEvents", "group1").withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever()).build();


        try {
            // schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobParserNotProcessedEvents, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void startJobSendEmailAlertJob() {
        JobDetail jobSendEmailAlert = JobBuilder.newJob(SendEmailAlertJob.class).withIdentity("jobSendEmailAlert", "group1").build();

        // Trigger the job to run on the next round minute
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerSendEmailAlert", "group1").withSchedule(
                SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever()).build();


        try {
            // schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobSendEmailAlert, trigger);
        } catch (SchedulerException ex) {
            System.out.println("Erro: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }


    }
}
