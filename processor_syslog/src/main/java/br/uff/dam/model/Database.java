/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.dam.model;

/**
 *
 * @author 01245189158
 */
public class Database {
    private long DBID;
    
    public Database(long DBID){
        this.DBID = DBID;
    }

    public long getDBID(){
        return DBID;
    }
}
