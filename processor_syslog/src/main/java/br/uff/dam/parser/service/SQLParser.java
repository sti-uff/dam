/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.dam.parser.service;

import br.uff.dam.model.BaseEvent;
import br.uff.dam.model.CheckedEvents;
import br.uff.dam.model.Event;
import br.uff.dam.model.NotProcessedEvent;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

/**
 *
 * @author 01245189158
 */
public class SQLParser {

    public static final int PARSER_ERROR = 0;
    public static final int UNKNOW_TYPE = 1;
    public static final int SELECT_TYPE = 2;
    public static final int INSERT_TYPE = 3;
    public static final int UPDATE_TYPE = 4;
    public static final int DELETE_TYPE = 5;
    public static final int CREATE_TABLE_TYPE = 6;
    public static final int DROP_TABLE_TYPE = 7;
    public static final int TRUNCATE_TYPE = 8;
    public static final int SELECT_FALTANDO_PEDACO = 9;
    public static final int SELECT_FOR_UPDATE = 10;
    public static final int CREATE_SESSION_TYPE = 15;
    public static final int CREATE_USER_TYPE = 16;
    public static final int ALTER_USER_TYPE = 17;
    public static final int DROP_USER_TYPE = 18;
    public static final int CREATE_PROFILE_TYPE = 19;
    public static final int ALTER_PROFILE_TYPE = 20;
    public static final int DROP_PROFILE_TYPE = 21;
    public static final int ALTER_SYSTEM_TYPE = 22;
    public static final int ALTER_ANY_TABLE_TYPE = 23;
    public static final int CREATE_ANY_TABLE_TYPE = 24;
    public static final int DROP_ANY_TABLE_TYPE = 25;
    public static final int CREATE_ANY_PROCEDURE_TYPE = 26;
    public static final int DROP_ANY_PROCEDURE_TYPE = 27;
    public static final int ALTER_ANY_PROCEDURE_TYPE = 28;
    public static final int CREATE_EXTERNAL_JOB_TYPE = 29;
    public static final int CREATE_ANY_JOB_TYPE = 30;
    public static final int CREATE_ANY_LIBRARY_TYPE = 31;
    public static final int ALTER_DATABASE_TYPE = 32;
    public static final int GRANT_ANY_PRIVILEGE_TYPE = 33;
    public static final int GRANT_ANY_OBJECT_PRIVILEGE_TYPE = 34;
    public static final int GRANT_ANY_ROLE_TYPE = 35;
    public static final int LOG_TYPE = 36;
    //não existia no original
    public static final int ALTER_SESSION = 37;
    public static final int BEGIN = 38;
    public static final int CALL = 39;
    public static final int COMMIT = 40;

