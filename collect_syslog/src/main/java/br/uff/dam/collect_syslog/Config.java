/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.collect_syslog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author victor
 */
public class Config {
    
    private static final Properties properties = new Properties();

    public static void load() throws FileNotFoundException, IOException{
        properties.load(new FileInputStream("./src/main/resources/config.properties"));
    }
    
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
   

}
