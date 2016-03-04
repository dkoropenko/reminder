package db_logic;

import gui.Constants;

import java.sql.*;
import java.util.*;

/**
 * Created by Koropenkods on 29.02.16.
 * Класс для работы с БД.
 * Используется SQLite
 */
public class DataBaseClass {

    private static DataBaseClass instance = null;

    private Connection connection = null;
    private Statement statement = null;

    public static String currentUser;

    /**Приватный конструктор.
     * Инициализирует драйвер для работы с DB.
     * Пробует подключиться к этой базе.
     * Если база пустая, то создаеются новые таблицы.
     */
    private DataBaseClass() throws SQLException{
        initDriver();
        connect();
        createTables();
        close();
    }

    //Singleton
    public static DataBaseClass getInstance() throws SQLException{
        if (instance == null){
            return new DataBaseClass();
        }
        return instance;
    }

    private void initDriver(){
        try {
            Class.forName(Constants.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void connect() throws SQLException{
        connection = DriverManager.getConnection(Constants.URL);
        statement = connection.createStatement();
    }
    private void createTables() throws SQLException {
        if(connection != null) {
            statement.execute("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, name char(20), " +
                    "passwd CHAR(30));");

            statement.execute("CREATE TABLE IF NOT EXISTS Master (id INTEGER PRIMARY KEY, " +
                                                                "name char(30), " +
                                                                "count INTEGER, " +
                                                                "author CHAR(30));");

            statement.execute("CREATE TABLE IF NOT EXISTS Tasks (id INTEGER PRIMARY KEY, " +
                    "name CHARACTER(100), createTime DATE, modifyTime DATE, finishTime DATE, " +
                    "status INTEGER, author CHAR(30) NOT NULL);");

            if (this.getSize("Users") == 0){
                statement.execute("INSERT INTO Users VALUES " +
                        "(1, \""+ Constants.DEFAULT_USER +"\", \""+ Constants.DEFAULT_PASSWORD +"\");");
            }
        }
    }
    public void close(){
        try {
            if (!this.databaseIsClosed()){
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean databaseIsClosed() throws SQLException {
        return (statement.isClosed() || connection.isClosed());
    }

    /**
     * Медот добавления данных в БД.
     * @param tableName - имя таблицы в БД
     * @param parametres - список передаваемых параметров.
     * @throws SQLException
     */
    public void add(String tableName, Object ... parametres) throws SQLException{
        StringBuffer prepareQuery = new StringBuffer();
        prepareQuery.append("INSERT INTO "+ tableName +" VALUES (");
        for (int i = 0; i < parametres.length; i++) {

            if (parametres[i] instanceof String)
                prepareQuery.append("\""+ parametres[i] +"\"");
            else
                prepareQuery.append(parametres[i]);

            if (i != parametres.length-1)
                prepareQuery.append(", ");
        }
        prepareQuery.append(");");

        statement.execute(prepareQuery.toString());
    }

    /**
     * Метод удаляющий строку из БД по ее ключу.
     * @param tableName - имя таблицы в БД
     * @param name - ключ строки.
     * @throws SQLException
     */
    public void delete (String tableName, String name) throws SQLException{
        StringBuilder buildQuery = new StringBuilder();

        buildQuery.append("DELETE FROM ");
        buildQuery.append(tableName);
        buildQuery.append(" where name = \"");
        buildQuery.append(name);
        buildQuery.append("\";");

        statement.execute(buildQuery.toString());
    }

    /**
     * Метод изменяет состояние строки в таблице.
     * @param tableName - имя таблицы
     * @param id - ключ строки
     * @param parametres - новые параметры
     * @throws SQLException
     */
    public void change (String tableName, int id, Object ... parametres) throws SQLException{
        StringBuilder prepareQuery = new StringBuilder();

        prepareQuery.append("UPDATE ");
        switch (tableName){
            case "Users":
                prepareQuery.append(tableName +" SET ");
                prepareQuery.append("name = \""+ parametres[0] +"\"");
                prepareQuery.append(" where id = "+ id + ";");
                break;
        }

        statement.execute(prepareQuery.toString());
    }

    /**
     * Метод возвращает значение одной строки из таблицы Users
     * @param tbName - Таблица для поиска значений
     * @param rowName - Выбор столбца для возврата
     * @param author - Выбор владельца записей
     * @return - ArrayList<String> Все значения столбца в таблице.
     * @throws SQLException
     */
    public ArrayList<String> getFromTable(String tbName, String rowName, String author) throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        connect();

        StringBuilder prepareQuery = new StringBuilder();

        prepareQuery.append("SELECT "+ rowName +" FROM "+ tbName +" ");

        if(author != null)
            prepareQuery.append("WHERE author=\""+ author +"\"");

        prepareQuery.append(";");

        ResultSet query = statement.executeQuery(prepareQuery.toString());

        while (query.next()){
            result.add(query.getString(rowName));
        }

        return result;
    }

    public ArrayList<String> getFromUsers (String rowName) throws SQLException {
        return this.getFromTable("Users", rowName, null);
    }
    public ArrayList<String> getFromMaster (String rowName, String author) throws SQLException {
        return this.getFromTable("Master", rowName, author);
    }

    /**
     * Метод возвращает размер таблицы. Используется для выяснения
     * пустая база или нет.
     * @param tbName - имя таблиц, размер которой необходимо узнать
     * @return int size - размер таблицы
     * @throws SQLException
     */
    public int getSize (String tbName) throws SQLException {

        StringBuilder query = new StringBuilder();

        query.append("SELECT COUNT(*) FROM ");
        query.append(tbName);

        connect();
        ResultSet rs = statement.executeQuery(query.toString());

        return rs.getInt(1);
    }
}