    public BaseEvent checkdEventParserd(NotProcessedEvent event) {


        String sql = event.getQueryText().replaceAll(":", "").toUpperCase();

        if (sql.contains("CREATE SESSION")) {
            event.setQueryType(CREATE_SESSION_TYPE);
        } else if (sql.contains("CREATE USER")) {
            event.setQueryType(CREATE_USER_TYPE);
        } else if (sql.contains("ALTER USER")) {
            event.setQueryType(ALTER_USER_TYPE);
        } else if (sql.contains("DROP USER")) {
            event.setQueryType(DROP_USER_TYPE);
        } else if (sql.contains("CREATE PROFILE")) {
            event.setQueryType(CREATE_PROFILE_TYPE);
        } else if (sql.contains("ALTER PROFILE")) {
            event.setQueryType(ALTER_PROFILE_TYPE);
        } else if (sql.contains("DROP PROFILE")) {
            event.setQueryType(DROP_PROFILE_TYPE);
        } else if (sql.contains("ALTER SYSTEM")) {
            event.setQueryType(ALTER_SYSTEM_TYPE);
        } else if (sql.contains("ALTER ANY TABLE'")) {
            event.setQueryType(ALTER_ANY_TABLE_TYPE);
        } else if (sql.contains("CREATE ANY TABLE")) {
            event.setQueryType(CREATE_ANY_TABLE_TYPE);
        } else if (sql.contains("DROP ANY TABLE")) {
            event.setQueryType(DROP_ANY_TABLE_TYPE);
        } else if (sql.contains("CREATE ANY PROCEDURE")) {
            event.setQueryType(CREATE_ANY_PROCEDURE_TYPE);
        } else if (sql.contains("DROP ANY PROCEDURE")) {
            event.setQueryType(DROP_ANY_PROCEDURE_TYPE);
        } else if (sql.contains("ALTER ANY PROCEDURE")) {
            event.setQueryType(ALTER_ANY_PROCEDURE_TYPE);
        } else if (sql.contains("CREATE EXTERNAL JOB")) {
            event.setQueryType(CREATE_EXTERNAL_JOB_TYPE);
        } else if (sql.contains("CREATE ANY JOB")) {
            event.setQueryType(CREATE_ANY_JOB_TYPE);
        } else if (sql.contains("CREATE ANY LIBRARY")) {
            event.setQueryType(CREATE_ANY_LIBRARY_TYPE);
        } else if (sql.contains("ALTER DATABASE")) {
            event.setQueryType(ALTER_DATABASE_TYPE);
        } else if (sql.contains("GRANT ANY PRIVILEGE")) {
            event.setQueryType(GRANT_ANY_PRIVILEGE_TYPE);
        } else if (sql.contains("GRANT ANY OBJECT PRIVILEGE")) {
            event.setQueryType(GRANT_ANY_OBJECT_PRIVILEGE_TYPE);
        } else if (sql.contains("GRANT ANY ROLE'")) {
            event.setQueryType(GRANT_ANY_ROLE_TYPE);
        } else if (sql.contains("CREATE USER")) {
            event.setQueryType(CREATE_USER_TYPE);
        } else if (sql.trim().startsWith("ALTER SESSION")) {
            event.setQueryType(ALTER_SESSION);
        } else if (sql.trim().startsWith("BEGIN")) {
            event.setQueryType(BEGIN);
        } else if (sql.trim().startsWith("CALL")) {
            event.setQueryType(CALL);
        } else if (sql.trim().startsWith("COMMIT")) {
            event.setQueryType(COMMIT);
        } else {

            boolean forUpdate = false;
            sql = sql.replaceAll("@", "");
            int index = sql.indexOf("#");
            if (index > 0) {
                sql = sql.substring(0, index);
            }
            if (sql.endsWith("FOR UPDATE")) {
                forUpdate = true;
                sql = sql.substring(0, sql.length() - 10);
            }
            CCJSqlParserManager ccJSqlParserManager = new CCJSqlParserManager();
            try {

                Reader reader = new StringReader(sql);
                Statement statement = ccJSqlParserManager.parse(reader);
                if (statement.getClass() == net.sf.jsqlparser.statement.select.Select.class) {
                    if (forUpdate) {
                        event.setQueryType(SELECT_FOR_UPDATE);
                        event.setTables(getTableNamesFromSelect(statement));
                        event.setColumns(getColumnsFromSelect(statement));
                    } else {
                        event.setQueryType(SELECT_TYPE);
                        event.setTables(getTableNamesFromSelect(statement));
                        event.setColumns(getColumnsFromSelect(statement));
                    }
                } else if (statement.getClass() == net.sf.jsqlparser.statement.insert.Insert.class) {
                    event.setQueryType(INSERT_TYPE);
                    event.setTables(getTableNameFromInsert(statement));
                    event.setColumns(getColumnsFromInsert(statement));
                } else if (statement.getClass() == net.sf.jsqlparser.statement.update.Update.class) {
                    event.setQueryType(UPDATE_TYPE);
                    event.setTables(getTableNameFromUpdate(statement));
                    event.setColumns(getColumnsFromUpdate(statement));
                } else if (statement.getClass() == net.sf.jsqlparser.statement.delete.Delete.class) {
                    event.setQueryType(DELETE_TYPE);
                    event.setTables(getTableNameFromDelete(statement));
                    event.setColumns(getColumnsFromDelete(statement));
                } else if (statement.getClass() == net.sf.jsqlparser.statement.create.table.CreateTable.class) {
                    event.setQueryType(CREATE_TABLE_TYPE);
                } else if (statement.getClass() == net.sf.jsqlparser.statement.drop.Drop.class) {
                    event.setQueryType(DROP_TABLE_TYPE);
                } else if (statement.getClass() == net.sf.jsqlparser.statement.truncate.Truncate.class) {
                    event.setQueryType(TRUNCATE_TYPE);
                } else {
                    event.setQueryType(UNKNOW_TYPE);
                }

            } catch (JSQLParserException e) {
                //System.out.println("Erro: " + e.getMessage());
                //e.printStackTrace();
                if (sql.trim().startsWith("SELECT")) {
                    event.setQueryType(SELECT_FALTANDO_PEDACO);
                } else {
                    event.setQueryType(PARSER_ERROR);
                }
            }
        }

        return event;
    }

