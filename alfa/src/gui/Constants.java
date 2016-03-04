package gui;

import javax.swing.*;

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
    String[] LETTERS = {"/", "\\", ":", "*", "?", "\"", "<", ">", "|"};
    String DRIVER = "org.sqlite.JDBC";
    String URL = "jdbc:sqlite:Task.db";
    String DEFAULT_USER = "User";
    String DEFAULT_PASSWORD = "";

    //Переменные UI
    String APPNAME = "Reminder 0.9";
    String ICON = "resource/images/icon.png";
    ImageIcon ADD = new ImageIcon("resource/images/add.png");
    ImageIcon DEL = new ImageIcon("resource/images/del.png");
    ImageIcon OPTIONS = new ImageIcon("resource/images/mod.png");
    ImageIcon COMPLETE = new ImageIcon("resource/images/complete.png");


}
