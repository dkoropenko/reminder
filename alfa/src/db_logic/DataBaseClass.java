package db_logic;

import gui.Constants;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Koropenkods on 29.02.16.
 * Класс для работы с БД.
 * Используется SQLite
 */
public class DataBaseClass {

    private static DataBaseClass instance = null;

    private Connection connection = null;
    private Statement statement = null;

    /**Приватный конструктор.
     * Инициализирует драйвер для работы с DB.
     * Пробует подключиться к этой базе.
     * Если база пустая, то создаеются новые таблицы.
     */
    private DataBaseClass() throws SQLException{
        initDriver();
        connect();
        createTables();

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
    }
    private void createTables() throws SQLException {
        if(connection != null) {
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, name char(20), " +
                    "hash INTEGER NOT NULL);");

            statement.execute("CREATE TABLE IF NOT EXISTS Master (id INTEGER PRIMARY KEY, " +
                                                                "name char(30), " +
                                                                "count INTEGER, " +
                                                                "author_hash INTEGER NOT NULL);");

            statement.execute("CREATE TABLE IF NOT EXISTS Tasks (id INTEGER PRIMARY KEY, " +
                    "name CHARACTER(100), createTime DATE, modifyTime DATE, finishTime DATE, " +
                    "status INTEGER, author_hash INTEGER NOT NULL);");
        }
    }
    public void close(){
        try {
            if (statement != null){
                statement.close();
            }
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Медот добавления данных в БД.
     * @param tableName - имя таблицы в БД
     * @param parametres - список передаваемых параметров.
     * @throws SQLException
     */
    public void add(String tableName, Object ... parametres) throws SQLException{
        statement = connection.createStatement();

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
     * @param id - ключ строки.
     * @throws SQLException
     */
    public void delete (String tableName, int id) throws SQLException{
        statement = connection.createStatement();

        StringBuilder buildQuery = new StringBuilder();

        buildQuery.append("DELETE FROM ");
        buildQuery.append(tableName);
        buildQuery.append(" where id =");
        buildQuery.append(id);
        buildQuery.append(";");

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
     *
     * @param id - номер записи в таблице
     * @return - HashMap<String, Object>
     *     String - Ключ в таблице
     *     Object - значение в ячейке.
     * @throws SQLException
     */
    public HashMap<String, Object> getFromUsers(int id) throws SQLException {
        HashMap<String, Object> result = new HashMap();
        StringBuilder prepareQuery = new StringBuilder();

        prepareQuery.append("SELECT name, hash FROM Users where id ="+ id +";");

        ResultSet query = statement.executeQuery(prepareQuery.toString());

        while (query.next()){
            result.put("name", query.getString("name"));
            result.put("hash", query.getInt("hash"));
        }

        return result;
    }

    public int getSize (String tbName) throws SQLException {

        StringBuilder query = new StringBuilder();

        query.append("SELECT COUNT(*) FROM ");
        query.append(tbName);

        ResultSet rs = statement.executeQuery(query.toString());

        return rs.getInt(1);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        DataBaseClass test = null;
        HashMap<String, Object> resultQuery = new HashMap<>();

        try{
            test = DataBaseClass.getInstance();
            test.connect();
            //test.add("Users", 1, "Dima", 123);
            //test.add("Users", 2, "Vadim", 456);
            //test.add("Users", 3, "Oleg", 789);

            resultQuery.putAll(test.getFromUsers(1));

            for (Map.Entry<String, Object> out: resultQuery.entrySet()){
                System.out.println(out.getKey() +": "+ out.getValue());
            }


            System.out.println(test.getSize("Users"));


            //test.delete("Users", 3);
            //test.change("Users", 8, "vvv");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            test.close();
        }
    }
}