    public int parser(String sql) {
        sql = sql.replaceAll(":", "").toUpperCase();

        if (sql.contains("CREATE SESSION")) {
            return CREATE_SESSION_TYPE;
        } else if (sql.contains("CREATE USER")) {
            return CREATE_USER_TYPE;
        } else if (sql.contains("ALTER USER")) {
            return ALTER_USER_TYPE;
        } else if (sql.contains("DROP USER")) {
            return DROP_USER_TYPE;
        } else if (sql.contains("CREATE PROFILE")) {
            return CREATE_PROFILE_TYPE;
        } else if (sql.contains("ALTER PROFILE")) {
            return ALTER_PROFILE_TYPE;
        } else if (sql.contains("DROP PROFILE")) {
            return DROP_PROFILE_TYPE;
        } else if (sql.contains("ALTER SYSTEM")) {
            return ALTER_SYSTEM_TYPE;
        } else if (sql.contains("ALTER ANY TABLE'")) {
            return ALTER_ANY_TABLE_TYPE;
        } else if (sql.contains("CREATE ANY TABLE")) {
            return CREATE_ANY_TABLE_TYPE;
        } else if (sql.contains("DROP ANY TABLE")) {
            return DROP_ANY_TABLE_TYPE;
        } else if (sql.contains("CREATE ANY PROCEDURE")) {
            return CREATE_ANY_PROCEDURE_TYPE;
        } else if (sql.contains("DROP ANY PROCEDURE")) {
            return DROP_ANY_PROCEDURE_TYPE;
        } else if (sql.contains("ALTER ANY PROCEDURE")) {
            return ALTER_ANY_PROCEDURE_TYPE;
        } else if (sql.contains("CREATE EXTERNAL JOB")) {
            return CREATE_EXTERNAL_JOB_TYPE;
        } else if (sql.contains("CREATE ANY JOB")) {
            return CREATE_ANY_JOB_TYPE;
        } else if (sql.contains("CREATE ANY LIBRARY")) {
            return CREATE_ANY_LIBRARY_TYPE;
        } else if (sql.contains("ALTER DATABASE")) {
            return ALTER_DATABASE_TYPE;
        } else if (sql.contains("GRANT ANY PRIVILEGE")) {
            return GRANT_ANY_PRIVILEGE_TYPE;
        } else if (sql.contains("GRANT ANY OBJECT PRIVILEGE")) {
            return GRANT_ANY_OBJECT_PRIVILEGE_TYPE;
        } else if (sql.contains("GRANT ANY ROLE'")) {
            return GRANT_ANY_ROLE_TYPE;
        } else if (sql.contains("CREATE USER")) {
            return LOG_TYPE;
        } else if (sql.trim().startsWith("ALTER SESSION")) {
            return ALTER_SESSION;
        } else if (sql.trim().startsWith("BEGIN")) {
            return BEGIN;
        } else if (sql.trim().startsWith("CALL")) {
            return CALL;
        } else if (sql.trim().startsWith("COMMIT")) {
            return COMMIT;
        } else {


            sql = sql.replaceAll("@", "");
            int index = sql.indexOf("#");
            if (index > 0) {
                sql = sql.substring(0, index);
            }
            CCJSqlParserManager ccJSqlParserManager = new CCJSqlParserManager();
            try {

                Reader reader = new StringReader(sql);
                Statement statement = ccJSqlParserManager.parse(reader);
                if (statement.getClass() == net.sf.jsqlparser.statement.select.Select.class) {
                    return SELECT_TYPE;
                } else if (statement.getClass() == net.sf.jsqlparser.statement.insert.Insert.class) {
                    return INSERT_TYPE;
                } else if (statement.getClass() == net.sf.jsqlparser.statement.update.Update.class) {
                    return UPDATE_TYPE;
                } else if (statement.getClass() == net.sf.jsqlparser.statement.delete.Delete.class) {
                    return DELETE_TYPE;
                } else if (statement.getClass() == net.sf.jsqlparser.statement.create.table.CreateTable.class) {
                    return CREATE_TABLE_TYPE;
                } else if (statement.getClass() == net.sf.jsqlparser.statement.drop.Drop.class) {
                    return DROP_TABLE_TYPE;
                } else if (statement.getClass() == net.sf.jsqlparser.statement.truncate.Truncate.class) {
                    return TRUNCATE_TYPE;
                } else {
                    return UNKNOW_TYPE;
                }

            } catch (JSQLParserException e) {
                //System.out.println("Erro: " + e.getMessage());
                //e.printStackTrace();
                if (sql.trim().startsWith("SELECT")) {
                    return SELECT_FALTANDO_PEDACO;
                }
                return PARSER_ERROR;
            }
        }

    }

