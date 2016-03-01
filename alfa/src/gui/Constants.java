package gui;

/**
 * Created by Koropenkods on 29.02.16.
 */
public interface Constants {
    //Данные для заполнения таблицы
    String ID =     "№";
    String DATE =   "Дата";
    String VALUE =  "Значение";
    String STATUS = "Статус";
    String TASK =   "Задание";

    //Данные для создания БД.
    public static String[] LETTERS = {"/", "\\", ":", "*", "?", "\"", "<", ">", "|"};

    public static String DRIVER = "org.sqlite.JDBC";
    public static String URL = "jdbc:sqlite:Task.db";
}