    private String getTableNamesFromSelect(Statement statement) {

        Select selectStatement = (Select) statement;

        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List tableList = tablesNamesFinder.getTableList(selectStatement);

        String tableNames = new String();
        for (Object object : tableList) {
            //System.out.println("Table: " + object.toString());
            tableNames = tableNames + object.toString() + ",";
        }

        return tableNames.substring(0, tableNames.length() - 1);
    }

    public String getTableNameFromUpdate(Statement statement) {
        Update updateStatement = (Update) statement;
        return updateStatement.getTable().getName();

    }

    public String getTableNameFromDelete(Statement statement) {
        Delete deleteStatement = (Delete) statement;
        return deleteStatement.getTable().getName();
    }

    public String getTableNameFromInsert(Statement statement) {
        Insert insertStatement = (Insert) statement;
        return insertStatement.getTable().getName();
    }

    private String getColumnsFromSelect(Statement statement) {
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        String columnNames = new String();
        List columnList = tablesNamesFinder.getColumnList(selectStatement);
        for (Object object : columnList) {
            columnNames = columnNames + object.toString() + ",";
        }

        return columnNames.length() == 0 ? columnNames : columnNames.substring(0, columnNames.length() - 1);
    }

    private String getColumnsFromDelete(Statement statement) {
        Delete deleteStatement = (Delete) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        String columnNames = new String();
        List columnList = tablesNamesFinder.getColumnList(deleteStatement.getWhere());
        for (Object object : columnList) {
            columnNames = columnNames + object.toString() + ",";
        }

        return columnNames.length() == 0 ? columnNames : columnNames.substring(0, columnNames.length() - 1);
    }

    public String getColumnsFromUpdate(Statement statement) {
        Update updateStatement = (Update) statement;
        String columnsNames = "";
        List columnsList = updateStatement.getColumns();
        if (columnsList != null) {
            for (Object column : columnsList) {
                columnsNames = columnsNames + column.toString() + ",";
            }
        }
        return columnsNames.length() == 0 ? columnsNames : columnsNames.substring(0, columnsNames.length() - 1);
    }

//    public String getColumnsFromDelete(Statement statement){
//        Delete deleteStatement = (Delete) statement;
//        String columnsNames = "";
//        List columnsList = deleteStatement.getColumns();
//        for(Object column : columnsList){
//            columnsNames = columnsNames + column.toString() + ",";
//        }
//        return columnsNames.substring(0, columnsNames.length() -1 );
//    }
    public String getColumnsFromInsert(Statement statement) {
        Insert insertStatement = (Insert) statement;
        String columnsNames = "";
        List columnsList = insertStatement.getColumns();
        if (columnsList != null) {
            for (Object column : columnsList) {
                columnsNames = columnsNames + column.toString() + ",";
            }
        }
        return columnsNames.length() == 0 ? columnsNames : columnsNames.substring(0, columnsNames.length() - 1);
    }
}
